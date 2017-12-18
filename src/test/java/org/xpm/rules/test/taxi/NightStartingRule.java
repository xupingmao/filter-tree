package org.xpm.rules.test.taxi;

import org.xpm.rules.RuleEngine;
import org.xpm.rules.AbstractRule;

/**
 * Created by xupingmao on 2017/11/30.
 */
public class NightStartingRule extends AbstractRule<TaxiInfo> {

    @Override
    public boolean match(RuleEngine engine, TaxiInfo globalContext) {
        int hours = globalContext.getStartTime().getHours();
        return (hours >= 21 || hours < 6) && globalContext.getDistance() <= 3;
    }

    @Override
    public void execute(RuleEngine ruleEngine, TaxiInfo globalContext) {
        System.out.println("晚上起步价内");
    }
}
