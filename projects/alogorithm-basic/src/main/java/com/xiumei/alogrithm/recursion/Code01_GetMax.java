package com.xiumei.alogrithm.recursion;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 10:23 2020/9/8
 * @Version: 1.0
 * @Description: 使用递归查找数据中的最大值
 **/
public class Code01_GetMax {

    public static void main(String[] args) {
        int[] arr = {1, 3, 4, 2, 7, 9, 12};
        int result = getMax(arr);
        System.out.println(result);
    }

    /**
     * 获取最大值
     * @param arr
     * @return
     */
    public static int getMax(int[] arr) {
        return process(arr, 0, arr.length-1);
    }

    /**
     * 求 arr[L...R] 范围上的最大值
     * @param arr 数组
     * @param L
     * @param R
     * @return
     */
    public static int process(int[] arr, int L, int R) {
        if(L == R) { // base case：arr[L...R] 范围上只有一个数，直接返回。
            return arr[L];
        }
        int mid = L + ((R - L) >> 1); // 重点
        int leftMax = process(arr, L, mid);
        int rightMax = process(arr, mid+1, R);
        return Math.max(leftMax, rightMax);
    }

}
