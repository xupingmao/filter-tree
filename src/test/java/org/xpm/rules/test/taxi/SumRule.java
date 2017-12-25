package org.xpm.rules.test.taxi;

import org.xpm.rules.RuleEngine;
import org.xpm.rules.AbstractRule;

import java.math.BigDecimal;

/**
 * Created by xupingmao on 2017/12/7.
 */
public class SumRule extends AbstractRule<TaxiInfo> {

    @Override
    public boolean match(TaxiInfo globalContext) {
        return true;
    }

    @Override
    public void execute(TaxiInfo globalContext) {
        BigDecimal totalPrice = globalContext.getStartingPrice().add(globalContext.getOverPrice());
        System.out.println("出租车总价：" + totalPrice);
    }
}
