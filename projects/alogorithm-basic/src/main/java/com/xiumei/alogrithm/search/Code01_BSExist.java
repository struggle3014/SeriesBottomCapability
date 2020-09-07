package com.xiumei.alogrithm.search;

import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:39 2020/9/4
 * @Version: 1.0
 * @Description: 二分查找算法（标准）
 * 在有序数组中，找到某个数是否存在
 **/
public class Code01_BSExist {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxSize = 100; // 最大长度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = LogarithmUtil.generateRandomArr(maxSize, maxValue);
            Arrays.sort(arr1);
            int randomNum = (int) ((maxValue + 1) * Math.random() - (int) ((maxValue + 1) * Math.random()));
            if(violenceSearch(arr1, randomNum) != exist(arr1, randomNum)) {
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 暴力搜索某个值是否在排序数组中
     * @param sortedArr
     * @param num
     * @return
     */
    public static boolean violenceSearch(int[] sortedArr, int num) {
        for (int cur : sortedArr) {
            if(cur == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个数是否在有序的数组中
     * @param sortedArr 有序数组
     * @param num 找寻的数
     * @return
     */
    public static boolean exist(int[] sortedArr, int num) {
        if(sortedArr == null || sortedArr.length == 0) {
            return false;
        }
        int L = 0; // 左侧位置为 L
        int R = sortedArr.length -1; // 右侧位置为 R
        int mid = 0; // 中间位置为 mid
        // L...R 上找某个数是否存在
        while(L <= R) {
            // mid = (L + R) / 2 该做法，当 L 和 R 很大时，L + R 可能会溢出
            // 上述公式的等价写法：mid = L + (R - L) / 2
            // 使用位运算的等价写法：mid = L + ((R - L) >> 1)
            mid = L + ((R - L) >> 1);
            if(sortedArr[mid] == num) {
                return true;
            } else if(sortedArr[mid] > num) {
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return false;
    }

}
