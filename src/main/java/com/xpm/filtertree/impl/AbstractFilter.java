package com.xpm.filtertree.impl;

import com.xpm.filtertree.Filter;
import com.xpm.filtertree.context.GlobalContext;
import com.xpm.filtertree.context.LocalContext;

import java.util.List;

/**
 * Created by xupingmao on 2017/3/28.
 * Java8 支持接口default实现，Java7尚不支持，只能用抽象类
 * 实际干活的filter, 等同于以前的doQuery
 * 可以把以前的doQuery改成继承，对原来的结果加一层wrapper
 * 实际上buildQuery也可以使用这个类
 */
public abstract class AbstractFilter implements Filter {

    // 可以把以前的doFilter方法写成抽象方法
    // public abstract Set<String> doFilter(FilterChain filterChain, Map<String, Object> attrs);

    protected String name = null;

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
    public List<Filter> getChildren() {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
