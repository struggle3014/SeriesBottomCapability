<div align="center"><img src="https://gitee.com/struggle3014/picBed/raw/master/name_code.png"></div>

# 导读

本文介绍 Dijkstra 算法。

***持续更新中~***



# 目录



# 正文

## 算法介绍

**Dijkstra 算法**解决的是**带权重的有向图上单源最短路径问题**，该算法要求所有边的权重都为非负值，即假设对于所有的边 (u, v) ∈ E，都有 w(u, v) >= 0。



## 算法过程

Dijkstra 算法在运行过程中维持的关键信息是一组节点集合 S。从源节点 s 到该集合中每个节点之间的最短路径已经被找到。算法重复节点集 V - S 中选择最短路径估计最小的节点 u，将 u 加入到集合 S，然后对所有从 u 出发的边进行**[松弛](./单源最短路径.md)**。



## 算法实现

对于图 G = (V, E)，假设从 V 中的初始点出发，不妨设初始点为顶点 A，记录一张初始点到所有点的最小距离表 T。初始状态 A 点到 A 点的距离为0，顶点 A 和 G 中其他顶点是不可见的（一般使用 ∞ 表示）。

1）从 T 中找出未被锁定记录中距离最小值对应的点 fromNode，并查找从该点出发的所有边集。遍历该边集中每一条边 (fromNode, toNode)，T 中顶点 fromNode 的距离（T.fromNode）与边 (fromNode, toNode) 的权重（(fromNode, toNode).weight）之和与 T 中顶点 toNode 的距离（T.toNode）两者取较小值，更新 T 中顶点 toNode 的距离。上述边集遍历完毕，该顶点 fromNode 在 T 中记录被锁定，后续无需更改。

2）重复步骤1），直到 T 中所有记录被锁定。

Dijkstra 算法在做选择时，总是选择 T 中距离最小的点，本质是一种贪心。Dijkstra 算法很明显是有效的。直观理解，假设图中存在边 (A, C)，其对应的权值为 100，若客观上存在一条更短的路径使得从 A 到达 C，那么根据 Dijkstra 的算法流程，会记录 (A, C) 的100权值，并找出从 A 出发最小的权值的边，去尽可能使得顶点 A 到顶点 C 路径的权值变小，所以定会找到对应的最短路径。

算法具体证明可参考《算法导论》中的单源最短路径章节相关内容。

[Dijkstra#dijkstra](../../../../../projects/alogorithm-basic/src/main/java/com/xiumei/datastructure/graph/Code06_Dijkstra.java)

[Dijkstra#dijkstraOptimization](../../../../../projects/alogorithm-basic/src/main/java/com/xiumei/datastructure/graph/Code06_Dijkstra.java)



# 总结



# 参考文献

[1] [《算法导论》，第三版，Thomas H.Cormen，机械工业出版社](https://99baiduyun.com/baidu/算法导论)