package com.zfoo.game.gameserver.module.repository.model;

import com.zfoo.game.gameserver.module.core.reward.AndReward;
import com.zfoo.util.StringUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-13 09:54
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class AbstractRepository<E extends RNode> {

    /**
     * 仓库的存储结构，可以自定义指定的结构
     */
    @JsonProperty
    private List<E> list;

    // list的Class类型
    protected Class<? extends List> clazz = ArrayList.class;

    private AbstractRepository() {
        try {
            list = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 仓库容量的最大上限
     *
     * @return 容量上限
     */
    public abstract int maxSize();

    /**
     * RNodeEnum排序的类型
     *
     * @return 排序的类型
     */
    public abstract List<RNodeEnum> sortedList();

    /**
     * 适配器
     *
     * @return 节点的适配器
     */
    public abstract IRNodeAdapter adapter();

    /**
     * 仓库
     *
     * @return 返回不可变的仓库List
     */
    public List<E> repository() {
        return Collections.unmodifiableList(list);
    }

    public long sizeOf(RNodeEnum nodeEnum, String id) {
        if (nodeEnum == null || !sortedList().contains(nodeEnum) || StringUtils.isEmpty(id)) {
            return 0;
        }
        long num = 0;
        for (E e : list) {
            if (e != null && e.getRNodeEnum() == nodeEnum && e.getId().equals(id)) {
                num += e.getNum();
            }
        }
        return num;
    }

    public AbstractRepository<E> merge() {
        Map<RNodeEnum, List<E>> map = new EnumMap<>(RNodeEnum.class);

        for (RNodeEnum nodeEnum : sortedList()) {
            map.put(nodeEnum, new ArrayList<>());
        }

        // 收集不同的类型
        for (E e : list) {
            if (e != null) {
                map.get(e.getRNodeEnum()).add(e);
            }
        }

        // 先堆叠不同的类型，堆叠过后的必须是深克隆的对象
        for (RNodeEnum nodeEnum : sortedList()) {
            if (nodeEnum.builder() != null) {
                List<E> stackList = (List<E>) nodeEnum.builder().build(map.get(nodeEnum));
                map.put(nodeEnum, stackList);
            }
        }

        // 再排序不同的类型
        for (RNodeEnum nodeEnum : sortedList()) {
            if (nodeEnum.comparator() != null) {
                Collections.sort(map.get(nodeEnum), (Comparator<? super E>) nodeEnum.comparator());
            }
        }

        list.clear();

        for (RNodeEnum nodeEnum : sortedList()) {
            List<E> resultList = map.get(nodeEnum);
            if (resultList != null && resultList.size() > 0) {
                list.addAll(resultList);
            }
        }

        return this;
    }

    public Map<Integer, E> addRNode(E e) {
        if (e == null || StringUtils.isBlank(e.getId())) {
            throw new IllegalArgumentException();
        }
        return addRnodes(Collections.singletonList(e));
    }

    /**
     * 在仓库增加一个节点
     *
     * @param nodes 要增加的List节点信息
     * @return 对应的索引，和对应索引变换的信息
     */
    public Map<Integer, E> addRnodes(List<E> nodes) {
        if (nodes.isEmpty()) {
            return new HashMap<>();
        }

        // 防止nodes里有超过堆叠上限的节点
        List<E> newNodes = new ArrayList<>();
        int count = 0;
        for (E e : nodes) {
            if (e == null || !sortedList().contains(e.getRNodeEnum())) {
                throw new IllegalArgumentException("此仓库不能添加node类型：" + e);
            }

            if (e.getRNodeEnum().builder() != null) {
                newNodes.addAll((Collection<? extends E>) e.getRNodeEnum().builder().build(Collections.singletonList(e)));
            } else {
                newNodes.add(e);
                count++;
            }
        }

        // 超过堆叠上限
        if (count + list.size() > maxSize()) {
            throw new IllegalArgumentException("超过堆叠上限");
        }

        AddResult addResult = new AddResult();
        for (E e : newNodes) {
            // 先看是否可以堆叠，能堆叠则先堆叠
            IRNodeStactBuilder builder = e.getRNodeEnum().builder();

            if (builder != null) {
                // 先收集所有可堆叠的节点
                int index = 0;
                Map<Integer, E> stackMap = new HashMap<>();
                for (E node : list) {
                    if (node != null && e.getRNodeEnum() == node.getRNodeEnum()
                            && e.getId().equals(node.getId()) && builder.maxStackNum(e) > node.getNum()) {
                        stackMap.put(index, node);
                    }
                    index++;
                }

                // 如果有可堆叠的节点则堆叠，堆叠过后可能有剩下的
                if (stackMap.size() > 0) {
                    for (Map.Entry<Integer, E> entry : stackMap.entrySet()) {
                        // 两个两个堆叠
                        List<E> stackList = new ArrayList<>();
                        stackList.add(e);
                        stackList.add(entry.getValue());
                        // 堆叠后的list
                        List<? extends RNode> newStackList = builder.build(stackList);

                        // 堆叠发生错误，两个节点堆叠出来的只可能是一个节点或者两个节点
                        if (newStackList.size() <= 0 || newStackList.size() >= 3) {
                            roolBack(addResult.histroy);
                            throw new IllegalArgumentException("堆叠未知异常：" + stackList.toString());
                        }

                        // 堆叠成功，没有剩下其它的节点
                        if (newStackList.size() == 1) {
                            addResult.putHistory(entry.getKey(), list.get(entry.getKey()));
                            addResult.putResult(entry.getKey(), (E) newStackList.get(0));
                            list.set(entry.getKey(), (E) newStackList.get(0));
                            e = null;
                            break;
                        }

                        // 堆叠还有剩余
                        if (newStackList.size() == 2) {
                            addResult.putHistory(entry.getKey(), list.get(entry.getKey()));
                            addResult.putResult(entry.getKey(), (E) newStackList.get(0));
                            list.set(entry.getKey(), (E) newStackList.get(0));
                            e = (E) newStackList.get(1);
                            continue;
                        }
                        roolBack(addResult.history());
                        throw new IllegalArgumentException("堆叠发送在最后的未知异常");
                    }
                }
            }

            // 如果e有剩余，则加入到空的节点中
            if (e != null) {
                int index = 0;
                for (E node : list) {
                    if (node == null) {
                        break;
                    }
                    index++;
                }

                if (index >= maxSize()) {
                    roolBack(addResult.history());
                    throw new IllegalArgumentException("仓库空间不足");
                }

                if (index < list.size()) {
                    addResult.putHistory(index, e);
                    addResult.putResult(index, e);
                    list.set(index, e);
                } else if (index == list.size() && list.size() < maxSize()) {
                    addResult.putHistory(index, null);
                    addResult.putResult(index, e);
                    list.add(e);
                } else {
                    roolBack(addResult.history());
                    throw new IllegalArgumentException("仓库最后空间不足");
                }
            }
        }

        return addResult.result;
    }

    public Map<Integer, E> addRNodes(AndReward andReward) {
        return addRnodes((List<E>) adapter().andReward2Nodes(andReward));
    }

    public E remove(int index) {
        if (index < 0 || index >= list.size()) {
            throw new IllegalArgumentException("index:" + index);
        }

        E e = list.get(index);

        if (e == null) {
            return null;
        }

        list.set(index, null);

        // 如果移除的最后一个node，则把它之前为空的node全部移除
        if (list.size() - 1 == index) {
            for (int i = index; i >= 0; i--) {
                if (list.get(i) == null) {
                    list.remove(i);
                } else {
                    break;
                }
            }
        }

        return e;
    }

    public void clearRepository() {
        list.clear();
    }

    public Map<Integer, E> exchange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= list.size()) {
            throw new IllegalArgumentException("fromIndex:" + fromIndex);
        }
        if (toIndex < 0 || toIndex >= maxSize()) {
            throw new IllegalArgumentException("toIndex:" + toIndex);
        }
        if (fromIndex == toIndex) {
            throw new IllegalArgumentException("fromIndex:" + fromIndex + ",toIndex:" + toIndex);
        }

        E a = list.get(fromIndex);
        E b;

        // 索引超过了当前的大小
        if (toIndex >= list.size()) {
            for (int i = list.size(); i < toIndex; i++) {
                list.add(null);
            }
            b = null;
        } else {
            b = list.get(toIndex);
        }

        list.set(fromIndex, b);
        list.set(toIndex, a);

        Map<Integer, E> result = new HashMap<>();
        result.put(fromIndex, b);
        result.put(toIndex, a);

        return result;
    }

    private void roolBack(Map<Integer, E> history) {
        if (history == null || history.isEmpty()) {
            return;
        }

        for (Map.Entry<Integer, E> entry : history.entrySet()) {
            if (entry.getKey() >= list.size()) {
                for (int i = 0; i <= entry.getKey() - list.size(); i++) {
                    list.add(null);
                }
            }
            list.set(entry.getKey(), entry.getValue());
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) == null) {
                list.remove(i);
            } else {
                break;
            }
        }
    }

    private class AddResult {

        // 历史索引对应的节点信息
        private Map<Integer, E> histroy;

        // 改变后的索引对应的节点信息
        private Map<Integer, E> result;

        private AddResult() {
            this.histroy = new HashMap<>();
            this.result = new HashMap<>();
        }

        private void putHistory(int index, E e) {
            if (!histroy.containsKey(index)) {
                histroy.put(index, e);
            }
        }

        private void putResult(int index, E e) {
            result.put(index, e);
        }

        private Map<Integer, E> history() {
            return histroy;
        }

        private Map<Integer, E> result() {
            return result;
        }
    }

}
