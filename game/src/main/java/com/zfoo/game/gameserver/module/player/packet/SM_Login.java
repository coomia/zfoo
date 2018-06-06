package com.zfoo.game.gameserver.module.player.packet;

import com.zfoo.game.gameserver.module.player.entity.PlayerEntity;
import com.zfoo.net.protocol.model.packet.IPacket;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-24 16:47
 */
public class SM_Login implements IPacket {

    private static final transient short PROTOCOL_ID = 3;

    private long id;

    private int age;

    private short loginNum;

    private boolean man;

    private String name;

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    public static SM_Login valueOf(PlayerEntity player) {
        SM_Login sm = new SM_Login();
        sm.setId(player.getId());
        sm.setAge(player.getAge());
        sm.setLoginNum(player.getLoginNum());
        sm.setMan(player.isMan());
        sm.setName(player.getName());
        return sm;
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
