package com.zfoo.net.protocol.model.packet;

/**
 * 所有需要发送的包都必须实现这个接口
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 10.10 18:19
 */
public interface IPacket {

    /**
     * 这个包的协议号
     *
     * @return 协议号Id
     */
    short protcolId();

}
