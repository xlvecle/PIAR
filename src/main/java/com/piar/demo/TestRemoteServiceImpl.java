package com.piar.demo;

/**
 * Created by xingke on 2017/3/23.
 */
public class TestRemoteServiceImpl implements TestLocalService {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
