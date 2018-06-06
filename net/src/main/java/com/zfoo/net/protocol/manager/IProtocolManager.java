package com.zfoo.net.protocol.manager;

import com.zfoo.net.protocol.model.protocol.ProtocolRegistration;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.09 10:59
 */
public interface IProtocolManager {

    void parseProtocol(String protocolLocation);

    ProtocolRegistration getProtocolRegistration(short id);

}
