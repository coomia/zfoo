package com.zfoo.orm.model.persister;

import com.zfoo.orm.model.entity.IEntity;
import com.zfoo.util.TimeUtils;

/**
 * Persister Node
 * <p>
 * 需要持久化的一个节点
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017-12-02 17:20
 */
public class PNode {

    private volatile PNodeTypeEnum nodeType;

    private volatile long createdTime = TimeUtils.currentTimeMillis();

    private IEntity<?> entity;

    private PNode(PNodeTypeEnum nodeType, IEntity<?> entity) {
        this.nodeType = nodeType;
        this.entity = entity;
    }

    public static PNode valueOfInsertPNode(IEntity<?> entity) {
        PNode node = new PNode(PNodeTypeEnum.INSERT, entity);
        return node;
    }

    public static PNode valueOfUpdatePNode(IEntity<?> entity) {
        PNode node = new PNode(PNodeTypeEnum.UPDATE, entity);
        return node;
    }

    public static PNode valueOfDeletePNode(IEntity<?> entity) {
        PNode node = new PNode(PNodeTypeEnum.DELETE, entity);
        return node;
    }

    public PNodeTypeEnum getNodeType() {
        return nodeType;
    }

    public void setNodeType(PNodeTypeEnum nodeType) {
        this.nodeType = nodeType;
    }

    public IEntity<?> getEntity() {
        return entity;
    }

    public void setEntity(IEntity<?> entity) {
        this.entity = entity;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
