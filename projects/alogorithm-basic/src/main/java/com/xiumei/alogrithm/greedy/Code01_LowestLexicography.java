package com.xiumei.alogrithm.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:01 2020/10/10
 * @Version: 1.0
 * @Description: 字符串拼接问题（字符串拼接最小字典序）
 **/
public class Code01_LowestLexicography {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 50000; // 校验次数
        int arrLen = 6; // 数组最大长度
        int strLen = 5; // 字符串最大长度
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArr(arrLen, strLen);
            String[] arr2 = Arrays.copyOf(arr1, arr1.length);
            if(!lowestStringUsingRecursion(arr1).equals(lowestStringUsingGreedy(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 生成随机字符串
     * @param strLen 字符串最大长度
     * @return
     */
    public static String generateRandomString(int strLen) {
        char[] answer = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < answer.length; i++) {
            int value = (int) (Math.random() * 5);
            // 随机生成大写或小写的字符
            answer[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char)(97 + value);
        }
        return String.valueOf(answer);
    }

    /**
     * 生成随机字符串数组
     * @param arrLen 数组最大长度
     * @param strLen 字符串最大长度
     * @return
     */
    public static String[] generateRandomStringArr(int arrLen, int strLen) {
        String[] answer = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = generateRandomString(strLen);
        }
        return answer;
    }

    /**
     * 使用暴力递归的方式来查找字符串数组拼接的最小字典序
     * @param strs
     * @return
     */
    public static String lowestStringUsingRecursion(String[] strs) {
        if(strs == null || strs.length == 0) {
            return "";
        }
        // 存放所有可能的拼接后的字符串
        ArrayList<String> all = new ArrayList<>();
        // 已使用字符串
        HashSet<Integer> use = new HashSet<Integer>();
        processUsingRecursion(strs, use, "", all);
        String lowest = all.get(0);
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).compareTo(lowest) < 0) {
                lowest = all.get(i);
            }
        }
        return lowest;
    }

    /**
     * 暴力递归方式的具体执行过程
     * @param strs 字符串数组
     * @param used 已经使用过的
     * @param path 已使用过的字符串拼接成的结果
     * @param all 收集所有可能拼成的结果
     */
    private static void processUsingRecursion(String[] strs, HashSet<Integer> used, String path, ArrayList<String> all) {
        if(used.size() == strs.length) {
            all.add(path);
        } else {
            for (int i = 0; i < strs.length; i++) {
                if(!used.contains(i)) {
                    used.add(i);
                    processUsingRecursion(strs, used, path+strs[i], all);
                    used.remove(i); // 恢复现场，做后续递归过程
                }
            }
        }
    }

    /**
     * 使用贪心算法查找字符串数组拼接的最小字典序
     * @param strs
     * @return
     */
    public static String lowestStringUsingGreedy(String[] strs) {
        if(strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return (s1+s2).compareTo(s2+s1);
            }
        });
        String res = "";
        for (int i = 0; i < strs.length; i++) {
            res += strs[i];
        }
        return res;
    }

}
