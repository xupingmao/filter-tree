package com.xpm.rules.engine;

import com.google.common.collect.Lists;
import com.xpm.rules.Rule;
import com.xpm.rules.RuleEngine;

import java.util.List;

/**
 * 线性执行规则引擎
 * Created by xupingmao on 2017/11/30.
 */
public class LinearRuleEngine implements RuleEngine {

    private boolean logProfile = true;

    private List<Rule> rules = Lists.newArrayList();

    private boolean accepted = false;

    public LinearRuleEngine() {

    }

    @Override
    public void accept() {
        accepted = true;
    }

    @Override
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object execute(Object params) {
        for (Rule rule: rules) {
            if (rule.match(params)) {
                long startTime = System.currentTimeMillis();
                rule.execute(this, params);
                long costTime = System.currentTimeMillis() - startTime;

                if (logProfile) {
                    System.out.println(String.format("执行规则[%s]耗时[%s]ms", rule.getName(), costTime));
                }

                if (accepted) {
                    // 执行成功，终止规则
                    return params;
                }
            }
        }
        return null;
    }

    @Override
    public <V> V get(String key, Class<V> clazz) {
        return null;
    }

    @Override
    public void update(String key, Object value) {

    }
}
