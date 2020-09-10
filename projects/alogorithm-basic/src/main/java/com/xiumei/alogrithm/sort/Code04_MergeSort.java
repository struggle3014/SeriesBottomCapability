package com.xiumei.alogrithm.sort;

import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 14:25 2020/9/8
 * @Version: 1.0
 * @Description: 归并排序
 * 递归方法和非递归方法实现归并排序
 **/
public class Code04_MergeSort {

    /**
     * 递归方法实归并排序
     * @param arr 待排序数组
     */
    public static void mergeSort1(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length-1);
    }

    /**
     * arr[L...R] 范围上，变成有序
     * @param arr
     * @param L
     * @param R
     */
    public static void process(int[] arr, int L, int R) {
        if(L == R) { // base case，L 与 R 相等，说明只有一个元素，则 arr[L...R] 上有序
            return;
        }
        int mid = L + ((R - L) >> 1); // 获取中点
        process(arr, L, mid); // 目标问题可以划分成 L...mid 上同样的子问题
        process(arr, mid+1, R); // 目标问题可以划分成 mid+1...R 上同样的子问题
        // 将 L...mid 和 mid+1...R 上排序好的数据做归并操作，使得 arr 在 L...R 范围有序
        merge(arr, L, mid, R);
    }

    /**
     * 归并操作
     * @param arr
     * @param L
     * @param M
     * @param R
     */
    public static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1]; // 准备与 data 数组同样大小的 help 数组
        int i = 0;
        int lIndex = L;
        int rIndex = M+1;
        while(lIndex <= M && rIndex <= R) { // 两指针均未越界
            // 将 lIndex 和 rIndex 指向的较小值追加到 help 数组
            help[i++] = arr[lIndex] <= arr[rIndex++] ? arr[lIndex] : arr[rIndex++];
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
            arr[L+i] = help[i];
        }
    }

    /**
     * 非递归方法实现归并排序
     * @param arr
     */
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        int mergeSize = 1; // 当前有序的左组长度
        while(mergeSize < N) {
            int L = 0; // 记录每一个左组开始的位置
            while(L < N) {
                // L...M 为左组，且对应的大小为 mergeSize
                int M = L + mergeSize -1;
                if(M >= N) { // M >=N，说明左组的大小 <= mergeSize，那么左组必然有序，因为在上一轮 merge 中已经排好序。不需要接下来的 merge 操作。
                    break;
                }
                // 左组：L...M    右组：M+1...R(mergeSize)，右组可能存在凑不齐 mergeSize 数
                int R = Math.min(M+mergeSize, N-1);
                merge(arr, L, M, R);
                L = R + 1; // 下一个左组所在位置
            }
            if(mergeSize > N/2) { // 由于 mergeSize > N/2，那么 mergeSize <<=1，mergeSize 的值必然大于 N。不需要做 mergeSize <<= 1 运算，防止溢出（超出 int 的最大值）。
                break;
            }
            mergeSize <<= 1;
        }
    }

}
