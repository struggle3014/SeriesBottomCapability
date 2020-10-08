package com.xiumei.datastructure.tree;

import java.util.LinkedList;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:37 2020/10/8
 * @Version: 1.0
 * @Description: 是否是完全二叉树
 **/
public class Code13_IsCompleteBinaryTree {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 5000000; // 校验次数
        int maxLevel = 5; // 深度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if(isCBTUsingRecursion(head) != isCBTUsingRecursionRoutine(head)) {
                printTree(head);
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 打印节点
     * @param head
     */
    public static void printTree(Node head) {
        printInorder(head, 0, "H", 17);
        System.out.println();
    }

    /**
     * 按右头左的顺序打印二叉树
     * @param head 头节点
     * @param height 节点所处高度
     * @param to 标识节点关系 v：左下方是其父节点；^：左上方是其父节点
     * @param len 节点值占据长度
     */
    public static void printInorder(Node head, int height, String to, int len) {
        if(head == null) {
            return;
        }
        printInorder(head.right, height+1, "v", len);
        // 将数值打印在中间位置，方便清楚显示树的结构
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInorder(head.left, height+1, "^", len);
    }

    /**
     * 生成指定数量空格的字符串
     * @param num
     * @return
     */
    private static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    /**
     * 生成随机二叉树
     * @param maxLevel 树的最大层数
     * @param maxValue 节点最大值
     * @return
     */
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue) {
        if(level > maxLevel || Math.random() < 0.75) {
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
     * 使用暴力递归判断是否为完全二叉树（宽度优遍历）
     * 步骤：
     * 1）任何节点，有右无左，返回 false，否则继续
     * 2）一旦遇到左右孩子不双全，后续遇到所有节点必须为叶节点
     * @param head
     * @return
     */
    public static boolean isCBTUsingRecursion(Node head) {
        if(head == null) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        Node l = null;
        Node r = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            // 1- 有右无左
            // 2- 遇到不双全的节点之后，发现当前节点不是叶节点
            if((l == null && r != null) || (leaf && (l != null || r != null))) {
               return false;
            }
            if(l != null) {
                queue.add(l);
            }
            if(r != null) {
                queue.add(r);
            }
            if(l == null || r == null) { // 左右两个孩子有一个缺失，则左右两个孩子不双全
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 子树信息的全集 S
     */
    public static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    /**
     * 使用二叉树递归套路判断是否为完全二叉树
     * @param head
     * @return
     */
    public static boolean isCBTUsingRecursionRoutine(Node head) {
        if(head == null) {
            return true;
        }
        return processUsingRecursionRoutine(head).isCBT;
    }

    /**
     * 使用二叉树递归套路判断是否为完全二叉树的处理过程
     * @param X
     * @return
     */
    public static Info processUsingRecursionRoutine(Node X) {
        if(X == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = processUsingRecursionRoutine(X.left);
        Info rightInfo = processUsingRecursionRoutine(X.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isCBT = false;
        if(isFull) {
            isCBT = true;
        } else { // 以 X 为头的整棵树，不满
            if(leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height+1) { // 情况 1
                isCBT = true;
            }
            if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height+1) { // 情况 2
                isCBT = true;
            }
            if(leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) { // 情况 3
                isCBT = true;
            }
        }
        return new Info(isFull, isCBT, height);
    }

}
