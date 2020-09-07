package com.xiumei.alogrithm.search;

import com.xiumei.util.LogarithmUtil;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 22:04 2020/9/6
 * @Version: 1.0
 * @Description: 局部最小值问题（二分法解决）
 * 局部最小值分为三种情况：
 * 1）当该值位于最左侧时，只要比次右边的值要小
 * 2）当该值位于最右侧时，只要比次左边的值要小
 * 3）当该值位于中间时，只要比两侧的值都要小
 *
 * 题：给一个长度为 N-1 无序数组 arr，其中任意两个相邻的数都不相等，返回其中一个局部最小值。
 *
 * |                    |
 * |    |   ......  |   |
 * 0    1          N-2 N-1
 * 0 位置的数要比 1 位置的数大（趋势向下），N-1 位置的数要比 N-2 位置的数要大（趋势向上），
 * 又任意两个相邻的数都不相等，呈现先下降后上升的形态，则中间必存在局部最小值。
 * 假设中间索引值为 M，又任意相邻的数不相等，若 M 位置的值 为 M-1 位置的值和 M+1 位置的值的最小值，则返回 M 位置的值即可；
 * 否则，M-1 位置的值，M 位置的值和 M+1 位置的值必然是单调的，又最左侧是下降趋势，最右侧是上升趋势，
 * 则必然存在 M 某一侧值存在先下降后上升的形态，则可以重复上述过程，直至找到局部最小值。
 *
 * 思考，二分使用的场景不一定需要所有元素有序，只要一种排他性的条件出现（排掉一侧的逻辑，且逻辑正确）
 **/
public class Code04_BSAwesome {

    public static void main(String[] args) {
        int[] arr = LogarithmUtil.generateRandomArr(10, 10);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(getLessIndex(arr));
    }


    /**
     * 从某个数组中获取一个局部最小值对应的索引
     * @param arr
     * @return
     */
    public static int getLessIndex(int[] arr) {
        if(arr == null || arr.length == 0) {
            return -1; // not exist
        }
        if(arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if(arr[arr.length -1] < arr[arr.length -2]) {
            return arr.length -1;
        }
        int L = 1;
        int R = arr.length -2;
        int mid = 0;
        while(L < R) {
            mid = L + (R - L) >> 1;
            if(arr[mid] > arr[mid -1]) {
                R = mid - 1;
            } else if(arr[mid] > arr[mid + 1]) {
                L = mid + 1;
            } else {
                return mid;
            }
        }
        return L;
    }

}
