package com.zfoo.net.dispatcher.manager;

import com.zfoo.net.protocol.model.packet.IPacket;
import com.zfoo.net.server.model.Session;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.28 14:31
 */
public interface IPacketDispatcherManager {

    void receive(Session session, IPacket packet);

    void send(Session session, IPacket packet);

    void registPacketReceiverDefintion(Object bean);

}
