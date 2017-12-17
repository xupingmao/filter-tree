package org.xpm.rules.test.taxi;

import org.xpm.rules.RuleEngine;
import org.xpm.rules.engine.LinearRuleEngine;
import org.junit.Test;

import java.util.Date;

/**
 * Created by xupingmao on 2017/11/30.
 */
public class TaxiRules {

    @Test
    public void charge() {
        RuleEngine ruleEngine = new LinearRuleEngine();

        ruleEngine.addRule(new DayRule());
        ruleEngine.addRule(new NightStartingRule());
        ruleEngine.addRule(new NightOverStartingRule());
        ruleEngine.addRule(new SumRule());
        TaxiInfo info = new TaxiInfo();
        info.setStartTime(new Date(2016,10,1,23,0));
        info.setDistance(10);
        ruleEngine.execute(info);
    }
}
