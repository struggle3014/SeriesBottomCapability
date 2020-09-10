package com.xiumei.alogrithm.sort;

import com.xiumei.util.AlogrithmUtil;
import com.xiumei.util.LogarithmUtil;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:39 2020/9/10
 * @Version: 1.0
 * @Description: Partition 和快速排序
 **/
public class Code06_PartitionAndQuickSort {

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
            int[] arr3 = arr1.clone();
            quickSort1(arr1);
            quickSort1(arr2);
            quickSort1(arr3);
            if(!LogarithmUtil.isEqual(arr1, arr2) || !LogarithmUtil.isEqual(arr2, arr3)) {
                isSuccess = false;
                LogarithmUtil.printArr(arr1);
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 以 arr[R] 为划分值，在数组 arr[L...R] 范围上进行 Partition 操作
     * 返回 Partition 后，划分值所在位置
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int partition(int[] arr, int L, int R) {
        if(L > R) { // 参数非法
            return -1;
        }
        if(L == R) { // 数组中只有一个元素
            return L;
        }
        int lessEqual = L - 1; // <=区 所在的索引
        int index = L;
        while(index < R) {
            if(arr[index] <= arr[R]) { // 若 arr[index] 小于等于划分值，则 arr[index] 应划分到 <= 区
                AlogrithmUtil.swap(arr, index, ++lessEqual);
            }
            index++;
        }
        // 将 R 位置的数（划分值）与 <=区右边的值做交换，划分值位置固定，后续不发生改变
        AlogrithmUtil.swap(arr, ++lessEqual, R);
        return lessEqual;
    }

    /**
     * 以 arr[R] 为划分值，在数组 arr[L...R] 范围上玩荷兰国旗问题，返回等于区域的左右边界索引
     * 可以划分成三部分：<arr[R], ==arr[R], >arr[R]
     * @param arr
     * @param L
     * @param R
     * @return
     */
    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        if(L > R) { // 参数异常
            return new int[] { -1, -1};
        }
        if(L == R) { // 数组中有且只有一个元素
            return new int[] {L, R};
        }
        int less = L -1; // <区 的右边界
        int more = R; // >区 的左边界
        int index = L;
        while(index < more) {
            if(arr[index] == arr[R]) {
                index++;
            } else if(arr[index] < arr[R]) {
                AlogrithmUtil.swap(arr, index++, ++less);
            } else { // >
                AlogrithmUtil.swap(arr, index, --more);
            }
        }
        // 将划分值 arr[R] 与 >区 的左边界交换即可
        AlogrithmUtil.swap(arr, more, R);
        return new int[] { less+1, more};
    }

    /**
     * 快排 1.0
     * @param arr
     */
    public static void quickSort1(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length-1);
    }

    /**
     * 快速排序 1.0
     * @param arr
     * @param L
     * @param R
     */
    public static void process1(int[] arr, int L, int R) {
        if(L > R) { // 参数非法
            return;
        }
        // arr[L...R] 范围上，以 arr[R] 为划分值做 Partition
        int M = partition(arr, L, R); // 划分值位置
        process1(arr, L, M-1);
        process1(arr, M+1, R);
    }

    /**
     * 快排 2.0
     * @param arr
     */
    public static void quickSort2(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length-1);
    }

    /**
     * 快速排序 2.0
     * @param arr
     * @param L
     * @param R
     */
    public static void process2(int[] arr, int L, int R) {
        if(L > R) { // 参数非法
            return;
        }
        int[] equalArea = netherlandsFlag(arr, L, R);
        process2(arr, L, equalArea[0]-1);
        process2(arr, equalArea[1]+1, R);
    }

    /**
     * 快排 3.0
     * @param arr
     */
    public static void quickSort3(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        process2(arr, 0, arr.length-1);
    }

    /**
     * 快速排序 3.0
     * @param arr
     * @param L
     * @param R
     */
    public static void process3(int[] arr, int L, int R) {
        if(L >= R) {
            return;
        }
        // 随机抽取 L...R 范围上任一数据，并与 R 位置上的数据交换
        AlogrithmUtil.swap(arr, L+(int)(Math.random()*(R-L+1)), R);
        // 玩荷兰国旗问题
        int[] equalArea = netherlandsFlag(arr, L, R);
        process3(arr, L, equalArea[0]-1);
        process3(arr, equalArea[1]+1, R);
    }

}
