package com.xpm.rules;

/**
 * Created by xupingmao on 2017/11/30.
 */
public interface RuleEngine {

    Object execute(Object params);

    void addRule(Rule rule);

    void accept();

    /**
     * 获取全局参数
     * @param key
     * @param clazz
     * @param <V>
     * @return
     */
    <V> V get(String key, Class<V> clazz);

    /**
     * 更新全局数据库，以便触发关联规则
     * @param key
     * @param value
     */
    void update(String key, Object value);

}
