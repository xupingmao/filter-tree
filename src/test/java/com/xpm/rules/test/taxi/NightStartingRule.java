package com.xpm.rules.test.taxi;

import com.xpm.rules.RuleEngine;
import com.xpm.rules.impl.AbstractRule;

/**
 * Created by xupingmao on 2017/11/30.
 */
public class NightStartingRule extends AbstractRule<TaxiInfo> {

    @Override
    public boolean match(TaxiInfo globalContext) {
        int hours = globalContext.getStartTime().getHours();
        return (hours >= 21 || hours < 6) && globalContext.getDistance() <= 3;
    }

    @Override
    public void execute(RuleEngine ruleEngine, TaxiInfo globalContext) {
        System.out.println("晚上起步价内");
    }
}
