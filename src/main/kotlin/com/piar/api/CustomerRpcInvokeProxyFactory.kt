package com.piar.api

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
                        proxy, method, args -> println("你正在被代理,这里将要被替换成调用远程的RPC方法 args: " + args?.joinToString(","))
                        val ret = args[0] as Int + args[1] as Int
                        println("result: $ret")
                        ret
                    })
        }
    }

}