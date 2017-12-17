package org.xpm.rules.test.queen;

import org.xpm.rules.engine.LinearRuleEngine;
import org.junit.Test;

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
