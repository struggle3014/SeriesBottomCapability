package com.xiumei.alogrithm.sort;

import com.xiumei.util.LogarithmUtil;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 23:27 2020/9/8
 * @Version: 1.0
 * @Description: 利用归并排序，实现小和问题计算
 * 小和问题：在某一数组中，一个数左边比它小的数的总和，叫做数的小和。所有数的小和的累加，叫做数组的小和。求给定数组的小和。
 **/
public class Code05_SmallSum {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxSize = 5; // 最大长度
        int maxValue = 10; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = LogarithmUtil.generateRandomArr(maxSize, maxValue);
            int[] arr2 = arr1.clone();
            if(smallSum(arr1) != voilanceCompute(arr2)) {
                isSuccess = false;
                LogarithmUtil.printArr(arr1);
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 暴力方法来实现小和计算
     * @param arr
     */
    public static int voilanceCompute(int[] arr) {
        if(arr == null || arr.length < 2) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                result += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return result;
    }

    public static int smallSum(int[] arr) {
        if(arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length-1);
    }

    public static int process(int[] arr, int L, int R) {
        if(L == R) { // base case：只有一个元素，小和不成立，返回0
            return 0;
        }
        // L < R
        int mid = L + ((R - L) >> 1);
        return process(arr, L, mid) + process(arr, mid+1, R) + merge(arr, L, mid, R);

    }

    /**
     * 归并操作
     * 过程中做小和计算
     * @param arr
     * @param L
     * @param M
     * @param R
     * @return
     */
    private static int merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1]; // 准备一个与 data 数组等长的 help 数组
        int i = 0;
        int lIndex = L;
        int rIndex = M+1;
        int result = 0;
        while(lIndex <= M && rIndex <= R) { // 两指针均未越界
            // TODO ... 寻找 rIndex 指针指向元素左边比它小的数总和
            result += arr[lIndex] < arr[rIndex] ? (R - rIndex + 1) * arr[lIndex] : 0;
            // 将 lIndex 和 rIndex 指向的较小值追加到 help 数组，若左组和右组相等时，拷贝右组
            help[i++] = arr[lIndex] < arr[rIndex] ? arr[lIndex++] : arr[rIndex++];
        }
        // 存在 lIndex 和 rIndex 指针越界的情况，下面两个判断逻辑只会中一个
        while(lIndex <= M) { // lIndex 指针未越界，则 rIndex 未越界
            // 将未越界指针指向的数组的剩余元素全部拷贝至 help 数组尾部
            help[i++] = arr[lIndex++];
        }
        while(rIndex <= R) { // rIndex 指针未越界，则 lIndex 指针越界
            // 将未越界指针指向的数组的剩余元素全部拷贝至 help 数组尾部
            help[i++] = arr[rIndex++];
        }
        // 将 help 数组中的数据刷写到 data 数据
        for (int j = 0; j < help.length; j++) {
            arr[L+j] = help[j];
        }
        return result;
    }

}
