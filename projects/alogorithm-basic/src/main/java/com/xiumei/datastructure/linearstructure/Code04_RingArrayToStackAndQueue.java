package com.xiumei.datastructure.linearstructure;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 21:21 2020/9/7
 * @Version: 1.0
 * @Description: RingBuffer 实现栈和队列
 **/
public class Code04_RingArrayToStackAndQueue {

    public static void main(String[] args) {

    }

    /**
     * 使用数组实现固定大小的栈（Stack）结构
     */
    public static class MyStack {
        private int[] arr; // 数组
        private int size; // 栈大小
        private int curr; // 指针
        private int limit; // 栈容量

        public MyStack(int limit) {
            arr = new int[limit];
            curr = 0;
            size = 0;
            this.limit = limit;
        }

        /**
         * 压栈
         * @param value
         */
        public void push(int value) {
            if(size == limit) {
                throw new RuntimeException("栈满了，不能再加了");
            }
            arr[curr] = value;
            curr++; // 指针上移
            size++; // 栈大小自增一
        }

        /**
         * 出栈
         * @return
         */
        public int pop() {
            if(size == 0) {
                throw new RuntimeException("栈空了，不能再拿了");
            }
            int result = arr[curr -1];
            curr--;
            size--;
            return result;
        }

    }

    /**
     * 使用数组实现固定大小的队列（Queue）结构
     * 只使用 pushIndex, popIndex 表示队列 push 和 pop 逻辑比较复杂，
     * 可以使用 size 将 pushIndex 和 popIndex 两者解耦，不关注其复杂逻辑，
     * 使用 push 时，只需关注 size 是否小于 limit；使用 pop 时，只需关注 size 是否大于 0
     */
    public static class MyQueue {
        private int[] arr;
        private int pushIndex; // 压入的指针
        private int popIndex; // 弹出的指针
        private int size;
        private final int limit;

        public MyQueue(int limit) {
            arr = new int[limit];
            pushIndex = 0;
            popIndex = 0;
            size = 0;
            this.limit = limit;
        }

        /**
         * 入队
         * @param value
         */
        public void push(int value) {
            if(size == limit) {
                throw new RuntimeException("队列满了，不能再加了");
            }
            size++;
            arr[pushIndex] = value;
            pushIndex = nextIndex(pushIndex);
        }

        /**
         * 出队
         * @return
         */
        public int pop() {
            if(size == 0) {
                throw new RuntimeException("队列空了，不能再拿了");
            }
            size--;
            int result = arr[popIndex];
            popIndex = nextIndex(popIndex);
            return result;
        }

        /**
         * 判断队列是否为空
         * @return
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * 若当前位置的索引是 i，返回下一个位置索引
         * @param i
         * @return
         */
        private int nextIndex(int i) {
            // 由于可能会存在环装的 buffer，所以需要确定下一个位置的索引
            return i < limit - 1 ? i+1 : 0;
        }

    }


}
