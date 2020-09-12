package com.xiumei.datastructure.linearstructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 21:12 2020/9/12
 * @Version: 1.0
 * @Description: 自定义堆
 * 实现基于比较器的堆，以及堆的动态调整
 **/
public class Code09_Heap02 {

    /**
     * 自定义堆
     * @param <T>
     */
    public static class MyHeap<T> {
        private ArrayList<T> heap; // 数组存放堆结构的数据
        private HashMap<T, Integer> indexMap; // 保存数据及其对应索引
        private int heapSize; // 堆大小
        private Comparator<? super T> comparator; // 比较器

        public MyHeap(Comparator<? super T> comparator) {
            this.heap = new ArrayList<>();
            this.indexMap = new HashMap<>();
            this.heapSize = 0;
            this.comparator = comparator;
        }

        /**
         * 堆是否为空
         * @return
         */
        public boolean isEmpty() {
            return this.heapSize == 0;
        }

        /**
         * 堆大小
         * @return
         */
        public int size() {
            return this.heapSize;
        }

        /**
         * 判断堆中是否存在某个元素
         * @return
         */
        public boolean contains(T key) {
            return indexMap.containsKey(key);
        }

        /**
         * 从堆中 pop 对象。返回大根堆（小根堆）中堆顶对象，即最大值（最小值），
         * 并将其删除，剩余数据，依然保持大根堆（小根堆）组织
         * @param value
         */
        public void push(T value) {
            this.heap.add(value);
            indexMap.put(value, heapSize);
            heapInsert(heapSize++);
        }

        /**
         *
         * @return
         */
        public T pop() {
            T result = heap.get(0);
            int endIndex = heapSize - 1;
            swap(0, endIndex);
            this.heap.remove(endIndex);
            this.indexMap.remove(result);
            heapify(0, --heapSize);
            return result;
        }

        /**
         * 动态调整堆
         * @param value
         */
        public void resign(T value) {
            int valueIndex = indexMap.get(value);
            // 以下两个逻辑最多中一个
            heapInsert(valueIndex);
            heapify(valueIndex, heapSize);
        }

        /**
         * HEAP-INSERT 操作
         * @param index
         */
        private void heapInsert(int index) {
            while(comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
                swap(index, (index -1) / 2);
                index = (index - 1) / 2;
            }
        }

        /**
         * HEAPIFY 操作
         * @param index
         * @param heapSize
         */
        private void heapify(int index, int heapSize) {
            int leftChildIndex  = index * 2 + 1;
            while(leftChildIndex < heapSize) { // 有子节点，进入循环，否则操作完成
                // 左右两个孩子中，谁大，谁把自己的小标给 larger
                // 右孩子成立条件：1）有右孩子 && 2）右孩子的值要比左孩子的值大
                // 否则，左
                int larger = leftChildIndex + 1 < heapSize && comparator.compare(heap.get(leftChildIndex + 1), heap.get(leftChildIndex)) < 0 ? leftChildIndex + 1 : leftChildIndex;
                int largest = comparator.compare(heap.get(larger), heap.get(index)) < 0 ?  larger : index;
                if(largest == index) { // 若 index 位置是自己及左右孩子中最大值，则操作完成
                    break;
                }
                swap(largest, index);
                index = largest;
                leftChildIndex = index * 2 + 1;
            }
        }

        /**
         * i 位置和 j 位置数据做交换
         * @param i
         * @param j
         */
        private void swap(int i, int j) {
            T o1 = this.heap.get(i);
            T o2 = this.heap.get(j);
            this.heap.set(i, o2);
            this.heap.set(j, o1);
            this.indexMap.put(o1, j);
            this.indexMap.put(o2, i);
        }

    }

    /**
     * 学生类
     */
    public static class Student {
        public int classNo;
        public int age;
        public int id;

        public Student(int classNo, int age, int id) {
            this.classNo = classNo;
            this.age = age;
            this.id = id;
        }
    }

    /**
     * 学生比较器
     */
    public static class StudentComparator implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return o1.age - o2.age;
        }
    }

}
