package com.xpm.rules.impl;

import com.xpm.rules.Rule;

/**
 * Created by xupingmao on 2017/3/28.
 * Java8 支持接口default实现，Java7尚不支持，只能用抽象类
 */
public abstract class AbstractRule<T> implements Rule<T> {

    protected String name = null;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
