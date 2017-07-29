package com.xpm.filtertree.test;

import com.xpm.filtertree.context.GlobalContext;
import com.xpm.filtertree.context.LocalContext;
import com.xpm.filtertree.exception.FilterBaseException;
import com.xpm.filtertree.impl.AbstractFilter;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class StopFilter extends AbstractFilter {
    @Override
    public void doFilter(GlobalContext globalContext, LocalContext localContext) throws FilterBaseException {
        System.out.println("I'm Bad, I want to stop the filter chain");
        throw new FilterBaseException("Just stop");
    }
}
