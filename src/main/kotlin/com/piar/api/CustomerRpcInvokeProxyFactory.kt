package com.piar.api

import com.piar.server.methodSerialize
import com.piar.server.sendMsgToServer
import java.lang.reflect.Proxy

/**
 * Created by xingke on 2017/3/21.
 */
class CustomerRpcInvokeProxyFactory {

    companion object {
        @JvmStatic
        fun <T> getProxy(clazz: Class<T>): T {
            return clazz.cast(
                    Proxy.newProxyInstance( ClassLoader.getSystemClassLoader(), arrayOf(clazz)) {
                        proxy, method, args -> println("你正在被代理,去请求远程 args: " + args?.joinToString(","))
                        println(clazz.name)
                        val response = sendMsgToServer(methodSerialize(clazz.name, method.name, args))
                        println("server_response: $response")
                        if (method.returnType.name == "int") {
                            Integer.parseInt(response)
                        } else {
                            method.returnType.cast(response)
                        }
                    })
        }
    }

}