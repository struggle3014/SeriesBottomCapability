package com.xiumei.datastructure.graph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 14:29 2020/10/16
 * @Version: 1.0
 * @Description: 图的表示
 **/
public class Graph {
    // 编号 -> 顶点
    public HashMap<Integer, Node> nodes;
    // 边
    public HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
