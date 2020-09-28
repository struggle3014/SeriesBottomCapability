package com.xiumei.datastructure.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 14:01 2020/9/28
 * @Version: 1.0
 * @Description: 派对最大快乐值问题（使用多叉树的递归套路）
 **/
public class Code12_MaxHappy {

    public static void main(String[] args) {
        int maxLevel = 4; // 最大深度
        int maxNexts = 7; // 最大子节点个数
        int maxHappy = 100; // 最大快乐值
        int testTimes = 50000; // 验证次数
        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = generateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappyUsingRecursion(boss) != maxHappyUsingRecursionRoutine(boss)) {
                isSuccess = false;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 随机生成以 boss 为头的多叉树
     * @param maxLevel
     * @param maxNexts
     * @param maxHappy
     * @return
     */
    public static Employee generateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if(Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        generateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    private static void generateNexts(Employee employee, int level, int maxLevel, int maxNexts, int maxHappy) {
        if(level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            employee.nexts.add(next);
            generateNexts(next, level+1, maxLevel, maxNexts, maxHappy);
        }
    }

    /**
     * 使用普通递归方式实现最大快乐值的计算
     * @param boss
     * @return
     */
    public static int maxHappyUsingRecursion(Employee boss) {
        if(boss == null) {
            return 0;
        }
        return Math.max(processUsingRecursion(boss, true), processUsingRecursion(boss, false));
    }

    /**
     *
     * @param X X 节点
     * @param isPresent 是否参加
     * @return
     */
    public static int processUsingRecursion(Employee X, boolean isPresent) {
        if(isPresent) {
            int presentHappy = X.happy;
            for (Employee next : X.nexts) {
                presentHappy += processUsingRecursion(next, false);
            }
            return presentHappy;
        } else {
            int absentHappy = 0;
            for (Employee next : X.nexts) {
                absentHappy += Math.max(processUsingRecursion(next, true), processUsingRecursion(next, false));
            }
            return absentHappy;
        }
    }

    /**
     * 雇员信息
     */
    public static class Employee {
        public int happy; // 快乐值
        public List<Employee> nexts; // 直接下级

        public Employee(int happy) {
            this.happy = happy;
            nexts = new ArrayList<>();
        }
    }

    /**
     * 子树信息的全集 S
     */
    public static class Info {
        public int presentHappy; // 参加情况下，以该节点为头的整棵子树的最大快乐值
        public int absentHappy; // 不参加情况下，以该节点为头的整棵子树的最大快乐值

        public Info(int presentHappy, int absentHappy) {
            this.presentHappy = presentHappy;
            this.absentHappy = absentHappy;
        }
    }

    /**
     * 寻找最大快乐值（使用多叉树递归套路）
     * @param boss
     * @return
     */
    public static int maxHappyUsingRecursionRoutine(Employee boss) {
        if(boss == null) {
            return 0;
        }
        Info info = processUsingRecursionRoutine(boss);
        return Math.max(info.presentHappy, info.absentHappy);
    }

    /**
     * 处理方法
     * @param X X 节点
     * @return
     */
    public static Info processUsingRecursionRoutine(Employee X) {
        if(X.nexts.isEmpty()) { // base case
            return new Info(X.happy, 0);
        }
        int present = X.happy;
        int absent = 0;
        for (Employee next : X.nexts) {
            Info nextInfo = processUsingRecursionRoutine(next);
            present += nextInfo.absentHappy; // 来情况下，派对的最大快乐值
            absent += Math.max(nextInfo.presentHappy, nextInfo.absentHappy); // 不来情况下，派对的最大快乐值
        }
        return new Info(present, absent);
    }

}
