package com.piar.test

import com.piar.protocol.ServerHandler
import com.piar.protocol.SimpleProtocolDecoder
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.Channel
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.ByteToMessageDecoder


/**
 * Created by xingke on 2017/3/22.
 */

fun main(args: Array<String>) {
    val serverBootstrap = ServerBootstrap()
    val eventLoopGroup = NioEventLoopGroup()
    val childEventLoopGroup = NioEventLoopGroup()

    try {
        serverBootstrap
                .group(eventLoopGroup, childEventLoopGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<Channel>() {
                    @Throws(Exception::class)
                    override protected fun initChannel(ch: Channel) {
                        ch.pipeline().addLast(SimpleProtocolDecoder()) // 解码器
                        ch.pipeline().addLast(ServerHandler()) // 打印数据
                    }
                })

        val future = serverBootstrap.bind("localhost", 9999).sync()

        future.channel().closeFuture().sync()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            eventLoopGroup.shutdownGracefully().sync()
            childEventLoopGroup.shutdownGracefully().sync()
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

    }
}