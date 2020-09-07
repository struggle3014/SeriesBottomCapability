package com.xiumei.alogrithm.sort;

import com.xiumei.util.AlogrithmUtil;
import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:37 2020/9/4
 * @Version: 1.0
 * @Description: 选择排序
 **/
public class Code01_SelectionSort {

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
            Code01_SelectionSort.selectionSort(arr1);
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
     * 选择排序
     * @param arr
     */
    public static void selectionSort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length -1; i++) { // i ~ N-1
            // 最小值在哪个位置上 i ~ N-1
            int minIndex = i; // 最小值位置
            for (int j = i+1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            AlogrithmUtil.swap(arr, i, minIndex);
        }
    }

}
