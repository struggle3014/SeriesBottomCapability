package com.xiumei.datastructure.tree;

import java.util.ArrayList;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 17:44 2020/9/27
 * @Version: 1.0
 * @Description: 查找二叉树中最大二叉搜索子树的头节点
 **/
public class Code11_MaxSubBSTHead {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxLevel = 5; // 深度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if(maxSubBSTHead(head) != maxSubBSTHeadUsingRecursionRoutine(head)) {
                System.out.println("----head----");
                printTree(head);
                System.out.println("----maxSubBSTHead----");
                printTree(maxSubBSTHead(head));
                System.out.println("----processUsingRecursionRoutine----");
                printTree(maxSubBSTHeadUsingRecursionRoutine(head));
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
     * 获取以 head 为头节点的二叉树中最大的二叉搜索子树的头节点
     * @param head
     * @return
     */
    public static Node maxSubBSTHead(Node head) {
        if(head == null) {
            return null;
        }
        if(getBSTSize(head) != 0) {
            return head;
        }
        Node leftResult = maxSubBSTHead(head.left);
        Node rightResult = maxSubBSTHead(head.right);
        return getBSTSize(leftResult) >= getBSTSize(rightResult) ? leftResult : rightResult;
    }

    /**
     * 获取以 X 为头节点的二叉搜索树的大小
     * @param X
     * @return
     */
    private static int getBSTSize(Node X) {
        if(X == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        inorder(X, arr);
        for (int i = 1; i < arr.size(); i++) {
            if(arr.get(i).value <= arr.get(i-1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    /**
     * 中序遍历以 X 为头节点的二叉树（左头右）
     * @param X
     * @param arr
     */
    private static void inorder(Node X, ArrayList<Node> arr) {
        if(X == null) {
            return;
        }
        inorder(X.left, arr);
        arr.add(X);
        inorder(X.right, arr);
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
        public Node maxSubBSTHead; // 最大二叉搜索子树的头节点
        public int maxSubBSTSize; // 最大二叉搜索子树的大小
        public boolean isBST; // 是否是二叉搜索子树
        public int min; // 最大值
        public int max; // 最小值

        public Info(Node maxSubBSTHead, int maxSubBSTSize, boolean isBST, int min, int max) {
            this.maxSubBSTHead = maxSubBSTHead;
            this.maxSubBSTSize = maxSubBSTSize;
            this.isBST = isBST;
            this.min = min;
            this.max = max;
        }
    }

    /**
     * 使用二叉树递归套路查找最大二叉子搜索树的头节点
     * @param head 头节点
     * @return
     */
    public static Node maxSubBSTHeadUsingRecursionRoutine(Node head) {
        if(head == null) {
            return null;
        }
        return processUsingRecursionRoutine(head).maxSubBSTHead;
    }

    /**
     * 处理过程
     * @param X X 节点
     * @return
     */
    public static Info processUsingRecursionRoutine(Node X) {
        Info baseCaseInfo = new Info(null, 0, true, Integer.MAX_VALUE, Integer.MIN_VALUE);
        if(X == null) { // base case
            return baseCaseInfo;
        }
        Info leftInfo = processUsingRecursionRoutine(X.left);
        Info rightInfo = processUsingRecursionRoutine(X.right);
        // 根据左右子树的信息，整合出需要返回的信息
        int min;
        int max;
        boolean isBST = false;
        int maxSubBSTSize = 0;
        Node maxSubBSTHead;
        // 左右子树都是二叉搜索树，才能满足以 X 为头的树是二叉搜索树，否则就破坏了该条件
        if(leftInfo.isBST && rightInfo.isBST && leftInfo.max <= X.value && rightInfo.min >= X.value) { // 最大子二叉搜索树与 X 有关
            // 左子树需要是搜索二叉树，右子树需要是搜索二叉树，左树最大值 <= X.value & 右树最小值 >= X.value
            isBST = true;
            min = Math.min(Math.min(leftInfo.min, X.value), rightInfo.min);
            max = Math.max(Math.max(leftInfo.max, X.value), rightInfo.max);
            maxSubBSTSize = leftInfo.maxSubBSTSize + rightInfo.maxSubBSTSize + 1;
            maxSubBSTHead = X;
        } else { // 最大子二叉搜索树与 X 无关
            min = leftInfo.isBST ? leftInfo.min : rightInfo.min;
            max = leftInfo.isBST ? leftInfo.max : rightInfo.max;
            maxSubBSTSize = Math.max(leftInfo.maxSubBSTSize, rightInfo.maxSubBSTSize);
            maxSubBSTHead = leftInfo.maxSubBSTSize > rightInfo.maxSubBSTSize ? leftInfo.maxSubBSTHead : rightInfo.maxSubBSTHead;
        }
        return new Info(maxSubBSTHead, maxSubBSTSize, isBST, min, max);
    }

}
