package org.xpm.rules.test;

import org.xpm.rules.RuleEngine;
import org.xpm.rules.AbstractRule;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class HaltRule extends AbstractRule<Void> {

    @Override
    public boolean match(RuleEngine engine, Void globalContext) {
        return true;
    }

    @Override
    public void execute(RuleEngine ruleEngine, Void globalContext) {
        System.out.println("I will halt the world");
        ruleEngine.accept();
    }
}
