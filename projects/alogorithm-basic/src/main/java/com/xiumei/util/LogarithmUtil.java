package com.xiumei.util;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:18 2020/9/4
 * @Version: 1.0
 * @Description: 对数器
 **/
public class LogarithmUtil {

    /**
     * 判断两数组是否完全相等
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if(arr1 == null && arr2 == null) {
            return true;
        }
        if(arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if(arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 打印数组
     * @param arr
     */
    public static void printArr(int[] arr) {
        if(arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

    }

    /**
     * 生成随机数组
     *
     * @param maxSize  最大长度
     * @param maxValue 最大大小，轴对称的
     * @return
     */
    public static int[] generateRandomArr(int maxSize, int maxValue) {
        // Math.random()    [0, 1)
        // (maxSize + 1) * Math.random()    [0, maxSize + 1)
        // (int)((maxSize + 1) * Math.random()) [0, maxSize]
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-maxValue, + maxValue]
            arr[i] = (int) ((maxValue + 1) * Math.random() - (int) ((maxValue + 1) * Math.random()));
        }
        return arr;
    }

    /**
     * 生成 [0, size] 上的随机整数数值
     * @param size 数值大小
     * @return
     */
    public static int generateRandomIndex(int size) {
        return (int)((size + 1) * Math.random());
    }

}
