package com.xpm.filtertree.impl;

import com.xpm.filtertree.Rule;

/**
 * Created by xupingmao on 2017/3/28.
 * 处理树的水平移动,失败之后退出
 */
public class MatchAllRule extends AbstractMatchRule {

    public MatchAllRule() {

    }

    public MatchAllRule(Rule... rules) {
        super.setFilterList(rules);
    }

    @Override
    public MatchType getMatchType() {
        return MatchType.ALL;
    }

    @Override
    public String toString() {
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
                sb.append(" ");
            }
        }
        sb.append(')');
        return sb.toString();
    }

}
