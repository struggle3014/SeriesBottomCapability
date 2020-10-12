package com.xiumei.alogrithm.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 11:16 2020/10/12
 * @Version: 1.0
 * @Description: 项目投资收益最大问题
 **/
public class Code05_IPO {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 10000; // 校验次数
        int profit = 100; // 随机最大的利润
        int cost = 50; // 随机最大的花费
        int programSize = 10; // 随机项目列表最大大小
        int k = 8; // 随机最大能做项目数的最大值
        int w = 100; // 随机初始资金的最大值
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Program[] programs = generatePrograms(programSize, profit, cost);
            int randK = generateRandom(k);
            int randW = generateRandom(w);
            int[] profits = new int[programs.length];
            int[] capitals = new int[programs.length];

            for (int j = 0; j < programs.length; j++) {
                profits[j] = programs[j].profit;
                capitals[j] = programs[j].cost;
            }

            if(findMaxCapitalUsingRecursion(randK, randW, profits, capitals) != findMaxCapitalUsingGreedy(randK, randW, profits, capitals)) {
                System.out.println("findMaxCapitalUsingRecursion:" + findMaxCapitalUsingRecursion(randK, randW, profits, capitals));
                System.out.println("findMaxCapitalUsingGreedy:" + findMaxCapitalUsingGreedy(randK, randW, profits, capitals));
                System.out.println("randK:" + randK + ",randW:" + randW);
                System.out.print("profits:");
                for (int ele : profits) {
                    System.out.print(ele + " ");
                }
                System.out.println();
                System.out.print("capitals:");
                for (int ele : capitals) {
                    System.out.print(ele + " ");
                }
                System.out.println();
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 生成随机项目列表
     * @param programSize
     * @param profit
     * @param cost
     * @return
     */
    public static Program[] generatePrograms(int programSize, int profit, int cost) {
        Program[] answer = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < answer.length; i++) {
            int p = (int) (Math.random() * (profit + 1));
            int c = (int) (Math.random() * (cost + 1));
            answer[i] = new Program(p, c);
        }
        return answer;
    }

    /**
     * 生成 0~num 之间的整数
     * @param num
     * @return
     */
    public static int generateRandom(int num) {
        return (int) (Math.random() * (num + 1));
    }

    /**
     * 项目
     */
    public static class Program {
        public int profit; // 利润
        public int cost; // 花费

        public Program(int profit, int cost) {
            this.profit = profit;
            this.cost = cost;
        }
    }

    /**
     * 使用暴力递归解决项目投资收益最大问题
     * @param k 最多能做的项目数
     * @param w 初始资金
     * @param profits 项目利润列表
     * @param capitals 项目花费列表
     * @return
     */
    public static int findMaxCapitalUsingRecursion(int k, int w, int[] profits, int[] capitals) {
        if(k == 0) { // 无项目可做
            return w;
        }
        return processUsingRecursion( k, w, profits, capitals);
    }

    /**
     * 使用暴力递归解决项目投资收益最大问题实际过程
     * @param k 最多能做的项目数
     * @param w 初始资金
     * @param profits 项目利润列表
     * @param capitals 项目花费列表
     * @return
     */
    private static int processUsingRecursion(int k, int w, int[] profits, int[] capitals) {
        int[] copyCapitals = Arrays.copyOf(capitals, capitals.length);
        Arrays.sort(copyCapitals); // 升序排序
        if(profits.length == 0 || capitals.length == 0 || w < copyCapitals[0] || k < 1) { // 是否有可做的项目
            return w;
        }
        int max = w;
        // 穷举任意项目做完之后的后续
        for (int i = 0; i < capitals.length; i++) {
            if(w >= capitals[i]) { // 当前的资金 w 可以 cover 住项目列表中所需的花费
                int[] nextProfits = copyButExcept(profits, i);
                int[] nextCapitals = copyButExcept(capitals, i);
                max = Math.max(max, processUsingRecursion(k-1, w+profits[i], nextProfits, nextCapitals));
            }
        }
        return max;
    }

    /**
     * 拷贝除 arr 中 i 索引的元素
     * @param arr
     * @param i
     * @return
     */
    private static int[] copyButExcept(int[] arr, int i) {
        int[] answer = new int[arr.length - 1];
        int index = 0;
        for (int j = 0; j < arr.length; j++) {
            if(j != i) {
                answer[index++] = arr[j];
            }
        }
        return answer;
    }

    /**
     * 使用贪心算法解决项目投资收益最大问题
     * @param k 最多能做的项目数
     * @param w 初始资金
     * @param profits 项目利润列表
     * @param capitals 项目花费列表
     * @return
     */
    public static int findMaxCapitalUsingGreedy(int k, int w, int[] profits, int[] capitals) {
        // 锁定项目堆（小根堆）
        PriorityQueue<Program> minCostQueue = new PriorityQueue<>(new MinCostComparator());
        // 解锁项目堆（大根堆）
        PriorityQueue<Program> maxProfitQueue = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < profits.length; i++) {
            minCostQueue.add(new Program(profits[i], capitals[i]));
        }
        for (int i = 0; i < k; i++) {
            // 锁定堆（以花费排序的小根堆）不为空，且资金能够 cover 住锁定堆的堆顶项目
            while (!minCostQueue.isEmpty() && minCostQueue.peek().cost <= w) {
                maxProfitQueue.add(minCostQueue.poll()); // 将项目加入解锁堆（以利润排序的大根堆）
            }
            // 无项目可做，返回即可
            if(maxProfitQueue.isEmpty()) {
                return w;
            }
            // 做解锁堆的堆顶项目
            w += maxProfitQueue.poll().profit;
        }
        return w;
    }

    /**
     * 花费比较器
     */
    private static class MinCostComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }

    /**
     * 利润比较器
     */
    private static class MaxProfitComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }


}