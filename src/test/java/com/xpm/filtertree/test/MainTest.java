package com.xpm.filtertree.test;

import com.xpm.filtertree.Filter;
import com.xpm.filtertree.context.GlobalContext;
import com.xpm.filtertree.context.LocalContext;
import com.xpm.filtertree.exception.FilterBaseException;
import com.xpm.filtertree.impl.MatchAllFilter;
import com.xpm.filtertree.impl.MatchAnyFilter;
import org.junit.Test;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class MainTest {

    private PrintHelloFilter printHelloFilter = new PrintHelloFilter();
    private StopFilter stopFilter = new StopFilter();
    private HaltFilter haltFilter = new HaltFilter();

    @Test
    public void matchAny() throws FilterBaseException {
        Filter anyFilterChain = new MatchAnyFilter(printHelloFilter, stopFilter, haltFilter);
        GlobalContext globalContext = new GlobalContext();
        globalContext.setDebug(true);

        try {
            LocalContext localContext = new LocalContext();
            anyFilterChain.doFilter(globalContext, localContext);
        } finally {
            System.out.println(globalContext.getLogMsg());
        }
    }

    @Test
    public void matchAll() throws FilterBaseException {
        GlobalContext globalContext = new GlobalContext();
        globalContext.setDebug(true);

        LocalContext localContext = new LocalContext();
        try {
            Filter allFilterChain = new MatchAllFilter(printHelloFilter, stopFilter, haltFilter);
            allFilterChain.doFilter(globalContext, localContext);
        } finally {
            System.out.println(globalContext.getLogMsg());
        }
    }
}
