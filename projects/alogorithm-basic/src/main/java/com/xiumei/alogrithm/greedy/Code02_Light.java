package com.xiumei.alogrithm.greedy;

import java.util.HashSet;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 17:35 2020/10/10
 * @Version: 1.0
 * @Description: 居民点放灯问题
 **/
public class Code02_Light {

    public static void main(String[] args) {
        int testTimes = 500000; // 校验次数
        int len = 20; // 随机字符串的最大长度
        boolean isSuccess = true;
        for (int i = 0; i < testTimes; i++) {
            String str = randomStr(len);
            int answer1 = minLightUsingRecursion(str);
            int answer2 = minLightUsingGreedy(str);
            if(answer1 != answer2) {
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 生成由 'X' 和 '.' 构成的字符串
     * @param len
     * @return
     */
    public static String randomStr(int len) {
        char[] result = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(result);
    }

    /**
     * 使用暴力递归解决居民点放灯问题
     * @param road
     * @return
     */
    public static int minLightUsingRecursion(String road) {
        if(road == null || road.length() == 0) {
            return 0;
        }
        return processUsingRecursion(road.toCharArray(), 0, new HashSet<Integer>());
    }

    /**
     * 使用（纯）暴力递归解决居民点放灯问题的处理过程
     * @param str
     * @param index
     * @param lights
     * @return
     */
    private static int processUsingRecursion(char[] str, int index, HashSet<Integer> lights) {
        if(index == str.length) { // 结束时
            for (int i = 0; i < str.length; i++) { // 判断对应的方案是否有效
                if(str[i] != 'X') { // 当前位置是 .
                    if(!(lights.contains(i-1) || lights.contains(i) || lights.contains(i+1))) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else { // str 还未结束
            // index 所在的位置可能是 'X' 或者是 '.'，需分情况讨论
            int no = processUsingRecursion(str, index+1, lights); // index 位置在不放灯情况下，做后续递归操作，返回需要的灯数量
            int yes = Integer.MAX_VALUE; // index 位置在放灯情况下
            if(str[index] == '.') { // index 位置为 '.' 的情况下才需要放灯，否则不放灯，来到 index+1 位置做后续判断
                lights.add(index);
                yes = processUsingRecursion(str, index+1, lights);
                lights.remove(index); // 恢复现场，不要影响后续过程
            }
            return Math.min(no, yes); // 获取 index 位置放灯或不放灯需要更小的灯的数量
        }
    }

    /**
     * 使用贪心算法解决居民点放灯问题
     * @param raod
     * @return
     */
    public static int minLightUsingGreedy(String raod) {
        char[] str = raod.toCharArray();
        int index = 0;
        int light = 0;
        while (index < str.length) {
            if(str[index] == 'X') {
                index++;
            } else { // i 位置为 '.'
                light++;
                if(index+1 == str.length) {
                    break;
                } else {
                    if(str[index+1] == 'X') {
                        index = index + 2;
                    } else {
                        index = index + 3;
                    }
                }
            }
        }
        return light;
    }

}
