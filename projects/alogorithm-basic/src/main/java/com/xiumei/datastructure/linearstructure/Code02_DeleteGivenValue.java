package com.xiumei.datastructure.linearstructure;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:49 2020/9/7
 * @Version: 1.0
 * @Description: 删除给定值的所有节点
 **/
public class Code02_DeleteGivenValue {

    /**
     * 单向链表
     */
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }

        /**
         * 删除链表中给定值的所有节点
         * 因为头节点可能会被删除，所以需要返回头节点
         * @param head
         * @param num
         * @return
         */
        public static Node removeValue(Node head, int num) {
            // 保留链表中第一个不为 num 的节点作为头节点
            while(head != null) {
                if(head.value != num) {
                    break;
                }
                head = head.next;
            }
            // head 来到第一个不需要删的位置
            Node prev = head; // prev 表示截至目前位置，最新的不为 num 的节点
            Node curr = head;
            while(curr != null) {
                if(curr.value == num) {
                    prev.next = curr.next;
                } else {
                    prev = curr;
                }
                curr = curr.next; // curr 来到 curr 的下一个节点
            }
            return head;
        }
    }

}
