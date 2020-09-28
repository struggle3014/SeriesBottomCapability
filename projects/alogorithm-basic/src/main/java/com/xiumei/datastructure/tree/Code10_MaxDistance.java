package com.xiumei.datastructure.tree;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:56 2020/9/27
 * @Version: 1.0
 * @Description: 二叉树最大距离查找（二叉树递归套路求解）
 **/
public class Code10_MaxDistance {

    public static void main(String[] args) {
        Node head = generateRandomBST(5, 100);
        int count = maxDistanceUsingRecursionRoutine(head);
        System.out.println(count);
    }

    /**
     * 生成随机二叉树
     * @param maxLevel
     * @param maxValue
     * @return
     */
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    /**
     * 生成随机二叉树
     * @param level
     * @param maxLevel
     * @param maxValue
     * @return
     */
    public static Node generate(int level, int maxLevel, int maxValue) {
        if(level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level+1, maxLevel, maxValue);
        head.right = generate(level+1, maxLevel, maxValue);
        return head;
    }

    /**
     * 节点
     */
    public static class Node {
        public int value; // 节点值
        public Node left; // 左孩子
        public Node right; // 右孩子

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 子树信息的全集 S
     */
    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    /**
     * 使用二叉树递归套路查找树中的最大距离
     * @param head
     * @return
     */
    public static int maxDistanceUsingRecursionRoutine(Node head) {
        return processUsingRecursionRoutine(head).maxDistance;
    }

    /**
     * 处理细节
     * @param X 节点 X
     * @return
     */
    public static Info processUsingRecursionRoutine(Node X) {
        if(X == null) {
            return new Info(0, 0);
        }
        Info leftInfo = processUsingRecursionRoutine(X.left);
        Info rightInfo = processUsingRecursionRoutine(X.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int maxDistance = Math.max(
                Math.max(leftInfo.maxDistance, rightInfo.maxDistance),
                        leftInfo.height + rightInfo.height + 1);
        return new Info(maxDistance, height);
    }

}
