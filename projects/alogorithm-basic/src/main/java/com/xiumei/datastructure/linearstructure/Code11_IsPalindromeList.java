package com.xiumei.datastructure.linearstructure;

import com.xiumei.util.LogarithmUtil;

import java.util.Stack;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:59 2020/9/19
 * @Version: 1.0
 * @Description: 判断单链表是否为回文结构
 * 回文结构的例子：12321，abcba
 **/
public class Code11_IsPalindromeList {

    public static void main(String[] args) throws Exception {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxSize = 10; // 最大长度
        int maxValue = 5; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Node head = LogarithmUtil.generateRandomList(Node.class, maxSize, maxValue, false);
            if(isPalindromeUsingStack(head) != isPalindromeUsingStackHalfSpace(head) || isPalindromeUsingStack(head) != isPalindrome(head)) {
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 链表
     */
    public static class Node {
        public int value;
        public Node next;

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 打印链表元素
     * @param head
     */
    private static void printNode(Node head) {
        if(head == null) {
            System.out.println("null");
            return;
        }
        while (head != null) {
            System.out.print(head.value + "\t");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * 使用链表长度额外空间（栈），实现回文结构判断
     * @return
     */
    public static boolean isPalindromeUsingStack(Node head) {
        // 准备栈，用于存放逆序的链表结构
        Stack<Node> stack = new Stack<Node>();
        Node curr = head;
        // 将链表元素依次压入栈中
        while (curr != null) {
            stack.push(curr);
            curr = curr.next;
        }
        // 依次判断栈元素和链表元素是否一致
        while (head != null) {
            if(head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 使用链表一半长度额外空间（栈），实现回文结构判断
     * 使用快慢指针，将慢指针定位到近似中点位置（奇数长度，慢指针来到中点；偶数长度，慢指针来到下中点）
     * @param head
     * @return
     */
    public static boolean isPalindromeUsingStackHalfSpace(Node head) {
       if(head == null || head.next == null) {
            return true;
       }
       // 慢指针定位到近似中点位置
       Node right = head.next;
       Node curr = head.next;
       while(curr.next != null && curr.next.next != null) {
           right = right.next;
           curr = curr.next.next;
       }
       // 将链表的后半部分元素压栈
       Stack<Node> stack = new Stack<Node>();
       while (right != null) {
           stack.push(right);
           right = right.next;
       }
       // 判断栈中元素和链表元素是否一致
       while (!stack.isEmpty()) {
           if(head.value != stack.pop().value) {
               return false;
           }
           head = head.next;
       }
       return true;
    }

    /**
     * 使用 O(1) 额外空间判断是否为回文结构
     * @param head
     * @return
     */
    public static boolean isPalindrome(Node head) {
        if(head == null || head.next == null) {
            return true;
        }
        // 1- 通过快慢指针找出近似中点的位置（偶数时，上中点；奇数时，中点）
        Node S = head; // 慢指针
        Node F = head; // 快指针
        while(F.next != null && F.next.next != null) {
            S = S.next;
            F = F.next.next;
        }

        // 2- 右半部分链表反转，且近似中间节点 S 的 next 置空
        // 偶数个时, 即 A -> B => A <- B
        // 奇数个时，即 A -> B -> C => A -> B <- C
        Node curr = S.next; // curr 来到右部分第一个节点
        Node prev = S; // curr 节点的前一个节点
        S.next = null;
        Node temp = null; // 临时节点
        while(curr != null) {
            temp = curr.next; // 记录下一个 curr 节点应该来到的节点
            curr.next = prev;
            prev = curr; // prev 来到 curr 指向的节点
            curr = temp; // curr 来到下一个 curr 该来到的节点
        }
        // 上述循环结束，prev 指向链表的最后一个节点
        Node L = head;
        Node R = prev;
        // 3- L 为链表头节点，R 为链表尾节点。L 和 R 分别从两端出发，判别对应的节点值是否相等
        boolean result = true;
        while(L != null && R != null) {
            if(L.value != R.value) {
                result = false;
                break;
            }
            L = L.next;
            R = R.next;
        }
        // 4- 右半部分链表反转恢复。
        // 偶数个时, 即 A <- B => A -> B
        // 奇数个时，即 A -> B <- C => A -> B -> C
        // 将 curr 置为原链表最后一个节点
        curr = prev;
        prev = curr.next;
        while (prev != null) {
            temp = prev.next; // 记录下一个 prev 应该来到的节点
            prev.next = curr;
            curr = prev; // curr 来到下一个 curr 应该来到的节点
            prev = temp; // prev 来到下一个 prev 应该来到的节点
        }
        return result;
    }

}
