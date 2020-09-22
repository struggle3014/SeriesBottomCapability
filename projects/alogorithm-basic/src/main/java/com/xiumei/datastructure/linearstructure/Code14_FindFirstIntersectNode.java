package com.xiumei.datastructure.linearstructure;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 11:22 2020/9/22
 * @Version: 1.0
 * @Description: 找寻两链表的第一个相交节点（入环点）
 **/
public class Code14_FindFirstIntersectNode {

    public static void main(String[] args) {

        Node head = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        head.next = node2;
        head.next.next = node3;
        head.next.next.next = node4;
        head.next.next.next.next = node5;
        head.next.next.next.next.next = node3;

        Node loopNode = getLoopNode(head);
        System.out.println(loopNode.value);

    }

    /**
     * 链表节点
     */
    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 找到两链表的相交节点
     * @param head1 链表1的头节点
     * @param head2 链表2的头节点
     * @return
     */
    public static Node getIntersectNode(Node head1, Node head2) {
        if(head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if(loop1 == null && loop2 == null) { // 两个无环链表的相交节点
            return noLoop(head1, head2);
        }
        if(loop1 != null && loop2 != null) { // 两个有环链表的相交节点
            return bothLoop(head1, loop1, head2, loop2);
        }
        // 一个有环，一个无环链表不可能存在相交节点
        return null;
    }

    /**
     * 两个无环链表的相交节点
     * @param head1
     * @param head2
     * @return
     */
    public static Node noLoop(Node head1, Node head2) {
        if(head1 == null || head2 == null) {
            return null;
        }
        Node curr1 = head1;
        Node curr2 = head2;
        int count = 0; // 链表1与链表2长度之差
        while (curr1.next != null) {
            count++;
            curr1 = curr1.next;
        }
        while (curr2.next != null) {
            count--;
            curr2 = curr2.next;
        }
        if(curr1 != curr2) { // 无环链表相交，必用公共部分，因此其尾节点必然是同一节点
            return null;
        }
        curr1 = count > 0 ? head1 : head2; // 谁长，谁的 head 变成 curr1
        curr2 = curr1 == head1 ? head2 : head1; // 谁短，谁的 head 变成 curr2
        count = Math.abs(count); // 绝对距离
        // 长链表，走绝对距离长度
        while (count != 0) {
            count--;
            curr1 = curr1.next;
        }
        // 以 curr1 和 curr2 为头节点的两链表长度相同，又两链表共用尾部，则同时遍历 curr1 和 curr2，必然会在某一步相遇
        while (curr1 != curr2) {
            curr1 = curr1.next;
            curr2 = curr2.next;
        }
        return curr1;
    }

    /**
     * 两个有环链表的相交节点
     * @param head1
     * @param loop1
     * @param head2
     * @param loop2
     * @return
     */
    private static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node curr1 = head1;
        Node curr2 = head2;
        if(loop1 == loop2) { // 两个链表入环节点为同一节点，与两无环链表相交情况相同
            curr1 = head1;
            curr2 = head2;
            int n = 0;
            while (curr1.next != null) {
                n++;
                curr1 = curr1.next;
            }
            while (curr2.next != null) {
                n--;
                curr2 = curr2.next;
            }
            if (curr1 != curr2) {
                return null;
            }
            // n  :  链表1长度减去链表2长度的值
            curr1 = n > 0 ? head1 : head2; // 谁长，谁的头变成cur1
            curr2 = curr1 == head1 ? head2 : head1; // 谁短，谁的头变成cur2
            n = Math.abs(n);
            while (n != 0) {
                n--;
                curr1 = curr1.next;
            }
            while (curr1 != curr2) {
                curr1 = curr1.next;
                curr2 = curr2.next;
            }
            return curr1;
        } else { // 入环节点不是同一节点
            curr1 = loop1.next;
            while (curr1 != loop1) { // curr1 转一圈，判断是否找着 loop2 节点，如果找着，说明相交，否则不想交
                if(curr1 == loop2) {
                    return loop1; // 任意换上节点，都是相交节点
                }
                curr1 = curr1.next;
            }
            return null;
        }
    }

    /**
     * 获取链表的第一个入环节点，如果无环，则返回 null
     * @param head
     * @return
     */
    public static Node getLoopNode(Node head) {
        if(head == null || head.next == null || head.next.next == null) { // 构不成环
            return null;
        }
        // 慢指针
        Node S = head.next;
        // 快指针
        Node F = head.next.next;
        // 若存在环，则快指针与慢指针相遇
        while (S != F) {
            if(F.next == null || F.next.next == null) { // F.next == null 判断，防止空指针异常
                return null;
            }
            S = S.next;
            F = F.next.next;
        }
        // 快指针从头节点出发，每次走一步
        F = head;
        while (S != F) {
            S = S.next;
            F = F.next;
        }
        return F;
    }


}
