package com.zfoo.game.gameserver.module.player.packet;

import com.zfoo.net.protocol.model.packet.IPacket;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-02-24 12:27
 */
public class CM_Login implements IPacket {

    private static final transient short PROTOCOL_ID = 2;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }
}
