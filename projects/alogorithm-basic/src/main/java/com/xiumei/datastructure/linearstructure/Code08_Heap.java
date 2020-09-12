package com.xiumei.datastructure.linearstructure;

import com.xiumei.util.AlogrithmUtil;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 20:20 2020/9/12
 * @Version: 1.0
 * @Description: 堆
 **/
public class Code08_Heap {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int limit = 100; // 最大长度
        int maxValue = 1000; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap myMaxHeap = new MyMaxHeap(curLimit);
            VolianceMaxHeap testMaxHeap = new VolianceMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if(myMaxHeap.isEmpty() != testMaxHeap.isEmpty()) {
                    System.out.println("Oops!");
                }
                if(myMaxHeap.isFull() != testMaxHeap.isFull()) {
                    System.out.println("Oops!");
                }
                if(myMaxHeap.isEmpty()) {
                    int curValue = (int) (Math.random() * maxValue);
                    myMaxHeap.push(curValue);
                    testMaxHeap.push(curValue);
                } else if(myMaxHeap.isFull()) {
                    if(myMaxHeap.pop() != testMaxHeap.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if(Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * maxValue);
                        myMaxHeap.push(curValue);
                        testMaxHeap.push(curValue);
                    } else {
                        if(myMaxHeap.pop() != testMaxHeap.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 大根堆 MyMaxHeap
     */
    public static class MyMaxHeap {
        private int[] heap; // 用数组放置堆元素
        private final int limit; // 堆的最大大小 limit
        private int heapSize; // 堆的当前大小

        public MyMaxHeap(int limit) {
            this.heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        /**
         * 堆是否为空
         * @return
         */
        public boolean isEmpty() {
            return this.heapSize == 0;
        }

        /**
         * 堆是否已满
         * @return
         */
        public boolean isFull() {
            return this.heapSize == this.limit;
        }

        /**
         * 向堆中压入对象
         * @param value
         */
        public void push(int value) {
            if(this.heapSize == this.limit) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        /**
         * 从堆中 pop 对象。返回大根堆中堆顶对象（最大值），
         * 并将其删除，剩余数据，依然保持大根堆组织
         * @return
         */
        public int pop() {
            int result = heap[0];
            AlogrithmUtil.swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return result;
        }

        /**
         * HEAP-INSERT 操作
         * 从 index 位置，往上看，不断上浮，直到下述条件停止：
         * 1）index 位置数的父不再比 index 位置数小
         * 2）index 来到整个堆根节点位置
         * @param arr
         * @param index
         */
        private void heapInsert(int[] arr, int index) {
            while(arr[index] > arr[(index - 1) / 2]) {
                AlogrithmUtil.swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        /**
         * HEAPIFY 操作
         * 从 index 位置，往下看，不断下沉，直到下述条件停：
         * 1）index 位置的子节点不再比 index 位置数大
         * 2）index 位置没有孩子
         * @param arr
         * @param index
         * @param heapSize
         */
        private void heapify(int[] arr, int index, int heapSize) {
            int leftChildIndex  = index * 2 + 1;
            while(leftChildIndex < heapSize) { // 有子节点，进入循环，否则操作完成
                // 左右两个孩子中，谁大，谁把自己的小标给 larger
                // 右孩子成立条件：1）有右孩子 && 2）右孩子的值要比左孩子的值大
                // 否则，左
                int larger = leftChildIndex + 1 < heapSize && arr[leftChildIndex + 1] > arr[leftChildIndex] ? leftChildIndex + 1 : leftChildIndex;
                int largest = arr[larger] > arr[index] ? larger : index;
                if(largest == index) { // 若 index 位置是自己及左右孩子中最大值，则操作完成
                    break;
                }
                AlogrithmUtil.swap(arr, largest, index);
                index = largest;
                leftChildIndex = index * 2 + 1;
            }
        }

    }

    /**
     * 暴力构建的大根堆结构
     * 提供与大根堆相同功能方法
     */
    public static class VolianceMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public VolianceMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if(size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 0; i < size; i++) {
                if(arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int result = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return result;
        }
    }

}
