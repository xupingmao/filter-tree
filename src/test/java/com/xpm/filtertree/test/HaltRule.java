package com.xpm.filtertree.test;

import com.xpm.filtertree.context.GlobalContext;
import com.xpm.filtertree.context.LocalContext;
import com.xpm.filtertree.exception.RuleBaseException;
import com.xpm.filtertree.exception.HaltException;
import com.xpm.filtertree.impl.AbstractRule;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class HaltRule extends AbstractRule {
    @Override
    public void execute(GlobalContext globalContext, LocalContext localContext) throws RuleBaseException {
        System.out.println("I will halt the world");
        throw new HaltException("Halt");
    }
}
