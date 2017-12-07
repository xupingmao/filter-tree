package com.xpm.rules.test;

import com.xpm.rules.RuleEngine;
import com.xpm.rules.impl.AbstractRule;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class HaltRule extends AbstractRule<Void> {

    @Override
    public boolean match(Void globalContext) {
        return true;
    }

    @Override
    public void execute(RuleEngine ruleEngine, Void globalContext) {
        System.out.println("I will halt the world");
        ruleEngine.accept();
    }
}
