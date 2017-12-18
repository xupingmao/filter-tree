package org.xpm.rules.engine;

import com.google.common.collect.Lists;
import org.xpm.rules.Rule;
import org.xpm.rules.RuleEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线性执行规则引擎
 * Created by xupingmao on 2017/11/30.
 */
public class LinearRuleEngine implements RuleEngine {

    private boolean logProfile = true;

    private List<Rule> rules = Lists.newArrayList();

    /** 是否提前接受决策结果 */
    private boolean accepted = false;

    /**
     * 全排列变量
     */
    private Map<String, PermutationVar> permutationVars = new HashMap<String, PermutationVar>();
    private PermutationVar last = null;

    /**
     * TODO 组合变量
     */

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

    public void addPermutationVar(String key, Object... values) {
        last = new PermutationVar(last, values);
        permutationVars.put(key, last);
    }

    @Override
    public void execute(Object params) {
        if (permutationVars.isEmpty()) {
            execute0(params);
        } else {
            // 实现非确定性计算
            // TODO 考虑使用并行加速
            while (last.hasNext()) {
                last.next();
                // TODO 提前把value set进params，避免使用规则引擎的get来查找
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
        return (V) permutationVars.get(key).getCurrent();
    }

    @Override
    public void update(String key, Object value) {

    }
}
