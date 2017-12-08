package com.xpm.rules.engine;

import com.google.common.collect.Lists;
import com.xpm.rules.Rule;
import com.xpm.rules.RuleEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 线性执行规则引擎
 * Created by xupingmao on 2017/11/30.
 */
public class LinearRuleEngine implements RuleEngine {

    private boolean logProfile = true;

    private List<Rule> rules = Lists.newArrayList();

    /** 是否提前接受决策结果 */
    private boolean accepted = false;

    private Map<String, NonDeterministicVar> nonDeterministicVars = new HashMap<String, NonDeterministicVar>();
    private NonDeterministicVar last = null;

    public LinearRuleEngine() {

    }

    @Override
    public void accept() {
        accepted = true;
    }

    public void setLogProfile(boolean logProfile) {
        this.logProfile = logProfile;
    }

    @Override
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void addNonDeterministicVar(String key, Object... values) {
        last = new NonDeterministicVar(last, values);
        nonDeterministicVars.put(key, last);
    }

    @Override
    public void execute(Object params) {
        if (nonDeterministicVars.isEmpty()) {
            execute0(params);
        } else {
            // 实现非确定性计算
            // TODO 提前把value set进params
            while (last.hasNext()) {
                last.next();
                execute0(params);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void execute0(Object context) {
        for (Rule rule: rules) {
            if (rule.match(this, context)) {
                long startTime = System.currentTimeMillis();
                rule.execute(this, context);
                long costTime = System.currentTimeMillis() - startTime;
                if (logProfile) {
                    System.out.println(String.format("执行规则[%s]耗时[%s]ms", rule.getName(), costTime));
                }
                if (accepted) {
                    // 执行成功，终止规则
                    return;
                }
            }
        }
    }

    @Override
    public <V> V get(String key, Class<V> clazz) {
        return (V) nonDeterministicVars.get(key).getCurrent();
    }

    @Override
    public void update(String key, Object value) {

    }
}
