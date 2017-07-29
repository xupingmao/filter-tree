package com.xpm.filtertree.context;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by xupingmao on 2017/7/28.
 */
public class LocalContext {

    // 默认有结果，如果没有结果，不会被传递到下一个Context
    private boolean hasResult = true;
    // filter处理的结果集
    private Set<String> idSet;

    // 同层上一个的filter处理结果，由框架(MatchAll)传递
    private LocalContext prevResult;

    // filter的name
    private String name;

    // 扩展调试信息
    private StringBuilder message;

    public boolean hasResult() {
        return hasResult;
    }

    public void setHasResult(boolean hasResult) {
        this.hasResult = hasResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getIdSet() {
        if (idSet == null) {
            idSet = Sets.newLinkedHashSet();
        }
        return idSet;
    }

    public void setIdSet(Set<String> idSet) {
        this.idSet = idSet;
    }

    /**
     * 获得一份上一个节点的结果拷贝
     * @return
     */
    public Set<String> getPrevIdSetCopy(){
        if (prevResult == null) {
            return Sets.newLinkedHashSet();
        }
        if (prevResult.getIdSet() == null) {
            return Sets.newLinkedHashSet();
        }
        return Sets.newLinkedHashSet(prevResult.getIdSet());
    }


    public void setPrevResult(LocalContext prevResult) {
        this.prevResult = prevResult;
    }

    public LocalContext getPrevResult() {
        return prevResult;
    }

    public List<String> getFilterNameChain() {
        List<String> nameChain = Lists.newArrayList();
        LocalContext prevResult = getPrevResult();
        int count = 0;

        while (prevResult != null && count <= 10) {
            String name = prevResult.getName();
            nameChain.add(name);
            prevResult = prevResult.getPrevResult();
            count++;
        }
        Collections.reverse(nameChain);
        return nameChain;
    }

    public void appendMessage(String msg) {
        if (message == null) {
            message = new StringBuilder();
        }
        message.append(msg);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("idSetSize=");
        sb.append(getIdSet().size());

        if (getIdSet().size() < 10) {
            sb.append(",");
            sb.append("idSet=");
            sb.append(getIdSet());
        }

        if (message != null) {
            sb.append(",");
            sb.append("message=");
            sb.append(message.toString());
        }
        sb.append("}");
        return sb.toString();
    }
}
