package com.xiumei.datastructure.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 21:53 2020/10/8
 * @Version: 1.0
 * @Description: 两个节点 A 和 B 的最低公共祖先
 **/
public class Code14_LowestAncestor {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 5000000; // 校验次数
        int maxLevel = 5; // 深度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node n1 = pickRandomOne(head);
            Node n2 = pickRandomOne(head);
            if(lowestAncestorUsingRecursion(head, n1, n2) != lowestAncestorUsingRecursionRoutine(head, n1, n2)) {
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
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
     * 从二叉树中随机抽取一个节点
     * @param head
     * @return
     */
    public static Node pickRandomOne(Node head) {
        if(head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPreList(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    /**
     * 二叉树先序遍历填充 arr（递归序）
     * @param head
     * @param arr
     */
    private static void fillPreList(Node head, ArrayList<Node> arr) {
        if(head == null) {
            return;
        }
        arr.add(head);
        fillPreList(head.left, arr);
        fillPreList(head.right, arr);
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
     * 使用暴力递归方式查找 n1, n2 的最低公共祖先
     * @param head
     * @param n1
     * @param n2
     * @return
     */
    public static Node lowestAncestorUsingRecursion(Node head, Node n1, Node n2) {
        if(head == null) {
            return null;
        }
        // key 的父节点为 value
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        HashSet<Node> n1Set = new HashSet<>();
        Node curr = n1;
        n1Set.add(curr);
        while (parentMap.get(curr) != null) {
            curr = parentMap.get(curr);
            n1Set.add(curr);
        }
        curr = n2;
        while (!n1Set.contains(curr)) {
            curr = parentMap.get(curr);
        }
        return curr;
    }

    /**
     * 填充 parentMap
     * @param head
     * @param parentMap
     */
    private static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if(head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if(head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    /**
     * 子树信息的全集 S
     */
    public static class Info {
        public Node answer; // 以其为头的子树上的最大公共祖先（可能为空）
        public boolean isFindN1; // 是否发现 n1
        public boolean isFindN2; // 是否发现 n2

        public Info(Node answer, boolean isFindN1, boolean isFindN2) {
            this.answer = answer;
            this.isFindN1 = isFindN1;
            this.isFindN2 = isFindN2;
        }
    }


        /**
     * 使用二叉树递归套路查找 n1, n2 的最低公共祖先
     * @param head head 节点
     * @param n1
     * @param n2
     * @return
     */
    public static Node lowestAncestorUsingRecursionRoutine(Node head, Node n1, Node n2) {
        return processUsingRecursionRoutine(head, n1, n2).answer;
    }

    /**
     * 使用二叉树递归套路查找 n1 和 n2 的最低公共祖先
     * @param X
     * @param n1
     * @param n2
     * @return
     */
    public static Info processUsingRecursionRoutine(Node X, Node n1, Node n2) {
        if(X == null) {
            return new Info(null, false, false);
        }
        Info leftInfo = processUsingRecursionRoutine(X.left, n1, n2);
        Info rightInfo = processUsingRecursionRoutine(X.right, n1, n2);
        boolean isFindN1 = X == n1 || leftInfo.isFindN1 || rightInfo.isFindN1;
        boolean isFindN2 = X == n2 || leftInfo.isFindN2 || rightInfo.isFindN2;
        // n1 和 n2 最初交汇点在哪儿？
        // 1)在左子树上提前交汇了
        // 2)在右子树上提前交汇了
        // 3)没有在左子树或者右子树上提前交汇，且都找全了 n1 和 n2
        Node answer = null;
        if(leftInfo.answer != null) {
            answer = leftInfo.answer;
        }
        if(rightInfo.answer != null) {
            answer = rightInfo.answer;
        }
        if(answer == null) {
            if(isFindN2 && isFindN1) {
                answer = X;
            }
        }
        return new Info(answer, isFindN1, isFindN2);
    }

}
