package com.xpm.rules.test;

import com.xpm.rules.RuleEngine;
import com.xpm.rules.AbstractRule;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class PrintHelloRule extends AbstractRule<Void> {

    @Override
    public boolean match(Void globalContext) {
        return true;
    }

    @Override
    public void execute(RuleEngine ruleEngine, Void globalContext) {
        System.out.println("Hello,World");
    }
}
