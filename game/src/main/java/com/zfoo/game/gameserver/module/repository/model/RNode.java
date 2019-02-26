package com.zfoo.game.gameserver.module.repository.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 一个格子节点，如果有其它不同类型的节点可以自己再定义
 * <p>
 * 只需要简单的继承基类，就可以实现自定义的节点垒许过期时间，是否受保护，坐骑，邮件等等
 * </p>
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 16:36
 */
public class RNode {


    @JsonProperty
    private String id;

    @JsonProperty
    private int num;

    // type———>RNodeEnum，两者对应
    @JsonProperty
    private int type;


    public RNode(String id, int num, int type) {
        this.id = id;
        this.num = num;
        this.type = type;
    }

    public RNodeEnum getRNodeEnum() {
        return RNodeEnum.getRnodeEnum(type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
