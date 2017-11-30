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

    // 同层上一个的filter处理结果，由框架(MatchAll)传递
    private LocalContext prevResult;

    // filter的name
    private String name;

    // 扩展调试信息
    private StringBuilder message;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        if (message != null) {
            sb.append(",");
            sb.append("message=");
            sb.append(message.toString());
        }
        sb.append("}");
        return sb.toString();
    }
}
