package com.xiumei.alogrithm.sort;

import com.xiumei.util.LogarithmUtil;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 23:16 2020/9/12
 * @Version: 1.0
 * @Description: 机会有序数组排序
 * 题：已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，
 * 每个元素移动的距离一定不超过 k，并且 k 相对于数组长度来说是比较小的。
 * 选择合适的排序策略，对数组进行排序。
 **/
public class Code08_SortArrayDistanceLessK {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxSize = 100; // 最大长度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
            int[] arr1 = arr.clone();
            int[] arr2 = arr.clone();
            sortedArrDistanceLessK(arr1, k);
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
     * 对几乎有序数组排序
     * @param arr
     * @param k 移动最长距离
     */
    public static void sortedArrDistanceLessK(int[] arr, int k) {
        if(k == 0) { // k 长度为 0，说明数组顺序不能做调整
            return;
        }
        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>(); // 优先队列是小根堆
        int index = 0;
        // 0...k-1
        for(; index <= Math.min(arr.length-1, k); index++) { // 将 k 个数据（若不足 k 个，则数组全部元素）放入堆中
            heap.add(arr[index]);
        }
        int i = 0;
        for(; index < arr.length; i++, index++) { // 先弹出 i 位置的数，则 i 位置的数放置完成，再将 index 位置的数放入堆中，重复上述过程
            arr[i] = heap.poll();
            heap.add(arr[index]);
        }
        while(!heap.isEmpty()) { // 数组元素全部都放入堆中，没有剩余元素可往堆中添加，那么只需元素顺序从堆中弹出即可。
            arr[i++] = heap.poll();
        }
    }

    /**
     * 构造一个机会有序的数组
     * @param maxSize 最大长度
     * @param maxValue 元素最大大小
     * @param k 每个元素移动距离不超过 k
     * @return
     */
    public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int k) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        // 先排个序
        Arrays.sort(arr);
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (k + 1)), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }

}
