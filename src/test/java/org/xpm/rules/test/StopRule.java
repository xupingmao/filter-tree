package org.xpm.rules.test;

import org.xpm.rules.RuleContext;
import org.xpm.rules.RuleEngine;
import org.xpm.rules.AbstractRule;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class StopRule extends AbstractRule<RuleContext> {

    @Override
    public boolean match(RuleContext globalContext) {
        return true;
    }

    @Override
    public void execute(RuleContext globalContext) {
        System.out.println("I'm Bad, I want to stop the filter chain");
        globalContext.stop();
    }
}
