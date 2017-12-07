package com.xpm.rules.test;

import com.xpm.rules.RuleEngine;
import com.xpm.rules.impl.AbstractRule;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class StopRule extends AbstractRule<Void> {

    @Override
    public boolean match(Void globalContext) {
        return true;
    }

    @Override
    public void execute(RuleEngine ruleEngine, Void globalContext) {
        System.out.println("I'm Bad, I want to stop the filter chain");
        ruleEngine.accept();
    }
}
