package com.zfoo.net.dispatcher.model.vo;

import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.net.server.model.Session;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.28 14:48
 */
public interface IPacketReceiver {

    void invoke(Session session, IPacket packet);

}
