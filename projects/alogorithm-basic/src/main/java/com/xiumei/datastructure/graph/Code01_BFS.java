package com.xiumei.datastructure.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 14:36 2020/10/16
 * @Version: 1.0
 * @Description: 图的宽度优先遍历
 **/
public class Code01_BFS {

    /**
     * 图的宽度优先遍历
     * @param node
     */
    public static void bfs(Node node) {
        if(node == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.println(curr.value);
            for (Node next : curr.nexts) {
                if(!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }

}
