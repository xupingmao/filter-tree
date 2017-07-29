package com.xpm.filtertree.impl;

import com.google.common.collect.Lists;
import com.xpm.filtertree.Filter;
import com.xpm.filtertree.impl.MatchAllFilter;
import com.xpm.filtertree.impl.MatchAnyFilter;

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
     * @param filters
     * @return
     */
    public MatchAnyFilter buildParallels(List<Filter> filters) {
        MatchAnyFilter matchFilter = new MatchAnyFilter();
        matchFilter.setName(rootName);
        List<MatchAllFilter> matchAllFilters = buildParallels0(filters, 0);
        matchFilter.getChildren().addAll(matchAllFilters);
        return matchFilter;
    }

    private List<MatchAllFilter> buildParallels0(List<Filter> filters, int start) {
        if (start == filters.size()) {
            // 终止条件
            // 最终结果数量和终止条件数量一致，所以在此处创建对象
            MatchAllFilter matchAll = new MatchAllFilter();
            matchAll.setName(matchAllName);
            return Lists.newArrayList(matchAll);
        } else {
            Filter current = filters.get(start);
            List<MatchAllFilter> allFilters = Lists.newArrayList();

            if (current instanceof MatchAnyFilter) {
                // 只处理Any的全排列
                for (Filter choice: current.getChildren()) {
                    List<MatchAllFilter> matchAllFilters = buildParallels0(filters, start + 1);
                    for (MatchAllFilter maf: matchAllFilters) {
                        maf.getChildren().add(0, choice);
                    }
                    allFilters.addAll(matchAllFilters);
                }
            } else {
                allFilters = buildParallels0(filters, start+1);
                for (MatchAllFilter maf: allFilters) {
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
     * @param filters
     * @return
     */
    public Filter buildTree(List<Filter> filters) {
        MatchAnyFilter rootFilter = new MatchAnyFilter();
        rootFilter.setName(matchAnyName);
        buildTree0(rootFilter, filters, 0);
        // 第一个分支是filters[0]
        // 1. 第一个分支是All
        // 2. 第一个分支是Any
        // 3. 第一个分支是Filter
        // 三种情况都是直接返回这个分支
        return rootFilter.getChildren().get(0);
    }

    private void buildTree0(Filter parentFilter, List<Filter> filters, int start) {
        if (start >= filters.size()) {
            return;
        }
        Filter choice = filters.get(start);
//        System.out.println(start+":" + choice);
        if (choice instanceof MatchAnyFilter) {
            // 展开可能的分支
            MatchAnyFilter anyFilter = new MatchAnyFilter();
            if (start == 0) {
                // 目标树的名称
                anyFilter.setName(rootName);
            } else {
                anyFilter.setName(matchAnyName);
            }
            for (Filter subChoice : choice.getChildren()) {
                // 构建子树
                MatchAllFilter matchAllFilter = new MatchAllFilter();
                matchAllFilter.setName(matchAllName);
                matchAllFilter.getChildren().add(subChoice);
                buildTree0(matchAllFilter, filters, start+1);
                // 优化只有一个子节点的matchAll
                if (matchAllFilter.getChildren().size() == 1) {
                    anyFilter.getChildren().add(subChoice);
                } else {
                    anyFilter.getChildren().add(matchAllFilter);
                }
            }
            parentFilter.getChildren().add(anyFilter);
        } else {
            parentFilter.getChildren().add(choice);
            buildTree0(parentFilter, filters, start+1);
        }
    }

}
