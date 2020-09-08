package com.xiumei.datastructure.linearstructure;

import java.util.Stack;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 6:24 2020/9/8
 * @Version: 1.0
 * @Description: 两个栈实现队列
 **/
public class Code06_TwoStacksImplQueue {

    public static class TwoStacksQueue {
        public Stack<Integer> stackPush;
        public Stack<Integer> stackPop;

        public TwoStacksQueue() {
            stackPush = new Stack<Integer>();
            stackPop = new Stack<Integer>();
        }

        /**
         * push 栈向 pop 栈倒入数据
         * 需满足两个条件：
         * 1）pop 栈为空的情况下才能向 push 倒入
         * 2）push 栈必须要倒空位置
         */
        private void pushToPop() {
            if(stackPop.empty()) {
                while(!stackPush.empty()) {
                    stackPop.push(stackPush.pop());
                }
            }
        }

        /**
         * 入队操作
         * @param pushInt
         */
        public void add(int pushInt) {
            stackPush.push(pushInt);
            pushToPop();
        }

        /**
         * 出队操作
         * @return
         */
        public int poll() {
            if(stackPop.empty() && stackPush.empty()) {
                throw new RuntimeException("Queue is empty.");
            }
            pushToPop();
            return stackPop.pop();
        }

        /**
         * 查看队尾元素
         * @return
         */
        public int peek() {
            if(stackPop.empty() && stackPush.empty()) {
                throw new RuntimeException("Queue is empty.");
            }
            pushToPop();
            return stackPop.peek();
        }

    }

    public static void main(String[] args) {
        TwoStacksQueue test = new TwoStacksQueue();
        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }

}
