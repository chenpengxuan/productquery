package com.ymatou.productquery.infrastructure.util;

/**
 * Created by zhangyong on 2017/4/25.
 */
public class Tuple<A, B> {
    public final A first;
    public final B second;

    public Tuple(A a, B b) {
        this.first = a;
        this.second = b;
    }
}
