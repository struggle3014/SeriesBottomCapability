package com.xiumei.alogrithm.sort;

import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 13:46 2020/9/16
 * @Version: 1.0
 * @Description: 计数排序
 **/
public class Code09_CountSort {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxSize = 100; // 最大长度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = LogarithmUtil.generateRandomArr(maxSize, maxValue, false);
            int[] arr2 = arr1.clone();
            countSort(arr1);
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
     * 计数排序
     * @param arr
     */
    public static void countSort(int[] arr) {
        if(arr == null || arr.length < 2) { // 不需要排序的情况
            return;
        }
        // 最大值
        int maxValue = Integer.MIN_VALUE;
        // 找出数组中的最大值
        for (int i = 0; i < arr.length; i++) {
            maxValue = Math.max(maxValue, arr[i]);
        }
        // 准备一个 bucket 桶，用于临时存储空间
        int[] bucket = new int[maxValue+1];
        // 遍历 arr 数组每一个元素 i，bucket 数组指定下标 arr[i] 对应元素 bucket[arr[i]] 的值自增1
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        int index = 0;
        // 遍历 bucket 数组，将排序完的序列按顺序依次输出到数组中
        for (int j = 0; j < bucket.length; j++) {
            while(bucket[j]-- > 0) {
                arr[index++] = j;
            }
        }
    }

}
