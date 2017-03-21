package com.piar.test

import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by xingke on 2017/3/21.
 */

fun testRpcProxy() {
    var context = ClassPathXmlApplicationContext("classpath:test-context.xml")
    context.start()
    var testLocalService = context.getBean("testService") as testLocalService
    testLocalService.add(1, 2)
}


fun main(args: Array<String>) {
    testRpcProxy()
}
