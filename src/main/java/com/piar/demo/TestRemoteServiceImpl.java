package com.piar.demo;

/**
 * Created by xingke on 2017/3/23.
 */
public class TestRemoteServiceImpl implements TestLocalService {

    @Override
    public Integer add(Integer a, Integer b, Integer c) {
        return a + b + c;
    }
}
