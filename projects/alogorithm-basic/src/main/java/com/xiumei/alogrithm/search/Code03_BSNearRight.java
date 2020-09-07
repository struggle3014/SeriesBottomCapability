package com.xiumei.alogrithm.search;

import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:39 2020/9/4
 * @Version: 1.0
 * @Description: 二分查找算法
 * 在一个有序数组中，找到 <= 某个数最右侧的位置
 * 例子：在有序数组 [0, 1, 1, 2, 2, 2, 3, 3, 5, 7, 9, 11] 找到 <= 1 最左侧的位置。结果在箭头所示位置
 * [0, 1, 1, 2, 2, 2, 3, 3, 5, 7, 9, 11]
 *       ↑
 **/
public class Code03_BSNearRight {

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
            if(violenceSearch(arr1, randomNum) != nearestRightIndex(arr1, randomNum)) {
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 暴力搜索
     * @param sortedArr
     * @param num
     * @return
     */
    public static int violenceSearch(int[] sortedArr, int num) {
        int resultIndex = -1;
        for (int i = 0; i < sortedArr.length; i++) {
            if(sortedArr[i] <= num) {
                resultIndex = i;
            }
        }
        return resultIndex;
    }

    /**
     * 在有序数组上找到 <= num 最右侧的下标
     * @param sortedArr 有序数组
     * @param num 找寻的数
     * @return
     */
    public static int nearestRightIndex(int[] sortedArr, int num) {
        int resultIndex = -1; // 记录 >= num 的最左侧索引
        if(sortedArr == null || sortedArr.length == 0) {
            return resultIndex;
        }
        int L = 0; // 左侧位置为 L
        int R = sortedArr.length -1; // 右侧位置为 R
        // L...R 上找某个数是否存在
        while(L <= R) {
            // mid = (L + R) / 2 该做法，当 L 和 R 很大时，L + R 可能会溢出
            // 上述公式的等价写法：mid = L + (R - L) / 2
            // 使用位运算的等价写法：mid = L + ((R - L) >> 1)
            int mid = L + ((R - L) >> 1); // 中间位置
            if(sortedArr[mid] > num) {
                R = mid - 1;
            } else {
                L = mid + 1;
                resultIndex = mid;
            }
        }
        return resultIndex;
    }

}
