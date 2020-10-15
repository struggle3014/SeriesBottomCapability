package com.xiumei.datastructure.extendstructure;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:24 2020/10/14
 * @Version: 1.0
 * @Description: 并查集
 **/
public class Code01_UnionFind {

    public static void main(String[] args) {

    }

    /**
     * 节点
     * @param <V>
     */
    public static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    /**
     * 并查集结构
     * @param <V>
     */
    public static class UnionSet<V> {
        // V 和 V 对应节点 Node<V> 的信息；
        // V -> Node<V> 即 V 对应的节点<V>
        public HashMap<V, Node<V>> nodes;
        // Node<V> 和其所在集合代表节点 Node<V> 的信息；
        // Node<V> -> Node<V> 即 Node<V> 所在集合的代表节点
        public HashMap<Node<V>, Node<V>> parents;
        // 代表节点 Node<V> 和其所在集合的节点数量 Integer；
        // Node<V> -> Integer 即 Node<V> 为其所在集合的代表节点，Integer 是所在集合的节点数
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionSet(List<V> values) {
            for (V curr : values) {
                Node<V> node = new Node<>(curr);
                nodes.put(curr, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        /**
         * 找 curr 节点所在集合的代表节点
         * 从 curr 节点开始，一直向上找，找到不能再往上的代表点，返回
         * @param curr
         * @return
         */
        public Node<V> findFather(Node<V> curr) {
            // Stack 用于存储 curr 向上查找代表点的沿途节点
            Stack<Node<V>> path = new Stack<>();
            // 从循环跳出，curr 指向其所在集合的代表节点
            while (curr != parents.get(curr)) {
                path.push(curr);
                curr = parents.get(curr);
            }
            // 扁平化处理，沿途节点的代表点都指向 curr
            while (!path.isEmpty()) {
                parents.put(path.pop(), curr);
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
        public boolean isSameSet(V a, V b) {
            if(!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return false;
            }
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        /**
         * 合并集合
         * 将 a 所在集合与 b 所在集合合并
         * @param a
         * @param b
         */
        public void union(V a, V b) {
            if(!nodes.containsKey(a) || !nodes.containsKey(b)) { // 不在并查集中，不予以合并
                return;
            }
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if(aHead != bHead) { // 如果 a 和 b 不属于同一个集合，可做集合合并操作
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big); // 小集合挂大集合
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }
    }

}
