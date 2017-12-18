package org.xpm.rules.utils;


import org.xpm.rules.Rule;

/**
 * Created by xupingmao on 2017/3/31.
 */
public final class RuleUtils {

    public static final int INDENT = 2;

    public static String repeat(int size, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static String repeat(int size) {
        return repeat(size, ' ');
    }

    private static String getClzName(Rule rule) {
        return rule.getClass().getSimpleName();
    }
}
