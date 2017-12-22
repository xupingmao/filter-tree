package org.xpm.rules.utils;


import org.xpm.rules.Rule;

import java.nio.charset.Charset;

/**
 * Created by xupingmao on 2017/3/31.
 */
public final class RuleUtils {

    public static final int INDENT = 2;
    private static Charset charset = Charset.forName("UTF-8");

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

    public static String getSetMethodName(String name) {
        if (name == null || name.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        sb.append("set").append(chars);
        return sb.toString();
    }
}
