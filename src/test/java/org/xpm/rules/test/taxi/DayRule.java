package org.xpm.rules.test.taxi;

import org.xpm.rules.RuleEngine;
import org.xpm.rules.AbstractRule;

/**
 * Created by xupingmao on 2017/11/30.
 */
public class DayRule extends AbstractRule<TaxiInfo> {

    @Override
    public boolean match(RuleEngine engine, TaxiInfo globalContext) {
        int hours = globalContext.getStartTime().getHours();
        return hours >= 6 && hours < 21;
    }

    @Override
    public void execute(RuleEngine ruleEngine, TaxiInfo globalContext) {
        System.out.println("It is day rule");
    }
}
