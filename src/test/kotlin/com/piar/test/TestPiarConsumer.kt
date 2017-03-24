package com.piar.test

import com.piar.demo.TestLocalService
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by xingke on 2017/3/21.
 */

fun testRpcProxy() {
    var context = ClassPathXmlApplicationContext("classpath:test-consumer.xml")
    context.start()
    var testLocalService = context.getBean("testService") as TestLocalService
    testLocalService.add(1, 2, 3)
}


fun main(args: Array<String>) {
    testRpcProxy()
}
