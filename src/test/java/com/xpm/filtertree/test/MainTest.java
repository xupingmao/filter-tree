package com.xpm.filtertree.test;

import com.xpm.filtertree.Rule;
import com.xpm.filtertree.context.GlobalContext;
import com.xpm.filtertree.context.LocalContext;
import com.xpm.filtertree.exception.RuleBaseException;
import com.xpm.filtertree.exception.HaltException;
import com.xpm.filtertree.impl.MatchAllRule;
import com.xpm.filtertree.impl.MatchAnyRule;
import org.junit.Test;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class MainTest {

    private PrintHelloRule printHelloFilter = new PrintHelloRule();
    private StopRule stopFilter = new StopRule();
    private HaltRule haltFilter = new HaltRule();

    @Test
    public void matchAny() throws RuleBaseException {
        Rule anyRuleChain = new MatchAnyRule(printHelloFilter, stopFilter, haltFilter);
        GlobalContext globalContext = new GlobalContext();
        globalContext.setDebug(true);

        try {
            LocalContext localContext = new LocalContext();
            anyRuleChain.execute(globalContext, localContext);
        } finally {
            System.out.println(globalContext.getLogMsg());
        }
    }

    @Test(expected = HaltException.class)
    public void matchAll_halt() throws RuleBaseException {
        GlobalContext globalContext = new GlobalContext();
        globalContext.setDebug(true);

        LocalContext localContext = new LocalContext();
        try {
            Rule allRuleChain = new MatchAllRule(printHelloFilter, stopFilter, haltFilter);
            allRuleChain.execute(globalContext, localContext);
        } finally {
            System.out.println(globalContext.getLogMsg());
        }
    }
}
