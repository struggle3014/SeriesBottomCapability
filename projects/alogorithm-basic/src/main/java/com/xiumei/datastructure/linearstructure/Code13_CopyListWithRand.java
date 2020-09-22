package com.xiumei.datastructure.linearstructure;

import java.util.HashMap;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 10:03 2020/9/21
 * @Version: 1.0
 * @Description: 拷贝带 rand 指针的数组
 **/
public class Code13_CopyListWithRand {

    public static void main(String[] args) {
        Node head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);

        head.next = second;
        head.next.next = third;
        head.rand = third;
        head.next.rand = null;
        head.next.next.rand = second;
        System.out.println(isSame(copyListWithRandUsingHashTable(head), copyListWithRand(head)));

    }

    /**
     * 链表节点
     */
    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 判断两个链表的值是否完全一致
     * @param head1
     * @param head2
     * @return
     */
    private static boolean isSame(Node head1, Node head2) {
        if(head1 == null || head2 == null) {
            if(head1 == null && head2 == null) {
                return true;
            } else {
                return false;
            }
        }
        while (head1 != null && head2 != null) {
            if(head1.value != head2.value) {
                return false;
            }
            if(head1.rand == null || head2.rand == null) {
                if(head1.rand != head2.rand) {
                    return false;
                }
            } else {
                if(head1.rand.value != head2.rand.value) {
                    return false;
                }
            }
            head1 = head1.next;
            head2 = head2.next;
        }
        return true;
    }

    /**
     * 使用 HashTable 来完成对带有 rand 指针的链表拷贝（笔试）
     * 时间复杂度 O(N)
     * @param head
     * @return
     */
    public static Node copyListWithRandUsingHashTable(Node head) {
        // 存储原节点和拷贝节点的对应关系 原节点 -> 拷贝节点
        HashMap<Node, Node> map = new HashMap<>();
        Node curr = head;
        // 完成对映射关系存储
        while (curr != null) {
            map.put(curr, new Node(curr.value));
            curr = curr.next;
        }
        curr = head;
        while (curr != null) {
            map.get(curr).next = map.get(curr.next);
            map.get(curr).rand = map.get(curr.rand);
            curr = curr.next;
        }
        return map.get(head);
    }

    /**
     * 额外空间复杂度为 O(1)，完成对带有 rand 指针的链表拷贝（面试）
     * @param head
     * @return
     */
    public static Node copyListWithRand(Node head) {
        if(head == null) {
            return head;
        }
        Node curr = head;
        Node currNext;
        Node copyNext;
        // 1- 将拷贝链表的元素交叉插入以 head 为头节点的链表中
        // 1 -> 2 -> null => 1 -> 1' -> 2 -> 2' -> null
        while (curr != null) {
            currNext = curr.next;
            curr.next = new Node(curr.value);
            curr.next.next = currNext;
            curr = currNext;
        }
        // 2- 设置拷贝链表的 rand 指针，并做拷贝链表和原链表的拆分操作
        curr = head;
        Node copyHead = curr.next; // 拷贝链表的头节点
        Node copyCurr = copyHead; // 拷贝链表的当前节点
        while (curr != null) {
            currNext = curr.next.next; // 记录下一次的 curr 节点环境
            copyCurr = curr.next;
            // 设置原链表的 next 指针
            head.next = currNext;
            // 设置拷贝链表的 next 指针（若 next == null，说明链表已遍历完）
            copyCurr.next = currNext == null ? null : currNext.next;
            // 设置拷贝链表的 rand 指针（若 curr.rand == null，说明 rand 指针指向 null，则同样 curr'.rand' 赋 null 即可）
            copyCurr.rand = curr.rand == null ? null : curr.rand.next;
            curr = currNext;
        }
        return copyHead;
    }

}
