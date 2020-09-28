package com.xiumei.datastructure.tree;

import java.util.Stack;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:10 2020/9/27
 * @Version: 1.0
 * @Description: 查找后继节点
 **/
public class Code08_SuccessorNode {

    public static void main(String[] args) {
        // 使用常规方法，找到节点的后继节点
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        System.out.println(test.value + " next: " + getSuccessorNodeNormalWay(test).value);
    }

    /**
     * 节点
     */
    public static class Node {
        public int value; // 节点值
        public Node left; // 左孩子
        public Node right; // 右孩子
        public Node parent; // 父节点

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 获取某个节点的后继节点常规方法
     * 中序遍历（非递归方式）
     * 将二叉树中每个子树按左头进行划分。
     * 1）将二叉树的整个左边界依次压入栈。
     * 2）弹出栈顶元素，该元素右树上执行步骤1）
     * 3）重复步骤2），直到栈中元素为空，停止。
     * @param node
     */
    public static Node getSuccessorNodeNormalWay(Node node) {
        Node result = null;
        Node head = getRootNode(node);
        if(head != null) {
            Node previousPop = null; // 上一个弹出的节点
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || head != null) {
                if(head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    if(node == previousPop) {
                        result = head;
                        break;
                    }
                    previousPop = head;
                    head = head.right;
                }
            }
        }
        return result;
    }

    /**
     * 获取 node 节点的根节点
     * @param node
     * @return
     */
    public static Node getRootNode(Node node) {
        if(node == null) {
            return null;
        }
        while (node.parent != null) {
            node = node.parent;
        }
        return node;
    }

    /**
     * 获取 node 节点的后继节点
     * @param node
     * @return
     */
    public static Node getSuccessorNode(Node node) {
        if(node == null) {
            return node;
        }
        if(node.right != null) { // 有右孩子
            return getLeftMost(node.right);
        } else { // 无右孩子
            Node parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    /**
     * 获取 node 节点最左子树的最左侧的节点
     * @param node
     * @return
     */
    private static Node getLeftMost(Node node) {
        if(node == null) {
            return node;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }


}
