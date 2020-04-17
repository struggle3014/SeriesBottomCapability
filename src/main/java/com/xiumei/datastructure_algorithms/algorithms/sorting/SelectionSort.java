package com.xiumei.datastructure_algorithms.algorithms.sorting;

/**
 * @Author: struggle3014
 * @Email: struggle3014@gmail.com
 * @Date: 14:49 2020/4/17
 * @Version: 1.0
 * @Description: 算法：选择排序实现
 **/
public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 3, 7, 0, 9, 6, 4, 1};

        print(arr);
        standardSelectionSort(arr);
        improvedV2SelectionSort(arr);
        imporvedV1SelectionSort(arr);
    }

    /**
     * 标准的选择排序算法
     * @param arr
     * @return
     */
    public static void standardSelectionSort(int[] arr) {
        for(int i=0; i<arr.length - 1; i++) {
            // 假设位置 i 是第 i 次遍历中数组中最小元素（位置 i 之前的元素不包含在本次数组中）
            int minPos = i;
            for(int j=i+1; j<arr.length; j++) {
                minPos = arr[j] < arr[minPos] ? j : minPos;
//                if(arr[minPos] > arr[j]) {
//                    minPos = j;
//                }
            }
            // 执行交换
            swap(arr, i, minPos);
            System.out.println("经过第" + i + "循环之后，数组的内容：");
            print(arr);
        }
    }

    /**
     * 改造的选择排序算法
     * 思路：每次遍历找出同时找出最大值和最小值
     * @param arr
     * @return
     */
    public static void imporvedV1SelectionSort(int[] arr) {
        for(int i=0; i<arr.length - 1; i++) {
            // 假设位置 i 是第 i 次遍历中的最小元素和最大元素（位置 i 之前的元素不包含在本次数组中）
            int minPos = i;
            int maxPos = arr.length - 1 - i;
            for(int j=i+1; j<arr.length - i; j++) {
                minPos = arr[j] < arr[minPos] ? j : minPos;
                maxPos = arr[j] > arr[maxPos] ? j : maxPos;
            }
            // 执行交换
            swap(arr, i, minPos);
            swap(arr, arr.length - 1 - i, maxPos);
            System.out.println("经过第" + i + "循环之后，数组的内容：");
            print(arr);
        }
    }

    /**
     * 改造的选择排序算法
     * 思路：每次遍历获取两个元素，找出其中的交小值与最小值比较。
     * @param arr
     */
    public static void improvedV2SelectionSort(int[] arr) {
        for(int i=0; i<arr.length - 1; i++) {
            // 假设位置 i 是第 i 次遍历中数组中最小元素（位置 i 之前的元素不包含在本次数组中）
            int minPos = i;
            for(int j=i+1; j<arr.length; j=j+2) {
                // 找到两个元素的较小值（若不足两个元素时，取最后一个元素）
                int minorPos = j+1 >= arr.length ? arr.length - 1 : findMinorPos(arr, j, j+1);
                minPos = arr[minorPos] < arr[minPos] ? minorPos : minPos;
            }
            // 执行交换
            swap(arr, i, minPos);
            System.out.println("经过第" + i + "循环之后，数组的内容：");
            print(arr);
        }
    }

    public static int findMinorPos(int[] arr, int i, int j) {
        return arr[i] < arr[j] ? i : j;
    }

    /**
     * 数据交换，对应索引位置元素进行交换。
     * @param arr 数组
     * @param i 索引 i
     * @param j 索引 j
     */
    static void swap(int[] arr, int i, int j) {
        int temp = 0;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 打印数组
     * @param arr 数组 arr
     */
    static void print(int[] arr) {
        for(int i=0; i<arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
