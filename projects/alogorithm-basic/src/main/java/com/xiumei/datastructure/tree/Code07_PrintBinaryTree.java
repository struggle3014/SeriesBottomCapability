package com.xiumei.datastructure.tree;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 11:03 2020/9/27
 * @Version: 1.0
 * @Description: 二叉树打印（左右头的顺序打印）
 **/
public class Code07_PrintBinaryTree {

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(-222222222);
        head.right = new Node(3);
        head.left.left = new Node(Integer.MIN_VALUE);
        head.right.left = new Node(55555555);
        head.right.right = new Node(66);
        head.left.left.right = new Node(777);
        printTree(head);
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

}
