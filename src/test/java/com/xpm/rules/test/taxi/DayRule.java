package com.xpm.rules.test.taxi;

import com.xpm.rules.RuleEngine;
import com.xpm.rules.impl.AbstractRule;

/**
 * Created by xupingmao on 2017/11/30.
 */
public class DayRule extends AbstractRule<TaxiInfo> {

    @Override
    public boolean match(TaxiInfo globalContext) {
        int hours = globalContext.getStartTime().getHours();
        return hours >= 6 && hours < 21;
    }

    @Override
    public void execute(RuleEngine ruleEngine, TaxiInfo globalContext) {
        System.out.println("It is day rule");
    }
}
