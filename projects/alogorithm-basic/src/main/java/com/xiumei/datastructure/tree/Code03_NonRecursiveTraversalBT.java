package com.xiumei.datastructure.tree;

import java.util.Stack;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 17:44 2020/9/23
 * @Version: 1.0
 * @Description: 非递归方式遍历二叉树（深度优先遍历：先序，中序，后续）
 **/
public class Code03_NonRecursiveTraversalBT {

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
        postorderUsingHelpStack(head);
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
     * 先序遍历（非递归方式）
     * 1）将头节点 head 压栈
     * 2）弹出栈顶元素，并打印，若有右压入右孩子，若有左压入左孩子。
     * 3）重复步骤2），直到栈中没有元素，结束。
     * @param head
     */
    public static void preorder(Node head) {
        if(head != null) {
            Stack<Node> stack = new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + "\t");
                if(head.right != null) {
                    stack.push(head.right);
                }
                if(head.left != null) {
                    stack.push(head.left);
                }
            }
            System.out.println();
        }
    }

    /**
     * 中序遍历（非递归方式）
     * 将二叉树中每个子树按左头进行划分。
     * 1）将二叉树的整个左边界依次压入栈。
     * 2）弹出栈顶元素，该元素右树上执行步骤1）
     * 3）重复步骤2），直到栈中元素为空，停止。
     * @param head
     */
    public static void inorder(Node head) {
        if(head != null) {
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || head != null) {
                if(head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.value + "\t");
                    head = head.right;
                }
            }
            System.out.println();
        }
    }

    /**
     * 后序遍历（非递归方式），使用额外栈
     * 1）将头节点 head 压栈 S1。
     * 2）弹出栈 S1 顶部元素，并压入栈 S2 中，若有左，压入右孩子；若有右，压入右孩子。
     * 3）重复步骤2），直到栈 S1 中没有元素。此时，栈 S2 中元素正好是后序遍历的逆序（头右左）。
     * @param head
     */
    public static void postorderUsingHelpStack(Node head) {
        if(head != null) {
            Stack<Node> s1 = new Stack<>();
            Stack<Node> s2 = new Stack<>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop();
                s2.push(head);
                if(head.left != null) {
                    s1.push(head.left);
                }
                if(head.right != null) {
                    s1.push(head.right);
                }
            }
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 后续遍历骚代码（卡逻辑，实现后续遍历）
     * head 为树的根节点。head 在后续中充当上一次遍历到的节点。
     * @param head
     */
    public static void postorder(Node head) {
        // 上次遍历（打印）到的节点
        // lastTraversedNode 初始值的赋值，只要不影响到后续第二次赋值之前的判断逻辑即可（此处设置为 head）
        Node lastTraversedNode = head;
        if(lastTraversedNode != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(lastTraversedNode);
            Node curr = null; // 当前节点
            while(!stack.isEmpty()) {
                curr = stack.peek();
                // 保证以 curr 为根的子树，左边界依次压入栈。
                // curr 的左孩子不为空，lastTraversedNode 不是 curr 的左孩子，lastTraversedNode 不是 curr 的右孩子
                if(curr.left != null && lastTraversedNode != curr.left && lastTraversedNode != curr.right) {
                    stack.push(curr.left);
                } else if(curr.right != null && lastTraversedNode != curr.right) {
                    // 保证以 curr 为根的子树，右孩子压入栈。
                    // curr 的右孩子不为空，且 lastTraversedNode 不是 curr 的右孩子（curr 有右孩子，且右孩子没有遍历（打印）过）
                    stack.push(curr.right);
                } else {
                    System.out.print(stack.pop().value + "\t");
                    lastTraversedNode = curr;
                }
            }
            System.out.println();
        }
    }

}
