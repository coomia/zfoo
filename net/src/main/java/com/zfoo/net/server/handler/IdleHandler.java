package com.zfoo.net.server.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 09.26 11:41
 */
@ChannelHandler.Sharable
public class IdleHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("channel:" + ctx.channel().remoteAddress() + "time out for close");
            }
            ctx.channel().close();
        }

    }
}
