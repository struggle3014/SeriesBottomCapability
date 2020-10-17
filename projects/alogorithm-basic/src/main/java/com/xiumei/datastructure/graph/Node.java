package com.xiumei.datastructure.graph;

import java.util.ArrayList;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 14:29 2020/10/16
 * @Version: 1.0
 * @Description: 顶点
 **/
public class Node {
    public int value; // 顶点编号
    public int in; // 入度
    public int out; // 出度
    public ArrayList<Node> nexts; // 直接邻居列表，直接邻居是指从当前节点直接能到的节点称为直接邻居
    public ArrayList<Edge> edges; // 直接边列表

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
