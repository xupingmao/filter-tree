package com.xpm.filtertree.context;

import com.google.common.collect.Lists;
import com.xpm.filtertree.Filter;

import java.util.List;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class GlobalContext {
    private String caseId;
    // 全局的实体可以放在这里
    private int max;

    // 用于调试测试
    private boolean debug = false;
    private int indent = -2;
    private List<Filter> executedFilters = null;
    // 扫荡者，过滤了所有输入
    private List<String> cleanerList = Lists.newArrayList();
    private StringBuilder logMsgBuilder;

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    // 调试方法
    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void enterScope() {
        indent += 2;
    }

    public void exitScope() {
        indent -= 2;
    }

    public int getIndent() {
        return indent;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getIndentStr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public void log(Object...args) {
        if (logMsgBuilder == null) {
            logMsgBuilder = new StringBuilder();
        }
        logMsgBuilder.append(getIndentStr());
        for (int i = 0; i < args.length; i++) {
            logMsgBuilder.append(args[i]);
        }
        logMsgBuilder.append('\n');
    }

    public String getLogMsg () {
        if (logMsgBuilder == null) {
            return "";
        } else {
            return logMsgBuilder.toString();
        }
    }

    public void logFilter(Filter filter) {
        if (executedFilters == null) {
            executedFilters = Lists.newArrayList();
        }
        executedFilters.add(filter);
    }

    public List<Filter> getExecutedFilters() {
        return executedFilters;
    }

    public String getExecutedFiltersInfo() {
        if (executedFilters == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Filter filter: executedFilters) {
            sb.append(filter.getName());
            sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public void logCleaner(String name) {
        cleanerList.add(name);
    }

    public List<String> getCleanerList() {
        return cleanerList;
    }
}
