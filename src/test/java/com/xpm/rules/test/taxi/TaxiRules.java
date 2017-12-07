package com.xpm.rules.test.taxi;

import com.xpm.rules.controller.LinearRuleEngine;
import org.junit.Test;

import java.util.Date;

/**
 * Created by xupingmao on 2017/11/30.
 */
public class TaxiRules {

    @Test
    public void charge() {
        LinearRuleEngine ruleEngine = new LinearRuleEngine();

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
