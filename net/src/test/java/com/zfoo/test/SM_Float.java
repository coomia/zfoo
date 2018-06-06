package com.zfoo.test;

import com.zfoo.net.protocol.model.packet.IPacket;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.12 16:00
 */
public class SM_Float implements IPacket {

    private static final transient short PROTOCOL_ID = 3;


    @Override
    public short protcolId() {
        return PROTOCOL_ID;
    }


}

