package com.xpm.filtertree.utils;


import com.xpm.filtertree.Rule;

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

    /**
     * 构造格式化的filter树图形
     * @param rule
     * @param indent
     * @param indentSize
     * @return
     */
    public static String buildFilterTree(Rule rule, boolean indent, int indentSize) {
        if (!indent) {
            return rule.toString();
        } else {
            return buildFilterTree1(rule, "", "|     ");
        }
    }

    public static String buildFilterTree(Rule rule) {
        return buildFilterTree(rule, true, 4);
    }

    private static String buildFilterTree0(Rule rule, int indent, int indentSize) {
        StringBuilder sb = new StringBuilder();
        String tagName = getClzName(rule) + ":" + rule.getName();

        String openTag = repeat(indent) + tagName;
        String closeTag = "";

        if (rule.hasChildren()) {
            sb.append(openTag);
            sb.append("\n");
            for (Rule f: rule.getChildren()) {
                sb.append(buildFilterTree0(f, indent+indentSize, indentSize));
            }
            sb.append(closeTag);
        } else {
            sb.append(openTag);
            sb.append(closeTag);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * 可以使用装饰器优化
     * @param rule
     * @param leftPad
     * @param indentPad
     * @return
     */
    private static String buildFilterTree1(Rule rule, String leftPad, String indentPad) {
        StringBuilder sb = new StringBuilder();
        String tagName = getClzName(rule) + ":" + rule.getName();

        String openTag = leftPad + tagName;
        String closeTag = "";

        if (rule.hasChildren()) {
            sb.append(openTag);
            sb.append("\n");
            leftPad += indentPad;
            for (Rule f: rule.getChildren()) {
                sb.append(buildFilterTree1(f, leftPad, indentPad));
            }
            sb.append(closeTag);
        } else {
            sb.append(openTag);
            sb.append(closeTag);
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String getClzName(Rule rule) {
        return rule.getClass().getSimpleName();
    }
}
