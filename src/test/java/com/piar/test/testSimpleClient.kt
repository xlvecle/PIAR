
package com.piar.test

import com.piar.protocol.SimpleProtocolDecoder
import java.util.UUID
import io.netty.channel.socket.nio.NioSocketChannel
import com.sun.tools.internal.xjc.model.Multiplicity.group
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.util.CharsetUtil


/**
 * Created by xingke on 2017/3/22.
 */

fun main(args: Array<String>) {
    val bootstrap = Bootstrap()

    val eventLoopGroup = NioEventLoopGroup()

    try {
        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<Channel>() {
                    @Throws(Exception::class)
                    override fun initChannel(ch: Channel) {
                        ch.pipeline().addLast(SimpleProtocolDecoder()) // 编码器
                        ch.pipeline().addLast(object : SimpleChannelInboundHandler<ByteBuf>() {
                            override fun channelRead0(ctx: ChannelHandlerContext?, msg: ByteBuf) {
                                System.out.println("send success, response is: " + msg.toString(CharsetUtil.UTF_8))
                            }
                        }) // 接收服务端数据
                    }
                })
        val channelFuture = bootstrap.connect("localhost", 9999).sync()

        channelFuture.channel().writeAndFlush(CustomProtocol(1024L, UUID.randomUUID().toString(), "content detail"))

        channelFuture.channel().closeFuture().sync()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            eventLoopGroup.shutdownGracefully().sync()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}
