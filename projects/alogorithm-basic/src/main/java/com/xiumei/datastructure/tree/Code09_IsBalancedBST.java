package com.xiumei.datastructure.tree;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:53 2020/9/27
 * @Version: 1.0
 * @Description: 二叉树平衡性判断（二叉树递归套路求解）
 **/
public class Code09_IsBalancedBST {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxLevel = 6; // 深度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if(isBalancedUsingRecursion(head) != isBalancedUsingRecursionRoutine(head)) {
                isSuccess = false;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
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
     * 使用递归判断二叉树的平衡性
     * @param head
     * @return
     */
    public static boolean isBalancedUsingRecursion(Node head) {
        boolean[] result = new boolean[1];
        result[0] = true;
        processUsingRecursion(head, result);
        return result[0];
    }

    /**
     * 使用递归处理过程
     * @param head
     * @param result
     * @return
     */
    public static int processUsingRecursion(Node head, boolean[] result) {
        if(!result[0] || head == null) {
            return -1;
        }
        int leftHeight = processUsingRecursion(head.left, result);
        int rightHeight = processUsingRecursion(head.right, result);
        if(Math.abs(leftHeight - rightHeight) > 1) {
            result[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 使用二叉树递归套路判断二叉树的平衡性
     * @param head
     * @return
     */
    public static boolean isBalancedUsingRecursionRoutine(Node head) {
        return processUsingRecursionRoutine(head).isBalanced;
    }

    /**
     * 子树信息的全集 S
     */
    public static class Info {
        public boolean isBalanced; // 左（右）子树是否平衡
        public int height; // 左（右）子树的高度

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    /**
     * 使用二叉树递归套路进行树的平衡性判断
     * @param X 节点 X
     * @return 子树信息
     */
    public static Info processUsingRecursionRoutine(Node X) {
        if(X == null) {
            return new Info(true, 0);
        }
        Info leftInfo = processUsingRecursionRoutine(X.left);
        Info rightInfo = processUsingRecursionRoutine(X.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = true;
        if(!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }

}
