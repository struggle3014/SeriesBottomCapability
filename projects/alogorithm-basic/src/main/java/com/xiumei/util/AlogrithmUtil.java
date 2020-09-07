package com.xiumei.util;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 17:59 2020/9/4
 * @Version: 1.0
 * @Description: 算法工具类
 **/
public class AlogrithmUtil {
    /**
     * 数组 i 和 j 对应的位置元素做交换
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 数组 i 和 j 对应的位置元素做交换
     * 异或，i 和 j 不能是同一位置，否则会出错
     * @param arr
     * @param i
     * @param j
     */
    public static void swapXor(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

}
