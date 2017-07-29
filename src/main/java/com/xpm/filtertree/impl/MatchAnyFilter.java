package com.xpm.filtertree.impl;

import com.xpm.filtertree.Filter;

/**
 * Created by xupingmao on 2017/3/28.
 * 处理树的上下移动，失败之后尝试下一个
 */
public class MatchAnyFilter extends AbstractMatchFilter {

    public MatchAnyFilter() {

    }

    public MatchAnyFilter(Filter... filters) {
        super.setFilterList(filters);
    }

    @Override
    public MatchType getMatchType() {
        return MatchType.ANY;
    }

    @Override
    public String toString() {
//        String baseName = super.toString();
        if (filterList == null) {
            return "()";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append("(");
        for (int i = 0; i < filterList.size(); i++) {
            Filter filter = filterList.get(i);
            sb.append(filter.toString());
            if (i != filterList.size()-1) {
                sb.append("|");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
