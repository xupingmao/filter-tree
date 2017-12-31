package org.xpm.rules.engine;

import com.google.common.collect.Lists;
import org.xpm.rules.Rule;
import org.xpm.rules.RuleContext;
import org.xpm.rules.RuleEngine;
import org.xpm.rules.utils.RuleUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private Map<String, PermutationVarGenerator> permutationVars = new HashMap<String, PermutationVarGenerator>();
    private PermutationVarGenerator last = null;

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
        last = new PermutationVarGenerator(last, values);
        last.setName(key);
        permutationVars.put(key, last);
    }

    @Override
    public void execute(RuleContext params) {
        if (permutationVars.isEmpty()) {
            execute0(params);
        } else {
            // 实现非确定性计算
            // TODO 考虑使用并行加速
            while (last.hasNext()) {
                last.next();
                // 提前把value set进params，避免使用规则引擎的get来查找
                setProperties(params, last);
                execute0(params);
            }
        }
    }

    private void setProperties(Object params, PermutationVarGenerator permutationVars) {
        if (params == null) {
            return;
        }
        while (permutationVars != null) {
            try {
                Object value = permutationVars.getCurrent();
                if (value != null) {
                    String methodName = RuleUtils.getSetMethodName(permutationVars.getName());
                    Method method = params.getClass().getMethod(methodName, value.getClass());
                    method.invoke(params, value);
                }
            } catch (NoSuchMethodException e) {
                // do Nothing
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            permutationVars = permutationVars.getParent();
        }
    }

    @SuppressWarnings("unchecked")
    public void execute0(RuleContext context) {
        // TODO 先计算出规则链再执行
        do {
            context.setNeedRePlan(false);
            for (Rule rule: rules) {
                if (rule.match(context)) {
                    executeRule(context, rule);
                    if (context.isStopped()) {
                        // 执行成功，终止规则
                        return;
                    }
                    if (context.isNeedRePlan()) {
                        // 重新规划线路
                        break;
                    }
                }
            }
        } while (context.isNeedRePlan());
    }

    @SuppressWarnings("unchecked")
    private void executeRule(RuleContext context, Rule rule) {
        long startTime = System.currentTimeMillis();
        rule.execute(context);
        long costTime = System.currentTimeMillis() - startTime;
        if (logProfile) {
            System.out.println(String.format("执行规则[%s]耗时[%s]ms", rule.getName(), costTime));
        }
    }

    @Override
    public <V> V get(String key, Class<V> clazz) {
        return (V) permutationVars.get(key).getCurrent();
    }

    @Override
    public void update(String key, Object value) {
        // TODO 更新全局上下文
    }

    @Override
    public void replan() {
        // TODO 重新生成规则链执行
    }
}
