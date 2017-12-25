package org.xpm.rules;

/**
 * Created by xupingmao on 2017/12/25.
 */
public class RuleContext {

    /**
     * 是否终止执行
     */
    protected boolean stopped;
    /**
     * 是否重新规划
     */
    protected boolean needRePlan;

    public void stop() {
        stopped = true;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setNeedRePlan(boolean needRePlan) {
        this.needRePlan = needRePlan;
    }

    public boolean isNeedRePlan() {
        return needRePlan;
    }
}
