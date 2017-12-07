package com.xpm.rules.controller;

import com.google.common.collect.Lists;
import com.xpm.rules.Rule;
import com.xpm.rules.RuleEngine;

import java.util.List;

/**
 * 线性执行规则引擎
 * Created by xupingmao on 2017/11/30.
 */
public class LinearRuleEngine implements RuleEngine {

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
                rule.execute(this, params);
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
