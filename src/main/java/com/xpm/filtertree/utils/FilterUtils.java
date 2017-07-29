package com.xpm.filtertree.utils;


import com.xpm.filtertree.Filter;

/**
 * Created by xupingmao on 2017/3/31.
 */
public final class FilterUtils {

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
     * @param filter
     * @param indent
     * @param indentSize
     * @return
     */
    public static String buildFilterTree(Filter filter, boolean indent, int indentSize) {
        if (!indent) {
            return filter.toString();
        } else {
            return buildFilterTree1(filter, "", "|     ");
        }
    }

    public static String buildFilterTree(Filter filter) {
        return buildFilterTree(filter, true, 4);
    }

    private static String buildFilterTree0(Filter filter, int indent, int indentSize) {
        StringBuilder sb = new StringBuilder();
        String tagName = getClzName(filter) + ":" + filter.getName();

        String openTag = repeat(indent) + tagName;
        String closeTag = "";

        if (filter.hasChildren()) {
            sb.append(openTag);
            sb.append("\n");
            for (Filter f:filter.getChildren()) {
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
     * @param filter
     * @param leftPad
     * @param indentPad
     * @return
     */
    private static String buildFilterTree1(Filter filter, String leftPad, String indentPad) {
        StringBuilder sb = new StringBuilder();
        String tagName = getClzName(filter) + ":" + filter.getName();

        String openTag = leftPad + tagName;
        String closeTag = "";

        if (filter.hasChildren()) {
            sb.append(openTag);
            sb.append("\n");
            leftPad += indentPad;
            for (Filter f:filter.getChildren()) {
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

    private static String getClzName(Filter filter) {
        return filter.getClass().getSimpleName();
    }
}
