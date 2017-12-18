package org.xpm.rules.test.queen;

import org.xpm.rules.RuleEngine;
import org.xpm.rules.engine.LinearRuleEngine;
import org.junit.Test;

/**
 * Created by xupingmao on 2017/12/8.
 */
public class QueenTest {

    public void addNonDeterministicVar(LinearRuleEngine ruleEngine, String varName) {
        ruleEngine.addNonDeterministicVar(varName, 0,1,2,3,4,5,6,7);
    }

    @Test
    public void queen() {
        LinearRuleEngine ruleEngine = new LinearRuleEngine();

        ruleEngine.addRule(new QueenRule());

        addNonDeterministicVar(ruleEngine, "x1");
        addNonDeterministicVar(ruleEngine, "x2");
        addNonDeterministicVar(ruleEngine, "x3");
        addNonDeterministicVar(ruleEngine, "x4");
        addNonDeterministicVar(ruleEngine, "y1");
        addNonDeterministicVar(ruleEngine, "y2");
        addNonDeterministicVar(ruleEngine, "y3");
        addNonDeterministicVar(ruleEngine, "y4");

        ruleEngine.setLogProfile(false);
        ruleEngine.execute(null);
    }

}
