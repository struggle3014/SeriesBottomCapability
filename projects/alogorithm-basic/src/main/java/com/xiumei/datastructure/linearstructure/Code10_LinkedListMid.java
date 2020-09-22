package com.xiumei.datastructure.linearstructure;

import com.xiumei.util.LogarithmUtil;

import java.util.ArrayList;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:11 2020/9/18
 * @Version: 1.0
 * @Description: 链表面试题
 * 使用快慢指针的方式（其中慢指针 S 走1步，快指针 F 走2步，当 F 走完时，S 来到近似中点的位置），寻找数组的中点位置。
 * 1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点。
 * 2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点。
 * 3）输入链表头节点，奇数长度返回中点前一个，偶数返回上中点前一个。
 * 4）输入链表头节点，奇数长度返回中点前一个，偶数返回下中点前一个。
 **/
public class Code10_LinkedListMid {

    public static void main(String[] args) throws Exception {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxSize = 5; // 最大长度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Node head = LogarithmUtil.generateRandomList(Node.class, maxSize, maxValue, false);
            if(midOrUpMidNode(head) != volianceWay1(head)) {
                isSuccess = false;
                System.out.println("midOrUpMidNode");
                printNode(head);
                break;
            }
            if(midOrDownMidNode(head) != volianceWay2(head)) {
                System.out.println("midOrDownMidNode");
                isSuccess = false;
                printNode(head);
                break;
            }
            if(midPreOrUpMidPreNode(head) != volianceWay3(head)) {
                System.out.println("midPreOrDownMidPreNode");
                isSuccess = false;
                printNode(head);
                break;
            }
            if(midPreOrDownMidPreNode(head) != volianceWay4(head)) {
                System.out.println("midPreOrDownMidPreNode");
                isSuccess = false;
                printNode(head);
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 打印链表元素
     * @param head
     */
    private static void printNode(Node head) {
        if(head == null) {
            System.out.print("null");
            return;
        }
        while (head != null) {
            System.out.print(head.value + "\t");
            head = head.next;
        }
    }

    /**
     * 链表节点类
     */
    public static class Node {
        public int value; // 当前节点值
        public Node next; // 该节点指向的下一个节点

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
     *
     * @param head 头节点
     * @return
     */
    public static Node midOrUpMidNode(Node head) {
        // 链表没有节点，有单个节点，有双节点情况
        // 保证快慢指针能够先走出去一步
        if(head == null || head.next == null || head.next.next == null) {
            return head;
        }
        // 链表有3个节点
        Node slow = head.next; // slow 节点从 head 节点走一步
        Node fast = head.next.next; // fast 节点从 head 节点走两步
        while(fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
     * @param head 头节点
     * @return
     */
    public static Node midOrDownMidNode(Node head) {
        if(head == null || head.next == null) {
            return head;
        }
        Node slow = head.next;
        Node fast = head.next;
        while(fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数返回上中点前一个
     * 类比 midOrUpMidNode，slow 节点少走一步，即可完成
     * @param head 头节点
     * @return
     */
    public static Node midPreOrUpMidPreNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 输入链表头节点，奇数长度返回中点前一个，偶数返回下中点前一个
     * 类比 midOrDownMidNode，slow 节点少走一步，即可完成
     * @param head 头节点
     * @return
     */
    public static Node midPreOrDownMidPreNode(Node head) {
        if(head == null || head.next == null) {
            return head;
        }
        Node slow = head;
        Node fast = head.next;
        while(fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 暴力方法，将数据放入数组中，定位到指定位置返回
     * 奇数长度返回中点，偶数长度返回上中点
     * @param head 头节点
     * @return
     */
    public static Node volianceWay1(Node head) {
        if(head == null) {
            return null;
        }
        Node curr = head;
        ArrayList<Node> arr = new ArrayList<>();
        while(curr != null) {
            arr.add(curr);
            curr = curr.next;
        }
        return arr.get((arr.size()-1)/2);
    }

    /**
     * 暴力方法，将数据放入数组中，定位到指定位置返回
     * 奇数长度返回中点，偶数长度返回下中点
     * @param head 头节点
     * @return
     */
    public static Node volianceWay2(Node head) {
        if(head == null) {
            return null;
        }
        Node curr = head;
        ArrayList<Node> arr = new ArrayList<>();
        while(curr != null) {
            arr.add(curr);
            curr = curr.next;
        }
        return arr.get(arr.size()/2);
    }

    /**
     * 暴力方法，将数据放入数组中，定位到指定位置返回
     * 输入链表头节点，奇数长度返回中点前一个，偶数返回上中点前一个
     * @param head 头节点
     * @return
     */
    public static Node volianceWay3(Node head) {
        if(head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node curr = head;
        ArrayList<Node> arr = new ArrayList<>();
        while(curr != null) {
            arr.add(curr);
            curr = curr.next;
        }
        return arr.get((arr.size()-3)/2);
    }

    /**
     * 暴力方法，将数据放入数组中，定位到指定位置返回
     * 输入链表头节点，奇数长度返回中点前一个，偶数返回下中点前一个
     * @return
     */
    public static Node volianceWay4(Node head) {
        if(head == null) {
            return null;
        }
        Node curr = head;
        ArrayList<Node> arr = new ArrayList<>();
        while(curr != null) {
            arr.add(curr);
            curr = curr.next;
        }
        return arr.get((arr.size()-2)/2);
    }


}
