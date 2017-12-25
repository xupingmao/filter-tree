package org.xpm.rules;

import java.util.Set;

/**
 * 基本规则
 * Created by xupingmao on 2017/3/28.
 */
public interface Rule<T extends RuleContext> {

    /**
     * 必须定义名称，方便记录日志，排除问题
     * @return
     */
    String getName();

    /**
     * 观察的变量名，如果这些变量发生变化，规则引擎会通知该规则
     * @return
     */
    Set<String> getWatchedKeys();

    /**
     * 规则的条件，也就是前件
     * 抽离出来可以让控制机构生成路由
     * @param param
     * @return
     */
    boolean match(T param);

    /**
     * 规则的操作，后件，如果执行失败，抛出异常
     * @param param 全局上下文
     * @return
     */
    void execute(T param);

}
