package com.xiumei.datastructure.graph;

import java.util.HashSet;
import java.util.Stack;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:41 2020/10/16
 * @Version: 1.0
 * @Description: 图的深度优先遍历
 **/
public class Code02_DFS {

    public static void dfs(Node node) {
        if(node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        System.out.println(node.value);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            for (Node next : curr.nexts) {
                if(!set.contains(next)) {
                    stack.push(curr);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next);
                    break;
                }
            }
        }
    }

}
