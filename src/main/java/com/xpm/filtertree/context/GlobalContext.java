package com.xpm.filtertree.context;

import com.google.common.collect.Lists;
import com.xpm.filtertree.Rule;

import java.util.List;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class GlobalContext {
    private String caseId;
    // 全局的实体可以放在这里
    // 用于调试测试
    private boolean debug = false;
    private int indent = -2;
    private List<Rule> executedRules = null;

    private List<LocalContext> localStack = Lists.newArrayList();

    private StringBuilder logMsgBuilder;

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

    public void logFilter(Rule rule) {
        if (executedRules == null) {
            executedRules = Lists.newArrayList();
        }
        executedRules.add(rule);
    }

    public List<Rule> getExecutedRules() {
        return executedRules;
    }

    public String getExecutedFiltersInfo() {
        if (executedRules == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Rule rule : executedRules) {
            sb.append(rule.getName());
            sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}
