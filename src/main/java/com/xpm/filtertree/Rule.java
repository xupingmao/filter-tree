package com.xpm.filtertree;

import com.xpm.filtertree.context.GlobalContext;
import com.xpm.filtertree.context.LocalContext;
import com.xpm.filtertree.exception.RuleBaseException;

import java.util.List;

/**
 * 决策树过滤器，可以参考编译器的AST定义
 * 注意：Filter是无状态的，不要使用filter保存数据
 *
 * 从作用上讲，Filter可以分为两种
 * 1. 用于执行具体的任务的filter
 * 2. 用于组合的filter的filter，可以看成1的变种
 *
 * 目前组合MatchFilter的实现有两种
 * 1. MatchAny
 * 2. MatchAll
 *
 * Filter作为组合行为可以继续扩展，比如
 * 1. RepeatFilter 重复执行子filter
 * 2. RandomFilter 随机选取一个子filter执行
 * ...
 *
 * Created by xupingmao on 2017/3/28.
 */
public interface Rule {

    /**
     * Filter必须定义名称，方便记录日志，排除问题
     * @return
     */
    String getName();

    /**
     * 是否包含子filter
     * @return
     */
    boolean hasChildren();

    /**
     *
     * @return
     */
    List<Rule> getChildren();

    /**
     * 规则的条件，也就是前件
     * @param globalContext
     * @return
     */
    boolean when(GlobalContext globalContext);

    /**
     * 规则的结果，后件
     * @param globalContext 全局上下文
     * @param localContext 每个处理器独立的上下文,每次调用的时候new一个
     *              比如说下一个处理器依赖上一个处理器的执行结果,那么结果应该放在param而不是context里面
     *        localContext是一个链表，可以关联到前面执行过的所有的有效rule局部上下文
     * @return
     */
    void execute(GlobalContext globalContext, LocalContext localContext) throws RuleBaseException;

}
