package com.xiumei.datastructure.linearstructure;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:27 2020/9/7
 * @Version: 1.0
 * @Description: 链表反转
 **/
public class Code01_ReverseList {

    /**
     * 单项链表
     */
    public static class Node {
        public int value; // 节点数据
        public Node next; // 指向下一个节点

        public Node(int data) { value = data;}
    }

    /**
     * 双向链表
     */
    public static class DoubleNode {
        public int value; // 节点数据
        public DoubleNode prev; // 指向前一个节点
        public DoubleNode next; // 指向下一个节点
    }

    /**
     * 反转单向链表
     * @param head
     * @return
     */
    public static Node reverseLinkedList(Node head) {
        Node prev = null;
        Node next = null;
        while(head != null) {
            next = head.next; // 记录 head 的下一个节点
            head.next = prev;
            prev = head;
            head = next; // 将下一个节点置为 head 节点，进入下一轮迭代
        }
        return prev;
    }

    /**
     * 反转双向链表
     * 双向链表反转的另一个思路，可以直接返回 tail 节点即可，因为链表是双向的
     * @param head
     * @return
     */
    public static DoubleNode reverseDoubleList(DoubleNode head) {
        DoubleNode prev = null;
        DoubleNode next = null;
        while(head != null) {
            next = head.next; // 记录 head 的下一个节点

            head.next = prev;
            head.prev = next; // 对比单链表，多记录信息
            prev = head;

            head = next; // 将下一个节点置为 head 节点，进入下一轮迭代
        }
        return prev;
    }

}
