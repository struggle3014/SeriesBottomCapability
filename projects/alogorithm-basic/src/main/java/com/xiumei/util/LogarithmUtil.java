package com.xiumei.util;

import java.lang.reflect.Field;

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
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 打印数组
     *
     * @param arr
     */
    public static void printArr(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

    }

    /**
     * 生成随机链表结构
     * @param clazz 链表节点类型
     * @param maxSize 链表的最大长度
     * @param maxValue 节点的最大值
     * @param isSymmetrical 节点值是否是轴对称的
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T generateRandomList(Class<T> clazz, int maxSize, int maxValue, boolean isSymmetrical) throws Exception {
        int randSize = (int) ((maxSize + 1) * Math.random());
        // 创建头节点
        T head = null;
        if (randSize > 0) {
            head = createT(clazz, "value", maxValue, isSymmetrical);
        }
        if(randSize <= 1) {
            return head;
        }
        T prev = head;
        T curr;
        for (int i = 1; i < randSize; i++) {
            curr = createT(clazz, "value", maxValue, isSymmetrical);
            setNext(prev, curr);
            prev = curr;
        }
        return head;

    }

    /**
     * 创建节点对象，并赋节点值
     * @param clazz
     * @param field
     * @param maxValue
     * @param isSymmetrical
     * @param <T>
     * @return
     * @throws Exception
     */
    private static <T> T createT(Class<T> clazz, String field, int maxValue, boolean isSymmetrical) throws Exception {
        T t = clazz.newInstance();
        Field value = t.getClass().getField(field);
        value.setAccessible(true);
        value.set(t, generateRandomNum(maxValue, isSymmetrical));
        return t;
    }

    /**
     * 设置当前接节点的下一个节点
     * @param curr
     * @param next
     * @param <T>
     * @throws Exception
     */
    private static <T> void setNext(T curr, T next) throws Exception {
        Field f = curr.getClass().getField("next");
        f.setAccessible(true);
        f.set(curr, next);
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
            // [-maxValue, +maxValue]
            arr[i] = (int) ((maxValue + 1) * Math.random() - (int) ((maxValue + 1) * Math.random()));
        }
        return arr;
    }

    /**
     * 生成随机数组
     *
     * @param maxSize       最大长度
     * @param maxValue      最大大小，轴对称的
     * @param isSymmetrical 生成数据所在范围是否是对称的
     * @return
     */
    public static int[] generateRandomArr(int maxSize, int maxValue, boolean isSymmetrical) {
        // Math.random()    [0, 1)
        // (maxSize + 1) * Math.random()    [0, maxSize + 1)
        // (int)((maxSize + 1) * Math.random()) [0, maxSize]
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            if (isSymmetrical) {
                // [-maxValue, +maxValue]
                arr[i] = (int) ((maxValue + 1) * Math.random() - (int) ((maxValue + 1) * Math.random()));
            } else {
                // [0, +maxValue]
                arr[i] = (int) ((maxValue + 1) * Math.random());
            }
        }
        return arr;
    }

    /**
     * isSymmetrical 为 true 时，maxValue 范围为 [-maxValue, maxValue]
     * isSymmetrical 为 false 时，maxValue 范围为 [0, maxValue]
     *
     * @param maxValue      数值最大值
     * @param isSymmetrical 生成数据所在范围是否是对称的
     * @return
     */
    public static int generateRandomNum(int maxValue, boolean isSymmetrical) {
        return isSymmetrical ? (int) ((maxValue + 1) * Math.random() - (int) ((maxValue + 1) * Math.random())) : (int) ((maxValue + 1) * Math.random());
    }

    /**
     * 生成随机字符串
     *
     * @param strLen 字符串最大长度
     * @return
     */
    public static String generateRandomStr(int strLen) {
        char[] result = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < result.length; i++) {
            int value = (int) (Math.random() * 6);
            result[i] = (char) (97 + value);
        }
        return String.valueOf(result);
    }

    /**
     * 生成随机字符串数组
     *
     * @param arrLen 数组最大长度
     * @param strLen 字符串最大长度
     * @return
     */
    public static String[] generateRandomStrArr(int arrLen, int strLen) {
        String[] result = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = generateRandomStr(strLen);
        }
        return result;
    }

}
