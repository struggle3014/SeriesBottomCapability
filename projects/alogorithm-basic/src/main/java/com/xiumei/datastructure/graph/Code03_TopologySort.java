package com.xiumei.datastructure.graph;

import java.util.*;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:45 2020/10/16
 * @Version: 1.0
 * @Description: 拓扑排序
 * 拓扑排序针对的是有向无环图，要求图不能有环，有环的图没有拓扑排序的概念
 * 拓扑排序一般用有依赖关系的事情的安排，如项目编译，任务调度
 **/
public class Code03_TopologySort {

    /**
     * 对有向无环图进行拓扑排序
     * @param graph
     * @return
     */
    public static List<Node> sortTopology(Graph graph) {
        // key：某个顶点 value：该顶点剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 剩余入度为0的点，才能进入该队列
        Queue<Node> zeroInQueue = new LinkedList<>();

        // 参数初始化
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if(node.in == 0) {
                zeroInQueue.add(node);
            }
        }

        // 拓扑排序的结果依次加入 result
        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node curr = zeroInQueue.poll();
            result.add(curr);
            for (Node next : curr.nexts) {
                inMap.put(next, inMap.get(next)-1);
                if(inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }

}
