package com.piar.protocol

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.piar.server.deserializedString
import io.netty.channel.ChannelFutureListener
import io.netty.util.CharsetUtil
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.context.ApplicationContext
import java.io.ObjectInputStream


/**
 * Created by xingke on 2017/3/22.
 */
@Component
@ChannelHandler.Sharable
class ServerHandler : SimpleChannelInboundHandler<SimpleProtocol>() {

    @Autowired
    lateinit var appContext: ApplicationContext

    @Throws(Exception::class)
    override fun channelRead0(ctx: ChannelHandlerContext, msg: SimpleProtocol) {
        println("server receive: " + msg)
        var jobj = deserializedString(msg.content)
        var serviceName = jobj.getString("service")
        var methodName = jobj.getString("method")
        var args = jobj.getJSONArray("args")

        var bean = appContext.getBean(appContext.getBeanNamesForType(Class.forName(serviceName))[0])
        var method = bean::class.java.declaredMethods.filter { it.name == methodName }[0]

        var realArgs = args.zip(method.parameterTypes).map {
            it.first as Int
        }.toTypedArray()


        var invoke = method.invoke(realArgs)
        println("服务为: $serviceName")
        println("参数为: $jobj['args']")
        ctx.writeAndFlush(Unpooled.copiedBuffer("100", CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE)
    }
}