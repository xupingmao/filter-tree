package org.xpm.rules.test.queen;

import org.xpm.rules.RuleEngine;
import org.xpm.rules.engine.LinearRuleEngine;
import org.junit.Test;

/**
 * Created by xupingmao on 2017/12/8.
 */
public class QueenTest {

    public void addPermutationVar(LinearRuleEngine ruleEngine, String varName) {
        ruleEngine.addPermutationVar(varName, 0,1,2,3);
    }

    @Test
    public void queen() {
        LinearRuleEngine ruleEngine = new LinearRuleEngine();

        ruleEngine.addRule(new QueenRule());

        addPermutationVar(ruleEngine, "x1");
        addPermutationVar(ruleEngine, "x2");
        addPermutationVar(ruleEngine, "x3");
        addPermutationVar(ruleEngine, "y1");
        addPermutationVar(ruleEngine, "y2");
        addPermutationVar(ruleEngine, "y3");
        addPermutationVar(ruleEngine, "x4");
        addPermutationVar(ruleEngine, "y4");

        ruleEngine.setLogProfile(false);
        ruleEngine.execute(new QueenParam());
    }

}
