package com.zfoo.game.gameserver.module.player.model;

import com.zfoo.game.gameserver.module.player.entity.PlayerEntity;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-12 17:10
 */
public class Player {

    private long id;

    private int age;

    private short loginNum;

    private boolean man;// 性别，false男，true女

    private String name;

    public static Player valueOf(PlayerEntity playerEntity) {
        Player player = new Player();
        player.setId(playerEntity.getId());
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public short getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(short loginNum) {
        this.loginNum = loginNum;
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
