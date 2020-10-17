package com.xiumei.datastructure.graph;


/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 14:29 2020/10/16
 * @Version: 1.0
 * @Description: 边的表示
 * 有向图的边（无向图也是有向图的一种）
 **/
public class Edge {

    public int weight; // 权重
    public Node from; // 出发顶点
    public Node to; // 到达顶点

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
