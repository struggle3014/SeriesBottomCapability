package com.xiumei.alogrithm.sort;

import com.xiumei.util.AlogrithmUtil;
import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:38 2020/9/4
 * @Version: 1.0
 * @Description: 冒泡排序
 **/
public class Code02_BubbleSort {

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
            bubbleSort(arr1);
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

    public static void bubbleSort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length -1; i > 0; i--) { // 0~i 上
            for (int j = 0; j < i; j++) {
                if(arr[j] > arr[j + 1]) {
                    AlogrithmUtil.swap(arr, j, j+1);
                }
            }
        }

    }

}
