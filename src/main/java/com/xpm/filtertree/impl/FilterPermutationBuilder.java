package com.xpm.filtertree.impl;

import com.google.common.collect.Lists;
import com.xpm.filtertree.Rule;

import java.util.List;

/**
 * Created by xupingmao on 2017/3/30.
 */
public class FilterPermutationBuilder {

    private String matchAnyName = "any";
    private String matchAllName = "all";
    private String rootName;

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getRootName() {
        return rootName;
    }

    public void setMatchAllName(String matchAllName) {
        this.matchAllName = matchAllName;
    }

    public void setMatchAnyName(String matchAnyName) {
        this.matchAnyName = matchAnyName;
    }

    /**
     * 构造平行的filter
     * 输入
     *     A = A1 | A2
     *     B = B1 | B2
     * 输出
     *    R = F1 | F2 | F3 | F4
     *    F1 = A1 B1
     *    F2 = A1 B2
     *    F3 = A2 B1
     *    F4 = A2 B2
     *
     * @param rules
     * @return
     */
    public MatchAnyRule buildParallels(List<Rule> rules) {
        MatchAnyRule matchFilter = new MatchAnyRule();
        matchFilter.setName(rootName);
        List<MatchAllRule> matchAllFilters = buildParallels0(rules, 0);
        matchFilter.getChildren().addAll(matchAllFilters);
        return matchFilter;
    }

    private List<MatchAllRule> buildParallels0(List<Rule> rules, int start) {
        if (start == rules.size()) {
            // 终止条件
            // 最终结果数量和终止条件数量一致，所以在此处创建对象
            MatchAllRule matchAll = new MatchAllRule();
            matchAll.setName(matchAllName);
            return Lists.newArrayList(matchAll);
        } else {
            Rule current = rules.get(start);
            List<MatchAllRule> allFilters = Lists.newArrayList();

            if (current instanceof MatchAnyRule) {
                // 只处理Any的全排列
                for (Rule choice: current.getChildren()) {
                    List<MatchAllRule> matchAllFilters = buildParallels0(rules, start + 1);
                    for (MatchAllRule maf: matchAllFilters) {
                        maf.getChildren().add(0, choice);
                    }
                    allFilters.addAll(matchAllFilters);
                }
            } else {
                allFilters = buildParallels0(rules, start+1);
                for (MatchAllRule maf: allFilters) {
                    maf.getChildren().add(0, current);
                }
            }
            return allFilters;
        }
    }

    /**
     * 构造树形的filter
     * 输入
     *      A = A1 | A2
     *      B = B1 | B2
     *      C = C1 | C2
     * 输出
     *      Step1. R = A1 | A2
     *      Step2. R = A1 B | A2 B
     *      Step3. R =  A1 (B1 C | B2 C)
     *                  A2 (B1 C | B2 C)
     *
     * @param rules
     * @return
     */
    public Rule buildTree(List<Rule> rules) {
        MatchAnyRule rootFilter = new MatchAnyRule();
        rootFilter.setName(matchAnyName);
        buildTree0(rootFilter, rules, 0);
        // 第一个分支是filters[0]
        // 1. 第一个分支是All
        // 2. 第一个分支是Any
        // 3. 第一个分支是Filter
        // 三种情况都是直接返回这个分支
        return rootFilter.getChildren().get(0);
    }

    private void buildTree0(Rule parentRule, List<Rule> rules, int start) {
        if (start >= rules.size()) {
            return;
        }
        Rule choice = rules.get(start);
//        System.out.println(start+":" + choice);
        if (choice instanceof MatchAnyRule) {
            // 展开可能的分支
            MatchAnyRule anyFilter = new MatchAnyRule();
            if (start == 0) {
                // 目标树的名称
                anyFilter.setName(rootName);
            } else {
                anyFilter.setName(matchAnyName);
            }
            for (Rule subChoice : choice.getChildren()) {
                // 构建子树
                MatchAllRule matchAllFilter = new MatchAllRule();
                matchAllFilter.setName(matchAllName);
                matchAllFilter.getChildren().add(subChoice);
                buildTree0(matchAllFilter, rules, start+1);
                // 优化只有一个子节点的matchAll
                if (matchAllFilter.getChildren().size() == 1) {
                    anyFilter.getChildren().add(subChoice);
                } else {
                    anyFilter.getChildren().add(matchAllFilter);
                }
            }
            parentRule.getChildren().add(anyFilter);
        } else {
            parentRule.getChildren().add(choice);
            buildTree0(parentRule, rules, start+1);
        }
    }

}
