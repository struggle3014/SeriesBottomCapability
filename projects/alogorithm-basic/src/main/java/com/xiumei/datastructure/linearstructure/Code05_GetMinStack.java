package com.xiumei.datastructure.linearstructure;

import java.util.Stack;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 22:44 2020/9/7
 * @Version: 1.0
 * @Description: 获取最小栈
 * 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能。
 * 1）pop, push, getMin 操作的时间复杂度都是 O(1)
 * 2）设计的栈类型可以使用现成的栈结构
 **/
public class Code05_GetMinStack {

    public static class MyStack1 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MyStack1() {
            this.stackData = new Stack<Integer>();
            this.stackMin = new Stack<Integer>();
        }

        /**
         * 压栈
         * 最小栈入栈逻辑：将最小栈栈顶和最新值中的较小值压入最小栈
         * @param newNum
         */
        public void push(int newNum) {
            if(this.stackMin.isEmpty()) { // 队列为空时
                this.stackMin.push(newNum);
            } else { // 队列不为空时，将最小栈栈顶和新值的较小值压入最小栈中
                this.stackMin.push(newNum < this.getMin() ? newNum : this.getMin());
            }
            this.stackData.push(newNum);
        }

        /**
         * 出栈
         * 最小栈出栈逻辑：正常 pop 即可
         * @return
         */
        public int pop() {
            if(this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            this.stackMin.pop();
            return this.stackData.pop();
        }

        /**
         * 获取最小值
         * @return
         */
        public int getMin() {
            if(this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek();
        }
    }

    public static class MyStack2 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MyStack2() {
            this.stackData = new Stack<Integer>();
            this.stackMin = new Stack<Integer>();
        }

        /**
         * 压栈
         * 最小栈入栈逻辑：若新值小于等于最小栈栈顶，则将新值压入最小栈；否则，不做任何操作
         * @param newNum
         */
        public void push(int newNum) {
            if(this.stackMin.isEmpty()) { // 队列为空时
                this.stackMin.push(newNum);
            } else if(newNum <= this.getMin()) { // 队列不为空，且压入值小于或等于最小栈栈顶，将最小栈栈顶和新值的较小值压入最小栈中
                this.stackMin.push(newNum);
            }
            this.stackData.push(newNum);
        }

        /**
         * 出栈
         * 最小栈出栈逻辑：若数据栈栈顶和最小栈栈顶相等，则出栈；否则，不做任何操作
         * @return
         */
        public int pop() {
            if(this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            int result = this.stackData.pop();
            if(result == this.getMin()) {
                this.stackMin.pop();
            }
            this.stackMin.pop();
            return result;
        }

        /**
         * 获取最小值
         * @return
         */
        public int getMin() {
            if(this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek();
        }

    }

}
