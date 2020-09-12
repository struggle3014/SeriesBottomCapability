package com.xiumei.alogrithm.sort;

import com.xiumei.util.AlogrithmUtil;
import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 20:19 2020/9/12
 * @Version: 1.0
 * @Description:
 **/
public class Code07_HeapSort {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxSize = 100; // 最大长度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = LogarithmUtil.generateRandomArr(maxSize, maxValue);
            int[] arr2 = arr1.clone();
            heapSort(arr1);
            Arrays.sort(arr2);
            if(!LogarithmUtil.isEqual(arr1, arr2)) {
                isSuccess = false;
                LogarithmUtil.printArr(arr1);
                LogarithmUtil.printArr(arr2);
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }


    /**
     * 堆排序
     * 时间复杂度为 O(N*logN)，额外空间复杂度为 O(1)
     * @param arr
     */
    public static void heapSort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        // 将无序数组调整成大根堆（HEAP-INSERT 方式）
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        // 将无序数组调整成大根堆（HEAPIFY 方式）
        for(int i=arr.length-1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        int heapSize = arr.length;
        AlogrithmUtil.swap(arr, 0, --heapSize);
        //
        while(heapSize > 1) { // 时间复杂度为 O(N)
            heapify(arr, 0, heapSize); // O(logN)
            AlogrithmUtil.swap(arr, 0, --heapSize); // O(1)
        }
    }

    /**
     * HEAP-INSERT 操作
     * 从 index 位置，往上看，不断上浮，直到下述条件停止：
     * 1）index 位置数的父不再比 index 位置数小
     * 2）index 来到整个堆根节点位置
     * @param arr
     * @param index
     */
    private static void heapInsert(int[] arr, int index) {
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
    private static void heapify(int[] arr, int index, int heapSize) {
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
