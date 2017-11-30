package com.xpm.filtertree.impl;

import com.xpm.filtertree.Rule;

/**
 * Created by xupingmao on 2017/3/28.
 * 处理树的上下移动，失败之后尝试下一个
 */
public class MatchAnyRule extends AbstractMatchRule {

    public MatchAnyRule() {

    }

    public MatchAnyRule(Rule... rules) {
        super.setFilterList(rules);
    }

    @Override
    public MatchType getMatchType() {
        return MatchType.ANY;
    }

    @Override
    public String toString() {
//        String baseName = super.toString();
        if (ruleList == null) {
            return "()";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append("(");
        for (int i = 0; i < ruleList.size(); i++) {
            Rule rule = ruleList.get(i);
            sb.append(rule.toString());
            if (i != ruleList.size()-1) {
                sb.append("|");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
