package com.xiumei.datastructure.linearstructure;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 20:02 2020/9/7
 * @Version: 1.0
 * @Description: 双端链表实现栈和队列
 **/
public class Code03_DoubleEndsLinkedListToStackAndQueue {

    public static class Node<T> {
        public T value; // 节点数据
        public Node<T> prev; // 指向前一个节点的指针
        public Node<T> next; // 指向后一个节点的指针

        public Node(T data) { value = data; }
    }

    /**
     * 双端链表
     * @param <T>
     */
    public static class DoubleEndsLinkeList<T> {
        public Node<T> head; // 头节点
        public Node<T> tail; // 尾节点

        /**
         * 从 head 添加节点
         * @param value
         */
        public void addFromHead(T value) {
            Node<T> curr = new Node<T>(value); // 创建节点 curr
            if(head == null) { // head 为空，说明队列为空
                head = curr;
                tail = curr;
            } else { // head 不为空，说明队列不为空
                curr.next = head; // 将 head 作为 curr 的后一个节点
                head.prev = curr; // 将 curr 作为 head 的前一个节点
                head = curr; // 将 curr 作为 head 节点
            }

        }

        /**
         * 从尾部添加节点
         * @param value
         */
        public void addFromBottom(T value) {
            Node<T> curr = new Node<T>(value); // 创建节点 curr
            if(head == null) { // head 为空，说明队列为空
                head = curr;
                tail = curr;
            } else {
                curr.prev = tail; // 将 tail 作为 curr 的前一个节点
                tail.next = curr; // 将 curr 作为 tail 的后一个节点
                tail = curr; // 将 curr 为 tail 节点
            }
        }

        /**
         * 从头部弹出节点
         * @return
         */
        public T popFromHead() {
            if(head == null) { // 链表没有元素
                return null;
            }
            Node<T> curr = head;
            if(head == tail) { // head 等于 tail，表明链表只有一个元素
                head = null;
                tail = null;
            } else { // 链表不止一个元素
                head = head.next; // 当前 head 来带上一个 head 的后一个节点
                head.prev = null; // 当前 head 的 prev 置 null
                curr.next = null; // 将 curr 节点的 next 置 null
            }
            return curr.value;
        }

        /**
         * 从尾部弹出节点
         * @return
         */
        public T popFromBottom() {
            if(head == null) { // 链表没有元素
                return null;
            }
            Node<T> curr = tail;
            if(head == tail) { // head 等于 tail，表明链表只有一个元素
                head = null;
                tail = null;
            } else { // 链表不止一个元素
                tail = tail.prev; // 当前 tail 来带上一个 tail 的前一个节点
                tail.next = null; // 当前 head 的 prev 置 null
                curr.prev = null; // 将 curr 节点的 prev 置 null
            }
            return curr.value;
        }

        /**
         * 判断双端链表是否为空
         * @return
         */
        public boolean isEmpty() {
            return head == null;
        }

    }

    /**
     * 使用双端链表构建 Stack 结构
     * @param <T>
     */
    public static class MyStack<T> {
        private DoubleEndsLinkeList<T> linkedList;

        public MyStack() {
            linkedList = new DoubleEndsLinkeList<>();
        }

        public void push(T value) {
            linkedList.addFromHead(value);
        }

        public T pop() {
            return linkedList.popFromHead();
        }

        public boolean isEmpty() {
            return linkedList.isEmpty();
        }

    }

    /**
     * 使用双端链表构建
     * @param <T>
     */
    public static class MyQueue<T> {
        private DoubleEndsLinkeList<T> linkedList;

        public MyQueue() {
            linkedList = new DoubleEndsLinkeList<>();
        }

        public void push(T value) {
            linkedList.addFromHead(value);
        }

        public T poll() {
            return linkedList.popFromBottom();
        }

        public boolean isEmpty() {
            return linkedList.isEmpty();
        }

    }


}
