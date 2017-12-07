package com.xpm.rules.test;

import com.xpm.rules.engine.LinearRuleEngine;
import org.junit.Test;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class MainTest {

    @Test
    public void linearRuleEngine() {
        LinearRuleEngine engine = new LinearRuleEngine();

        engine.addRule(new PrintHelloRule());
        engine.addRule(new HaltRule());

        engine.execute(null);
    }
}
