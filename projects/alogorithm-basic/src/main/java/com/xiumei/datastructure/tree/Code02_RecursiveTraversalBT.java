package com.xiumei.datastructure.tree;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 17:23 2020/9/23
 * @Version: 1.0
 * @Description: 递归方式遍历二叉树（深度优先遍历：先序，中序，后续）
 **/
public class Code02_RecursiveTraversalBT {

    public static void main(String[] args) {

        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        preorder(head);
        System.out.println("------");
        inorder(head);
        System.out.println("------");
        postorder(head);
        System.out.println("------");

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
     * 递归序
     * @param head
     */
    public static void recursiveOrder(Node head) {
        if(head == null) {
            return;
        }
        // 1
        recursiveOrder(head.left);
        // 2
        recursiveOrder(head.right);
        // 3
    }

    /**
     * 先序遍历
     * @param head
     */
    public static void preorder(Node head) {
        if(head == null) {
            return;
        }
        System.out.println(head.value);
        preorder(head.left);
        preorder(head.right);
    }

    /**
     * 中序遍历
     * @param head
     */
    public static void inorder(Node head) {
        if(head == null) {
            return;
        }
        inorder(head.left);
        System.out.println(head.value);
        inorder(head.right);
    }

    /**
     * 后序遍历
     * @param head
     */
    public static void postorder(Node head) {
        if(head == null) {
            return;
        }
        postorder(head.left);
        postorder(head.right);
        System.out.println(head.value);
    }



}
