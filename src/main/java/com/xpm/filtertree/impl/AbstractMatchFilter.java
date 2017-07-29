package com.xpm.filtertree.impl;

import com.google.common.collect.Lists;
import com.xpm.filtertree.Filter;
import com.xpm.filtertree.context.GlobalContext;
import com.xpm.filtertree.context.LocalContext;
import com.xpm.filtertree.exception.FilterBaseException;
import com.xpm.filtertree.exception.HaltException;

import java.util.List;

/**
 * Created by xupingmao on 2017/3/28.
 * 匹配过滤器
 */
public abstract class AbstractMatchFilter extends AbstractFilter {

    enum MatchType {
        ANY, ALL
    }

    private String name = null;
    protected List<Filter> filterList = Lists.newArrayList();

    @Override
    public boolean hasChildren() {
        return true;
    }

    @Override
    public List<Filter> getChildren() {
        return filterList;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
        // 检测循环依赖
    }

    public void setFilterList(Filter...filters) {
        this.filterList = Lists.newArrayList(filters);
        // 检测循环依赖
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    private String getFilterName(Filter filter) {
        String name = filter.getName();
        if (name == null) {
            return filter.getClass().getSimpleName();
        }
        return name;
    }

    public abstract MatchType getMatchType();

    @Override
    public void doFilter(GlobalContext globalContext, LocalContext parentContext) throws FilterBaseException {
        // 初始化prevResult
        LocalContext lastResult = parentContext;
        FilterBaseException exception = null;

        if (globalContext.isDebug()) {
            globalContext.enterScope();
        }

        for (Filter filter : filterList) {
            // 处理临时变量localParam
            // 把上层的处理结果放入localParam供下一个filter使用
            // 比如搜索引擎，每次build可以先尝试从localParam中取builder,最后一个filter负责查询
            // 或关系属于开辟新上下文重试，相当于放弃前一个filter的结果
            LocalContext newLocal = new LocalContext();
            newLocal.setPrevResult(lastResult);
            newLocal.setName(filter.getName());

            // 调试
            if (globalContext.isDebug()) {
                globalContext.logFilter(filter);
                globalContext.log("<", getFilterName(filter), ">");
                globalContext.log("  prevChain:", newLocal.getFilterNameChain());
            }

            long startTimeMs = System.currentTimeMillis();
            try {
                filter.doFilter(globalContext, newLocal);
                // 执行子filter
                // 执行结果
                long costTimeMs = System.currentTimeMillis() - startTimeMs;
                lastResult = newLocal;
                if (getMatchType() == MatchType.ANY) {
                    return;
                }
            } catch (HaltException ex) {
                exception = ex;
                break;
            } catch (FilterBaseException ex) {
                if (getMatchType() == MatchType.ALL) {
                    lastResult = parentContext;
                } else {
                    exception = ex;
                    break;
                }
            } finally {
                if (globalContext.isDebug()) {
                    globalContext.log("</" + getFilterName(filter), ">");
                }
            }
        }

        if (globalContext.isDebug()) {
            globalContext.exitScope();
        }
        if (exception != null) {
            throw exception;
        }
    }

    @Override
    public String toString() {
//        String baseName = super.toString();
        if (filterList == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append("[");
        for (int i = 0; i < filterList.size(); i++) {
            Filter filter = filterList.get(i);
            sb.append(filter.toString());
            if (i != filterList.size()-1) {
                sb.append(",");
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
