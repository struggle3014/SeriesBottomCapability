package com.xiumei.datastructure.graph;

import java.util.*;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 17:12 2020/10/18
 * @Version: 1.0
 * @Description: 最小生成树 Kruskal 算法
 * 该算法是贪心算法，具体证明可参考《算法导论》
 **/
public class Code04_MSTKruskal {

    /**
     * 并查集结构
     */
    public static class UnionSet {
        // Node 和其所在集合代表节点 Node 的信息；
        // Node -> Node 即 Node 所在集合的代表节点
        public HashMap<Node, Node> fatherMap;
        // 代表节点 Node 和其所在集合的节点数量 Integer；
        // Node -> Integer 即 Node 为其所在集合的代表节点，Integer 是所在集合的节点数
        public HashMap<Node, Integer> sizeMap;

        public UnionSet() {
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public void makeUnionSet(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        /**
         * 找 curr 节点所在集合的代表节点
         * 从 curr 节点开始，一直向上找，找到不能再往上的代表点，返回
         * @param curr
         * @return
         */
        public Node findFather(Node curr) {
            // Stack 用于存储 curr 向上查找代表点的沿途节点
            Stack<Node> path = new Stack<>();
            // 从循环跳出，curr 指向其所在集合的代表节点
            while (curr != fatherMap.get(curr)) {
                path.push(curr);
                curr = fatherMap.get(curr);
            }
            // 扁平化处理，沿途节点的代表点都指向 curr
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), curr);
            }
            return curr;
        }

        /**
         * 集合之间连通性判断
         * a 所在集合和 b 所在集合是否连通
         * @param a
         * @param b
         * @return
         */
        public boolean isSameSet(Node a, Node b) {
            return findFather(a) == findFather(b);
        }

        /**
         * 合并集合
         * 将 a 所在集合与 b 所在集合合并
         * @param a
         * @param b
         */
        public void union(Node a, Node b) {
            if(a == null || b == null) {
                return;
            }
            Node aHead = findFather(a);
            Node bHead = findFather(b);
            if(aHead != bHead) { // 如果 a 和 b 不属于同一个集合，可做集合合并操作
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node big = aSetSize >= bSetSize ? aHead : bHead;
                Node small = big == aHead ? bHead : aHead;
                fatherMap.put(small, big); // 小集合挂大集合
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }
    }

    /**
     * 边比较器
     */
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    /**
     * 最小生成树 K 算法
     * @param graph
     * @return
     */
    public static Set<Edge> kruskalMST(Graph graph) {
        UnionSet unionSet = new UnionSet();
        unionSet.makeUnionSet(graph.nodes.values());
        // 优先队列，由边组织的小根堆
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge : graph.edges) { // M 条边
            priorityQueue.add(edge); // add 操作的时间复杂度为 O(logM)
        }
        Set<Edge> result = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            if(!unionSet.isSameSet(edge.from, edge.to)) {
                result.add(edge);
                unionSet.union(edge.from, edge.to);
            }
        }
        return result;
    }

}
