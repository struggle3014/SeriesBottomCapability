package com.xiumei.datastructure.linearstructure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 6:25 2020/9/8
 * @Version: 1.0
 * @Description: 两个队列实现栈结构
 **/
public class Code07_TwoQueueIsmplStack {

    public static class TwoQueueStack<T> {
        public Queue<T> queue;
        public Queue<T> help;

        public TwoQueueStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        /**
         * 入栈操作
         * @param value
         */
        public void push(T value) {
            queue.offer(value);
        }

        /**
         * 出栈操作
         * @return
         */
        public T pop() {
            // 将 Data 队列的元素倒入 Help 队列中，直到 Data 队列最多只有一个数据为止
            while(queue.size() > 1) { // 数据队列中的元素不止一个
                help.offer(queue.poll()); // Data 队列元素倒入 Help 队列中
            }
            T result = queue.poll();
            // Data 队列和 Help 队列角色互换，Data 队列变 Help 队列，Help 队列变 Data 队列
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return result;
        }

        /**
         * 查看栈顶元素
         * @return
         */
        public T peek() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T result = queue.poll();
            help.offer(result);
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return result;
        }

        /**
         * 判断栈是否为空
         * @return
         */
        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

}
