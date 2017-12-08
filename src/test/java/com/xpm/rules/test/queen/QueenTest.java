package com.xpm.rules.test.queen;

import com.xpm.rules.RuleEngine;
import com.xpm.rules.engine.LinearRuleEngine;
import com.xpm.rules.test.taxi.*;
import org.junit.Test;

import java.util.Date;

/**
 * Created by xupingmao on 2017/12/8.
 */
public class QueenTest {

    @Test
    public void queen() {
        LinearRuleEngine ruleEngine = new LinearRuleEngine();

        ruleEngine.addRule(new QueenRule());
        ruleEngine.addNonDeterministicVar("x1", 0,1,2,3,4,5,6,7);
        ruleEngine.addNonDeterministicVar("y1", 0,1,2,3,4,5,6,7);
        ruleEngine.execute(null);
    }

}
