package com.xiumei.datastructure.graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 17:12 2020/10/18
 * @Version: 1.0
 * @Description: 最小生成树 Prim 算法
 * 贪心算法
 **/
public class Code05_MSTPrim {

    /**
     * 边的比较器
     */
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    /**
     * 最小生成树 Prim 算法
     * @return
     */
    public static Set<Edge> primMST(Graph graph) {
        // 由边组织成的小根堆，存放处于解锁状态的边
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        // 曾经被解锁过的边集
        Set<Edge> edgeSet = new HashSet<>();
        // 曾经被解锁过的点集
        Set<Node> nodeSet = new HashSet<>();
        // 最小生成树边集
        Set<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()) { // 随便挑一个点，此处 for 循环的目的是防止 Graph 是一片森林
            if(!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Edge edge : node.edges) { // 由一个点，解锁所有相连的边
                    if(!edgeSet.contains(edge)) {
                        edgeSet.add(edge);
                        priorityQueue.add(edge);
                    }
                }
                while (!priorityQueue.isEmpty()) {
                    Edge edge = priorityQueue.poll(); // 弹出解锁边中，权值最小的边
                    Node toNode = edge.to; // 可能的一个新顶点
                    if(!nodeSet.contains(toNode)) { // toNode 是新顶点
                        nodeSet.add(toNode);
                        result.add(edge);
                        for (Edge nextEdge : toNode.edges) {
                            if(!edgeSet.contains(nextEdge)) {
                                edgeSet.add(nextEdge);
                                priorityQueue.add(nextEdge);
                            }
                        }
                    }
                }
            }
            break; // 防止 Graph 是森林
        }
        return result;
    }

}
