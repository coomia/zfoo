package com.zfoo.game.gameserver.module.activity.packet;

import com.zfoo.net.protocol.model.packet.IPacket;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-23 17:15
 */
public class CM_ActivityInfo implements IPacket {

    private static final transient short PROTOCOL_ID = 1;

    private int a;


    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

}
