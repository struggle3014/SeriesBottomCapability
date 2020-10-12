package com.xiumei.alogrithm.greedy;

import java.util.PriorityQueue;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 20:48 2020/10/11
 * @Version: 1.0
 * @Description: 金条切割代价问题（哈夫曼树）
 **/
public class Code03_LessMoneySplitGold {
    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 50000; // 校验次数
        int maxValue = 100; // 数组元素最大大小
        int maxSize = 6; // 数组最大长度
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArr(maxSize, maxValue);
            if(lessMoneyUsingRecursion(arr) != lessMoneyUsingGreedy(arr)) {
                for (int ele : arr) {
                    System.out.print(ele + "\t");
                }
                System.out.println();
                System.out.println("----lessMoneyUsingRecursion:" + lessMoneyUsingRecursion(arr));
                System.out.println("----lessMoneyUsingGreedy:" + lessMoneyUsingGreedy(arr));
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 生成随机数组
     * @param maxSize 随机数组的最大长度
     * @param maxValue 随机数值的最大值
     * @return
     */
    public static int[] generateRandomArr(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    /**
     * 使用暴力递归方式解决金条切割代价最小问题
     * @param arr
     * @return
     */
    public static int lessMoneyUsingRecursion(int[] arr) {
        if(arr == null || arr.length == 0) {
            return 0;
        }
        return processUsingRecursion(arr, 0);
    }

    /**
     * 使用暴力递归方式解决金条切割代价最小问题实际过程
     * @param arr
     * @param pre
     * @return
     */
    private static int processUsingRecursion(int[] arr, int pre) {
        if(arr.length == 1) {
            return pre;
        }
        int answer = Integer.MAX_VALUE;
        // 穷举任意两个金条合在一起的后续过程
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                answer = Math.min(answer, processUsingRecursion(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return answer;
    }

    /**
     * 拷贝 arr，并合并 i 和 j 位置的数据，添加到拷贝数组 answer，并返回
     * @param arr
     * @param i
     * @param j
     * @return
     */
    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] answer = new int[arr.length -1];
        int answerI = 0;
        for (int arrI = 0; arrI < arr.length; arrI++) {
            if(arrI != i && arrI != j) {
                answer[answerI++] = arr[arrI];
            }
        }
        // i 和 j 位置的数据合并后放入拷贝数组最后一个元素
        answer[answerI] = arr[i] + arr[j];
        return answer;
    }

    /**
     * 使用贪心算法解决金条切割代价最小问题
     * 每次都贪心尽可能小的切割代价
     * @param arr
     * @return
     */
    public static int lessMoneyUsingGreedy(int[] arr) {
        // 优先队列（小根堆）
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            priorityQueue.add(arr[i]);
        }
        int sum = 0;
        int curr = 0;
        while (priorityQueue.size() > 1) {
            curr = priorityQueue.poll() + priorityQueue.poll();
            sum += curr;
            priorityQueue.add(curr);
        }
        return sum;
    }

}
