package com.xpm.filtertree.impl;

import com.google.common.collect.Lists;
import com.xpm.filtertree.Rule;
import com.xpm.filtertree.context.GlobalContext;
import com.xpm.filtertree.context.LocalContext;
import com.xpm.filtertree.exception.RuleBaseException;
import com.xpm.filtertree.exception.HaltException;

import java.util.List;

/**
 * Created by xupingmao on 2017/3/28.
 * 匹配过滤器
 */
public abstract class AbstractMatchRule extends AbstractRule {

    enum MatchType {
        ANY, ALL
    }

    private String name = null;
    protected List<Rule> ruleList = Lists.newArrayList();

    @Override
    public boolean hasChildren() {
        return true;
    }

    @Override
    public List<Rule> getChildren() {
        return ruleList;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
        // 检测循环依赖
    }

    public void setFilterList(Rule... rules) {
        this.ruleList = Lists.newArrayList(rules);
        // 检测循环依赖
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    private String getFilterName(Rule rule) {
        String name = rule.getName();
        if (name == null) {
            return rule.getClass().getSimpleName();
        }
        return name;
    }

    public abstract MatchType getMatchType();

    @Override
    public void execute(GlobalContext globalContext, LocalContext parentContext) throws RuleBaseException {
        // 初始化prevResult
        LocalContext lastResult = parentContext;
        RuleBaseException exception = null;

        if (globalContext.isDebug()) {
            globalContext.enterScope();
        }

        for (Rule rule : ruleList) {
            // 处理临时变量localParam
            // 把上层的处理结果放入localParam供下一个filter使用
            // 比如搜索引擎，每次build可以先尝试从localParam中取builder,最后一个filter负责查询
            // 或关系属于开辟新上下文重试，相当于放弃前一个filter的结果
            LocalContext newLocal = new LocalContext();
            newLocal.setPrevResult(lastResult);
            newLocal.setName(rule.getName());

            // 调试
            if (globalContext.isDebug()) {
                globalContext.logFilter(rule);
                globalContext.log("<", getFilterName(rule), ">");
                globalContext.log("  prevChain:", newLocal.getFilterNameChain());
            }

            long startTimeMs = System.currentTimeMillis();
            try {
                rule.execute(globalContext, newLocal);
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
            } catch (RuleBaseException ex) {
                if (getMatchType() == MatchType.ALL) {
                    lastResult = parentContext;
                } else {
                    exception = ex;
                    break;
                }
            } finally {
                if (globalContext.isDebug()) {
                    globalContext.log("</" + getFilterName(rule), ">");
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
        if (ruleList == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append("[");
        for (int i = 0; i < ruleList.size(); i++) {
            Rule rule = ruleList.get(i);
            sb.append(rule.toString());
            if (i != ruleList.size()-1) {
                sb.append(",");
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
