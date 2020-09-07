package com.xiumei.alogrithm.basic;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 7:10 2020/9/7
 * @Version: 1.0
 * @Description: 奇数次和偶数次（异或运算）
 **/
public class Code1_EvenTimesOddTimes {

    public static void main(String[] args) {
        int result = bit1Counts(3);
        System.out.println(result);
    }

    /**
     * 一个数组中有一种数出现了奇数次，其他都出现了偶数次，找到并打印该奇数次的数
     * @param arr
     */
    public static void printOddimesNum1(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor);
    }

    /**
     * 如何将 int 类型的数，提取最右侧的 1 来
     * @param num
     */
    public static void drawLeftestBit1(int num) {
        System.out.println(num & ((~num) + 1));
    }

    /**
     * 统计 int 类型的数，bit 位为 1 的个数
     * @param num
     * @return
     */
    public static int bit1Counts(int num) {
        int count = 0;
        // 011011010000
        // 000000010000     1
        // 011011000000
        while(num != 0) {
            int rightOne = num & ((~num) + 1);
            count++;
            num ^= rightOne;
        }
        return count;
    }

    /**
     * 一个数组中有两种数出现了奇数次，其他都出现了偶数次，怎么找到并打印这两种数
     * 思路：
     * 由于只有两个数是奇数次，不妨设这两个数字是 a 和 b，那么所有的数字进行异或，得到的结果 eor = a ^ b
     * 又 a 和 b 是两个数字，则 a 必不等于 b，则 eor != 0，说明 eor 必然有一个位置上是 1，即 a 与 b 在某位上是不等的。
     * 不妨设 eor 第 8 位上的数是 1，a 的第 8 位是 1，b 的第 8 位是 0，则整个数组按照次条件可以划分成两组：
     * 1）第一组：第 8 位是 1 的数。
     * 2）第二组：第 8 位是 0 的数。
     * 准备变量 eor'，用 eor' 异或第一组数，得到 a。用 eor' 异或 eor，得到 b。
     *
     * @param arr
     */
    public static void printOddimesNum2(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        int rightOne = eor & (~eor + 1); // 提取最右侧的 1
        int onlyOne = 0; // eor'
        for (int i = 0; i < arr.length; i++) {
            if((arr[i] & rightOne) != 0) {
                onlyOne ^= arr[i];
            }
        }
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

    /**
     * 不用额外变量交换两个数
     * 注意 i 和 j 所指得对象不能是同一个内存空间（i 不等于 j）
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j]; // arr[i] = arr[i] ^ arr[j], arr[j] = arr[j]
        arr[j] = arr[i] ^ arr[j]; // arr[i] = arr[i] ^ arr[j], arr[j] = arr[i] ^ arr[j] ^ arr[j] = arr[i]
        arr[i] = arr[i] ^ arr[j]; // arr[i] = arr[i] ^ arr[j] ^ arr[i] = arr[j], arr[j] = arr[i]
    }

}
