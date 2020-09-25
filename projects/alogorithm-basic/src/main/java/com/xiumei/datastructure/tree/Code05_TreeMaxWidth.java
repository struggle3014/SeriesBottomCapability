package com.xiumei.datastructure.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:00 2020/9/24
 * @Version: 1.0
 * @Description: 树的最大宽度
 **/
public class Code05_TreeMaxWidth {

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println(maxWidth(head));
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
     * 查找树的最大宽度
     * 类比宽度优先遍历，在宽度优先遍历的过程中记录一些信息
     * @param head
     * @return
     */
    public static int maxWidth(Node head) {
        int maxWidth = 0;
        if(head == null) {
            return maxWidth;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 当前层最右节点
        Node currLevelEnd = head;
        // 下一层最右节点
        Node nextLevelEnd = null;
        // 当前层节点数
        int currLevelNodes = 0;
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if(curr.left != null) {
                queue.add(curr.left);
                nextLevelEnd = curr.left;
            }
            if(curr.right != null) {
                queue.add(curr.right);
                nextLevelEnd = curr.right;
            }
            currLevelNodes++;
            if(curr == currLevelEnd) {
                maxWidth = Math.max(maxWidth, currLevelNodes);
                currLevelNodes = 0;
                currLevelEnd = nextLevelEnd;
            }
        }
        return maxWidth;
    }

}
