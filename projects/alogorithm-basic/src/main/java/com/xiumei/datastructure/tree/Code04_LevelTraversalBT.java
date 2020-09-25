package com.xiumei.datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 14:17 2020/9/24
 * @Version: 1.0
 * @Description: 树的宽度优先遍历
 **/
public class Code04_LevelTraversalBT {

    public static void main(String[] args) {

        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        level(head);
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
     * 树的宽度优先遍历（按层遍历）
     * 1）将 head 加入队列
     * 2）若队列不为空，弹出队首元素，并打印。若该元素的左孩子不为空，加入队列。若该元素的右孩子不为空，加入队列。
     * @param head
     */
    public static void level(Node head) {
        if(head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.print(curr.value + "\t");
            if(curr.left != null) {
                queue.add(curr.left);
            }
            if(curr.right != null) {
                queue.add(curr.right);
            }
        }
    }

}
