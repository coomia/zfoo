package com.zfoo.orm.model.persister;

import com.zfoo.orm.OrmContext;
import com.zfoo.orm.model.accessor.IAccessor;
import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.util.AssertionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-04 10:13
 */
public abstract class AbstractPersister implements IPersister {

    private static final Logger logger = LoggerFactory.getLogger(AbstractPersister.class);


    protected ReadWriteLock lock = new ReentrantReadWriteLock();

    protected Map<Object, PNode> persisterMap = new ConcurrentHashMap<>();

    @Override
    public void put(PNode node) {
        AssertionUtils.notNull(node);

        try {
            lock.readLock().lock();

            // 如果持久化队列没有node，则直接添加
            PNode previousPNode = persisterMap.putIfAbsent(node.getEntity().getId(), node);
            if (previousPNode == null) {
                return;
            }

            previousPNode.setCreatedTime(Math.max(previousPNode.getCreatedTime(), node.getCreatedTime()));

            // 持久化类型相同，则以最新的为准
            if (previousPNode.getNodeType() == node.getNodeType()) {
                return;
            }

            // 持久化类型不同
            if (previousPNode.getNodeType() == PNodeTypeEnum.INSERT) {
                if (node.getNodeType() == PNodeTypeEnum.UPDATE) {
                    previousPNode.setNodeType(PNodeTypeEnum.INSERT);
                    return;
                }
                if (node.getNodeType() == PNodeTypeEnum.DELETE) {
                    if (previousPNode.getCreatedTime() < node.getCreatedTime()) {
                        previousPNode.setNodeType(PNodeTypeEnum.DELETE);
                    }
                    return;
                }
            }

            if (previousPNode.getNodeType() == PNodeTypeEnum.UPDATE) {
                if (node.getNodeType() == PNodeTypeEnum.INSERT) {
                    previousPNode.setNodeType(PNodeTypeEnum.INSERT);
                    return;
                }
                if (node.getNodeType() == PNodeTypeEnum.DELETE) {
                    if (previousPNode.getCreatedTime() < node.getCreatedTime()) {
                        previousPNode.setNodeType(PNodeTypeEnum.DELETE);
                    }
                    return;
                }
            }

            // 如果前一个节点是移除，后来的节点是插入或者更新，而且后面的节点的时间大于前一个节点，则移除删除操作
            if (previousPNode.getNodeType() == PNodeTypeEnum.DELETE && previousPNode.getCreatedTime() < node.getCreatedTime()) {
                previousPNode.setNodeType(node.getNodeType());
            }
        } catch (Exception e) {
            logger.error("持久化器：[{}]插入元素异常退出,[e:{}]", getClass().getSimpleName(), e.getMessage());
        } finally {
            lock.readLock().unlock();
        }
    }

    // 游戏中80%都是执行更新的操作，这样做会极大的提高更新速度
    @Override
    public void persist() {
        if (persisterMap.isEmpty()) {
            return;
        }

        try {
            lock.writeLock().lock();

            List<IEntity<?>> insertList = new ArrayList<>();
            List<IEntity<?>> updateList = new ArrayList<>();
            List<IEntity<?>> deleteList = new ArrayList<>();

            for (Map.Entry<Object, PNode> entry : persisterMap.entrySet()) {
                PNode node = entry.getValue();
                PNodeTypeEnum nodeType = node.getNodeType();
                IEntity<?> entity = node.getEntity();
                switch (nodeType) {
                    case INSERT:
                        insertList.add(entity);
                        break;
                    case UPDATE:
                        updateList.add(entity);
                        break;
                    case DELETE:
                        deleteList.add(entity);
                        break;
                    default:
                        FormattingTuple formattingTuple = MessageFormatter.format("[{}]不能持久化该类型[PNodeType:{}]", this.getClass().getSimpleName(), nodeType);
                        throw new IllegalArgumentException(formattingTuple.getMessage());
                }
            }

            IAccessor accessor = OrmContext.getAccessor();

            // 执行插入
            if (!insertList.isEmpty()) {
                try {
                    accessor.batchInsert(insertList);
                    insertList.clear();
                } catch (Exception e) {
                    logger.debug("批量插入异常，数据库中存在重复的实体，执行一个一个插入的操作");
                }
            }
            if (!insertList.isEmpty()) {
                for (IEntity<?> entity : insertList) {
                    try {
                        accessor.insert(entity);
                        continue;
                    } catch (Exception exception) {
                        logger.debug("插入异常，数据库中经存在重复的[entity:{}]", entity);
                    }
                    try {
                        accessor.update(entity);
                    } catch (Exception exception) {
                        logger.debug("插入异常过后就行更新异常，[entity:{}]", entity);
                    }
                }
            }

            // 执行更新
            if (!updateList.isEmpty()) {
                try {
                    accessor.batchUpdate(updateList);
                    updateList.clear();
                } catch (Exception e) {
                    logger.debug("批量更新异常，数据库中不存在对应实体，执行一个一个更新的操作");
                }
            }

            if (!updateList.isEmpty()) {
                for (IEntity<?> entity : updateList) {
                    try {
                        accessor.update(entity);
                        continue;
                    } catch (Exception exception) {
                        logger.debug("更新异常，数据库中不存在[entity:{}]", entity);
                    }
                    try {
                        accessor.insert(entity);
                    } catch (Exception exception) {
                        logger.debug("更新异常过后进行插入异常，[entity:{}]", entity);
                    }
                }
            }

            // 执行删除
            if (!deleteList.isEmpty()) {
                try {
                    accessor.batchDelete(deleteList);
                    deleteList.clear();
                } catch (Exception e) {
                    logger.debug("批量删除异常，数据库中不存在对应的实体，执行一个一个删除的操作");
                }
            }

            if (!deleteList.isEmpty()) {
                for (IEntity<?> entity : deleteList) {
                    try {
                        accessor.delete(entity);
                    } catch (Exception exception) {
                        logger.debug("删除异常，数据库中不存在[entity:{}]", entity);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("持久化器：{}的持久化过程中异常退出[e:{}]", getClass().getSimpleName(), e.getMessage());
        } finally {
            // 将需要持久化的node全部清除
            persisterMap.clear();
            lock.writeLock().unlock();
        }


    }

    Map<String, String> getInfos() {
        return null;
    }

}
