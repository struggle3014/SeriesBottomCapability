package com.xiumei.alogrithm.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 10:18 2020/10/12
 * @Version: 1.0
 * @Description: 会议安排场次最多问题
 **/
public class Code04_BestMeetingArrange {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 50000; // 校验次数
        int timeMax = 20; // 随机会议的最大的时间（包括开始时间和结束时间）
        int programSize = 12; // 随机会议列表最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if(bestArrangeUsingRecursion(programs) != bestArrangeUsingGreedy(programs)) {
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 生成随机会议列表
     * @param programSize 随机会议列表最大大小
     * @param timeMax 随机会议的最大的时间（包括开始时间和结束时间）
     * @return
     */
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] answer = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < answer.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if(r1 == r2) {
                answer[i] = new Program(r1, r1+1);
            } else {
                answer[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return answer;
    }

    /**
     * 会议实体
     */
    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 使用暴力递归方式解决会议安排场次最多问题
     * @return
     */
    public static int bestArrangeUsingRecursion(Program[] programs) {
        if(programs == null || programs.length == 0) {
            return 0;
        }
        return processUsingRecursion(programs, 0, 0);
    }

    /**
     * 使用暴力递归方式解决会议安排场次最多问题实际过程
     * @param programs 还未安排的会议
     * @param done 已安排的会议数量
     * @param timeLine 目前来的时间点
     * @return
     */
    private static int processUsingRecursion(Program[] programs, int done, int timeLine) {
        if(programs.length == 0) {
            return done;
        }
        // 还有会议可以选择
        int max = done;
        // 穷举任意会议安排之后的后续
        for (int i = 0; i < programs.length; i++) {
            if(programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, processUsingRecursion(next, done+1, programs[i].end));
            }
        }
        return max;
    }

    /**
     * 拷贝除 programs 中 i 索引的元素
     * @param programs
     * @param i
     * @return
     */
    private static Program[] copyButExcept(Program[] programs, int i) {
        Program[] answer = new Program[programs.length - 1];
        int index = 0;
        for (int j = 0; j < programs.length; j++) {
            if(j != i) {
                answer[index++] = programs[j];
            }
        }
        return answer;
    }

    /**
     * 会议比较器
     */
    public static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    /**
     * 使用贪心算法解决会议安排场次最多问题
     * @param programs
     * @return
     */
    public static int bestArrangeUsingGreedy(Program[] programs) {
        // 按照会议时间先后进行排序
        Arrays.sort(programs, new ProgramComparator());
        int timeLine = 0; // 记录会议已安排到的时间节点
        int result = 0; // 会议数量
        for (int i = 0; i < programs.length; i++) {
            if(timeLine <= programs[i].start) {
                result++;
                timeLine = programs[i].end;
            }
        }
        return result;
    }

}
