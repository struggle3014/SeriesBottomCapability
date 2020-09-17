package com.xiumei.alogrithm.sort;

import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 15:03 2020/9/17
 * @Version: 1.0
 * @Description: 基数排序
 **/
public class Code10_RadixSort {

    public static void main(String[] args) {

        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxSize = 100; // 最大长度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = LogarithmUtil.generateRandomArr(maxSize, maxValue, false);
            int[] arr2 = arr1.clone();
            radixSort(arr1);
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
     * 基数排序
     * @param arr
     */
    public static void radixSort(int[] arr) {
        if(arr == null || arr.length < 2) { // 不需要排序的情况
            return;
        }
        radixSort(arr, 0, arr.length-1, maxBit(arr));
    }

    /**
     * arr[L...R] 范围上进行基数排序，其中最高位为 digit
     * 下述为“骚代码”模拟入桶、出桶行为
     * arr[101, 003, 202, 041, 302]
     * [个位数]模拟进桶过程如下：
     * 1）count 为 arr 中个位数为指定数的总个数，如 arr 中个位数为1总个数为2个。
     * count[0, 2, 2, 1, 0, 0, 0, 0, 0, 0] 个数
     *       0  1  2  3  4  5  6  7  8  9  数值
     * 2）count' 为 arr 中个数 <= 指定数的总个数。记录得是范围信息。
     * count'[0, 2, 4, 5, 5, 5, 5, 5, 5, 5] 个数
     *        0  1  2  3  4  5  6  7  8  9  数值
     * 3）回忆下，如果 arr 中的元素进桶，那么对应的元素是按照队列的形式放入桶中。
     * 那么如果我从右往左遍历 arr 数组：
     * 3.1）那么 302 理论上进 2 号桶且位于该桶的最后一个元素，
     * 又当前情况下 arr 中 <= 2 的元素的个数为 4，那么出桶时，302 必定排在 index 为 3 的位置，
     * 即 [  ,  ,  , 302, ]
     *     0  1  2    3  4
     * count' 变成 [0, 2, 3, 5, 5, 5, 5, 5, 5, 5] 个数
     *              0  1  2  3  4  5  6  7  8  9 数值
     * 3.2）那么 041 理论上进 1 号桶且位于该桶的最后一个元素，
     * 又当前情况下 arr 中 <= 1 的元素的个数为 2，那么出桶时，041 必定排在 index 为 1 的位置，
     * 即 [ , 041,  , 302, ]
     *     0   1  2    3  4
     * count' 变成 [0, 1, 3, 5, 5, 5, 5, 5, 5, 5] 个数
     *              0  1  2  3  4  5  6  7  8  9 数值
     * 3.3）那么 202 理论上进 2 号桶且位于该桶的倒数第二个元素，
     * 又当前情况 arr 中 <= 2 的元素的个数为 3，那么出桶时，202 必定排在 index 为 2 的位置，
     * 即 [ , 041, 202 , 302, ]
     *     0   1    2     3  4
     * count' 变成 [0, 1, 2, 5, 5, 5, 5, 5, 5, 5] 个数
     *              0  1  2  3  4  5  6  7  8  9 数值
     * 3.4）那么 003 理论上进 3 号桶且位于该桶的最后一个元素，
     * 又当前情况 arr 中 <= 3 的元素的个数为 5，那么出桶时，003 必定排在 index 为 4 的位置，
     * 即 [ , 041, 202 , 302, 003]
     *     0   1    2     3  4
     * count' 变成 [0, 1, 2, 4, 5, 5, 5, 5, 5, 5] 个数
     *              0  1  2  3  4  5  6  7  8  9 数值
     * 3.4）那么 101 理论上进 1 号桶且位于该桶的倒数第三个元素，
     * 又当前情况 arr 中 <= 1 的元素的个数为 1，那么出桶时，101 必定排在 index 为 0 的位置，
     * 即 [101 , 041, 202 , 302, 003]
     *      0     1    2     3    4
     * count' 变成 [0, 0, 2, 4, 5, 5, 5, 5, 5, 5] 个数
     *              0  1  2  3  4  5  6  7  8  9 数值
     * 4）将数据拷贝回 arr 数组，重复步骤 1）2）3），做完整个过程。
     * @param arr 数组
     * @param L 左下标
     * @param R 右下标
     * @param digit 最高位数
     */
    public static void radixSort(int[] arr, int L, int R, int digit) {
        final int radix = 10;
        int i = 0, j = 0;
        // 有多少个数，准备多长辅助空间
        int[] help = new int[R-L+1];
        for (int d = 0; d < digit; d++) { // 有多少位就进几次
            // 单次操作，完成对某一位（个位数、十位数、百位数...）的入桶、出桶操作
            // count 为 arr 中某一位为指定数的总个数，count 长度为 9
            int[] count = new int[radix];
            // 完成对 count 的赋值操作
            for (i = 0; i <= R; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }
            // 此时 count 充当上述方法中 count' 的功能，count' 为 arr 中个数 <= 指定数的总个数
            for(i = 1; i < radix; i++) {
                count[i] = count[i] + count[i-1];
            }
            // 右往左遍历 arr 数组，模拟入桶、出桶过程
            for(i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                help[count[j]-1] = arr[i]; // 将 arr[i] 放入 help 数组的 count[j]-1 位置
                count[j]--; // 对应词频 --
            }
            // 将 help 数据刷写回 arr
            for(i = L, j = 0; i <= R; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    /**
     * 获取 x 的 d 位数上的数值
     * 198
     * 10^1
     * 198/10^1 = 19
     * (198/10^1) % 10 = 9
     * @param x
     * @param d 指定位数（个位数，十位数，百位数...）
     * @return
     */
    public static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d))) % 10);
    }

    /**
     * 获取数组中最大值的位数
     * @param arr
     * @return
     */
    private static int maxBit(int[] arr) {
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            maxValue = Math.max(maxValue, arr[i]);
        }
        int maxBit = 0; // 最高位
        while (maxValue != 0) {
            maxBit++;
            maxValue /= 10;
        }
        return maxBit;
    }

}
