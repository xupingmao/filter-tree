package com.xpm.rules;

/**
 * Created by xupingmao on 2017/3/28.
 * Java8 支持接口default实现，Java7及以前尚不支持，只能用抽象类
 */
public abstract class AbstractRule<T> implements Rule<T> {

    protected String name = null;

    @Override
    public String getName() {
        if (name != null) {
            return name;
        }
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getName();
    }
}
