package com.xpm.rules.context;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class GlobalContext {
    private boolean debug = false;
    private boolean success = false;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
