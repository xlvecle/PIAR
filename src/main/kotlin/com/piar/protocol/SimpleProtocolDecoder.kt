package com.piar.protocol

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

/**
 * Created by xingke on 2017/3/22.
 */
class SimpleProtocolDecoder: ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext?, input: ByteBuf, out: MutableList<Any>) {
        try {
            val version = input.readLong() // 读取version

            val headerBytes = ByteArray(36)
            input.readBytes(headerBytes) // 读取header

            val header = String(headerBytes)

            val contentBytes = ByteArray(input.readableBytes()) // 读取content
            input.readBytes(contentBytes)

            out.add(SimpleProtocol(version, header, String(contentBytes)))
        } catch (e: Exception) {
        }

    }
}