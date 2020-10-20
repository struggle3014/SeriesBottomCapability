package com.xiumei.datastructure.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 20:52 2020/10/19
 * @Version: 1.0
 * @Description: Dijkstra 算法
 * 解决带权重的有向图上单源最短路径问题
 **/
public class Code06_Dijkstra {

    /**
     * dijkstra 算法
     * @param from 从 from 顶点出发到所有点的最小距离
     * @return HashMap<Node, Integer> 中 key 是顶点 from 出发到达顶点 key，value 是顶点 from 出发到达顶点 key 的最小距离，
     * 若在表中，没有顶点 v 的记录，表示从顶点 from 到顶点 v 的距离为正无穷
     */
    public static HashMap<Node, Integer> dijkstra(Node from) {
        // 顶点 from 到图 Graph 的最短距离
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 已经计算过源顶点 from 到图中顶点的最短距离的顶点集合，顶点集合中的源顶点 from 到该顶点的记录处于锁定状态，后续再不进行任何操作
        HashSet<Node> selectedNodes = new HashSet<>();
        // 找到未被锁定点集中，且距离最小的顶点 minNode
        Node minNode = getMinDistanceAndUnSelectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            Integer distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if(!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance+edge.weight);
                } else {
                    distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance+edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnSelectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    /**
     * 从 distanceMap 中找出未被锁定的记录中距离最小的顶点
     * @param distanceMap
     * @param selectedNodes
     * @return
     */
    private static Node getMinDistanceAndUnSelectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if(!selectedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

    /**
     * 自定义堆结构
     */
    public static class NodeHeap {
        // 实际堆结构
        private Node[] nodes;
        // 堆上元素及其对应的在堆中的下标，若下标为 -1，则表示曾经进来过，但目前该顶点所在记录处于锁定状态
        private HashMap<Node, Integer> heapIndexMap;
        // key 为某顶点，value 为源顶点出发到该顶点的目前最小距离
        private HashMap<Node, Integer> distanceMap;
        private int size; // 堆上有元素

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            this.size = 0;
        }

        /**
         * add or update or ignore 元素
         * @param node
         * @param distance
         */
        public void addOrUpdateOrIgnore(Node node, int distance) {
            // add 操作：node 顶点没进过堆
            if(!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insert(node, size++);
            }
            // update 操作：node 顶点在堆上
            if(isInHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insert(node, heapIndexMap.get(node));
            }
        }

        /**
         * pop 堆顶元素
         * @return
         */
        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size-1);
            heapIndexMap.put(nodes[size-1], -1);
            distanceMap.remove(nodes[size-1]);
            nodes[size-1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        /**
         * insert 操作，维护小根堆性质
         * @param node
         * @param index
         */
        private void insert(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        /**
         * heapify 操作，维护小根堆性质
         * @param index
         * @param size
         */
        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left]) ? left + 1 : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if(smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        /**
         * 堆是否为空
         * @return
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * 节点 node 是否曾经进来过堆
         * @param node
         * @return
         */
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        /**
         * 节点 node 是否在堆上
         * @param node
         * @return
         */
        private boolean isInHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        /**
         * NodeHeap 上 index1 元素和 index2 元素交换
         * @param index1
         * @param index2
         */
        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }

    }

    /**
     * 节点记录
     */
    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    /**
     * dijkstra 算法优化（使用自定义堆结构优化）
     * 在上述算法实现过程中，从 distanceMap 中查找未被锁定的记录中的距离最小的顶点每次都需要做遍历操作
     * 考虑是否可以使用二叉堆，维护好堆结构，每次只需要 pop 堆顶元素即可。
     * 在 dijkstra 算法的流程中，可能会存在需要修改堆上元素的值，并要维护小根堆性质，由此可知，
     * 系统内置的堆是无法提供该功能的，需要手动实现堆结构。
     * 考虑该小根堆堆需要提供怎样的功能：
     * 1，add 操作：添加元素到小根堆中
     * 2，update 操作：可能将堆上元素值做修改，并重新维护好小根堆堆的性质
     * 3，ignore 操作：若某个记录已经处于锁定状态，若后续发现新的顶点指向处于锁定状态的顶点，则直接忽略即可
     * @return
     */
    public static HashMap<Node, Integer> dijkstraOptimization(Node from, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(from, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node curr = record.node;
            int distance = record.distance;
            for (Edge edge : curr.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(curr, distance);
        }
        return result;
    }

}
