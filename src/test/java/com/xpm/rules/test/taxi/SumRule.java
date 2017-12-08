package com.xpm.rules.test.taxi;

import com.xpm.rules.RuleEngine;
import com.xpm.rules.AbstractRule;

import java.math.BigDecimal;

/**
 * Created by xupingmao on 2017/12/7.
 */
public class SumRule extends AbstractRule<TaxiInfo> {

    @Override
    public boolean match(RuleEngine engine, TaxiInfo globalContext) {
        return true;
    }

    @Override
    public void execute(RuleEngine ruleEngine, TaxiInfo globalContext) {
        BigDecimal totalPrice = globalContext.getStartingPrice().add(globalContext.getOverPrice());
        System.out.println("出租车总价：" + totalPrice);
    }
}
