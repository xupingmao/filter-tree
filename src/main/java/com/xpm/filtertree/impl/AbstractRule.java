package com.xpm.filtertree.impl;

import com.xpm.filtertree.Rule;
import com.xpm.filtertree.context.GlobalContext;

import java.util.List;

/**
 * Created by xupingmao on 2017/3/28.
 * Java8 支持接口default实现，Java7尚不支持，只能用抽象类
 */
public abstract class AbstractRule implements Rule {

    // 可以把以前的doFilter方法写成抽象方法
    // public abstract Set<String> execute(FilterChain filterChain, Map<String, Object> attrs);

    protected String name = null;

    @Override
    public boolean when(GlobalContext globalContext) {
        return true;
    }

    @Override
    public String getName() {
        if (name == null) {
            return getClass().getSimpleName();
        } else {
            return name;
        }
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public List<Rule> getChildren() {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
