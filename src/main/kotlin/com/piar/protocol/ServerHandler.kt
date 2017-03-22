package com.piar.protocol

import io.netty.channel.ChannelFutureListener
import io.netty.util.CharsetUtil
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler



/**
 * Created by xingke on 2017/3/22.
 */
class ServerHandler : SimpleChannelInboundHandler<SimpleProtocol>() {
    @Throws(Exception::class)
    override fun channelRead0(ctx: ChannelHandlerContext, msg: SimpleProtocol) {
        println("server receive: " + msg)
        ctx.writeAndFlush(Unpooled.copiedBuffer("server get", CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE)
    }
}