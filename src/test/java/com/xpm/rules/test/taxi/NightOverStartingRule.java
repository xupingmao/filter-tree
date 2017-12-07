package com.xpm.rules.test.taxi;

import com.xpm.rules.RuleEngine;
import com.xpm.rules.AbstractRule;

import java.math.BigDecimal;

/**
 * Created by xupingmao on 2017/11/30.
 */
public class NightOverStartingRule extends AbstractRule<TaxiInfo> {
    @Override
    public boolean match(TaxiInfo globalContext) {
        int hours = globalContext.getStartTime().getHours();
        return (hours >= 21 || hours < 6) && globalContext.getDistance() > 3;
    }

    @Override
    public void execute(RuleEngine ruleEngine, TaxiInfo globalContext) {
        globalContext.setStartingPrice(new BigDecimal(10.0));
        globalContext.setOverPrice(new BigDecimal((globalContext.getDistance() - 3) * 3.5));
        System.out.println("晚上起步价外");
    }
}
