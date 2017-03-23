
package com.piar.test

import com.piar.protocol.SimpleProtocol
import com.piar.protocol.SimpleProtocolDecoder
import java.util.UUID
import io.netty.channel.socket.nio.NioSocketChannel
import com.sun.tools.internal.xjc.model.Multiplicity.group
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.handler.codec.MessageToByteEncoder
import io.netty.util.CharsetUtil
import java.util.concurrent.TimeUnit


/**
 * Created by xingke on 2017/3/22.
 */

fun sendMsgToServer(formatedString: String): String {
    var response = ""
    val bootstrap = Bootstrap()
    val eventLoopGroup = NioEventLoopGroup()

    try {
        bootstrap.option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK,10 * 64 * 1024)
        bootstrap.option(ChannelOption.TCP_NODELAY, true)
        bootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<Channel>() {
                    @Throws(Exception::class)
                    override fun initChannel(ch: Channel) {
                        ch.pipeline().addLast(object : MessageToByteEncoder<SimpleProtocol>() {
                            override fun encode(ctx: ChannelHandlerContext?, msg: SimpleProtocol, out: ByteBuf) {
                                out.writeLong(msg.version)
                                out.writeBytes(msg.header.toByteArray())
                                out.writeBytes(msg.content.toByteArray())
                            }
                        }) // 编码器
                        ch.pipeline().addLast(object : SimpleChannelInboundHandler<ByteBuf>() {
                            override fun channelRead0(ctx: ChannelHandlerContext?, msg: ByteBuf) {
//                                System.out.println("send success, response is: " + msg.toString(CharsetUtil.UTF_8))
                                response = msg.toString(CharsetUtil.UTF_8)
                            }
                        }) // 接收服务端数据
                    }
                })
        val channelFuture = bootstrap.connect("localhost", 9999).sync().channel()

        channelFuture.writeAndFlush(SimpleProtocol(1024L, UUID.randomUUID().toString(), formatedString))

        channelFuture.closeFuture().sync()

        return response
    } catch (e: Exception) {
        e.printStackTrace()
        throw RuntimeException("请求失败")
    } finally {
        try {
            eventLoopGroup.shutdownGracefully(1, 1, TimeUnit.MILLISECONDS).sync()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}

fun main(args: Array<String>) {
    println(sendMsgToServer("xingke"))
}
