package com.xiumei.datastructure.tree;

import com.xiumei.util.LogarithmUtil;

import java.util.HashMap;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 23:19 2020/9/14
 * @Version: 1.0
 * @Description: 字典树
 **/
public class TireTree {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int arrMaxLen = 100; // 数组最大长度
        int strMaxLen = 20; // 字符串最大长度
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            String[] arr = LogarithmUtil.generateRandomStrArr(arrMaxLen, strMaxLen);
            TireImplByArr tire1 = new TireImplByArr();
            TireImplByHashTable tire2 = new TireImplByHashTable();
            TireImplByVoliance tire3 = new TireImplByVoliance();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if(decide < 0.25) {
                    tire1.insert(arr[j]);
                    tire2.insert(arr[j]);
                    tire3.insert(arr[j]);
                } else if(decide < 0.5) {
                    tire1.delete(arr[j]);
                    tire2.delete(arr[j]);
                    tire3.delete(arr[j]);
                } else if(decide < 0.75) {
                    int result1 = tire1.search(arr[j]);
                    int result2 = tire2.search(arr[j]);
                    int result3 = tire3.search(arr[j]);
                    if(result1 != result2 || result2 != result3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int result1 = tire1.prefixNum(arr[j]);
                    int result2 = tire2.prefixNum(arr[j]);
                    int result3 = tire3.prefixNum(arr[j]);
                    if(result1 != result2 || result2 != result3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("Nice!");
    }

    /**
     * 数组实现的节点
     */
    public static class NodeImplByArr {
        public int pass; // 通过该节点的次数
        public int end; // 以该节点为结尾的次数
        public NodeImplByArr[] nexts; // 接下来的对象

        public NodeImplByArr() {
            this.pass = 0;
            this.end = 0;
            // 该结构中只保存 a~z 26个字符，那么可以 0 设想为 a，1 设想成 b，...，25 设想成 z
            nexts = new NodeImplByArr[26];
        }
    }

    /**
     * 数组实现的前缀树
     */
    public static class TireImplByArr {
        private NodeImplByArr root;

        public TireImplByArr() {
            this.root = new NodeImplByArr();
        }

        /**
         * 插入某个字符
         * 只支持 a~z 的 26 小写字符
         * @param word 字符串
         */
        public void insert(String word) {
            if(word == null) { // 字符串为空，参数非法~！
                return;
            }
            char[] chars = word.toCharArray();
            NodeImplByArr node = root;
            node.pass++;
            int path = 0;
            for (int i = 0; i < chars.length; i++) { // 从左到右依次遍历字符
                path = chars[i] - 'a'; // 由字符，确定对应走向哪条路。如 a 字符，走下标为 0 的路。
                if(node.nexts[path] == null) { // 之前没有对应的路，创建对应路
                    node.nexts[path] = new NodeImplByArr();
                }
                node = node.nexts[path]; // 下一个节点赋值给 node 变量
                node.pass++; // 因为通过该 node 节点，所以 pass++
            }
            node.end++; // 遍历完所有字符串，是以末尾的字符为结尾的，所以 end++
        }

        /**
         * 搜索 word 单词加入过几次
         * @param word
         */
        public int search(String word) {
            if(word == null) { // 单词为空，非法参数~！必然未加入过
                return 0;
            }
            char[] chars = word.toCharArray();
            NodeImplByArr node = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if(node.nexts[index] == null) { // 遍历单词中字符时，发现没有对应的路，说明字典树中没有该单词
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end; // 以 word 单词末尾字符结尾的单词数
        }

        /**
         * 删除某个字符串
         * 删除时插入的逆过程
         * @param word
         */
        public void delete(String word) {
            if(search(word) == 0) { // 先去搜索字符串是否在字典树中，若存在，则进行删除操作。否则，不做任何操作。
                return;
            }
            char[] chars = word.toCharArray();
            NodeImplByArr node = root;
            node.pass--;
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = chars[i] - 'a';
                if(--node.nexts[path].pass == 0) { // 若首次发现 word 的某个 node 对应的下一个字符 pass 为 1，说明只有后续的子树只能是该 word 所构建的。只需要 node 下方的树置空即可。
                    node.nexts[path] = null;
                    return;
                }
                node = node.nexts[path];
            }
            node.end--; // 若成功走到最后，需要前缀树以该 node 结尾 end-- 即可。
        }

        /**
         * 加入前缀树的字符串中，以 str 字符串为前缀的字符串数量
         * 和 search 过程类似
         * @param str
         * @return
         */
        public int prefixNum(String str) {
            if(str == null) { // 单词为空，非法参数~！必然未加入过
                return 0;
            }
            char[] chars = str.toCharArray();
            NodeImplByArr node = root;
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if(node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }
    }

    /**
     * 哈希表实现的节点
     */
    public static class NodeImplByHashTable {
        public int pass;
        public int end;
        public HashMap<Integer, NodeImplByHashTable> nexts;

        public NodeImplByHashTable() {
            this.pass = 0;
            this.end = 0;
            this.nexts = new HashMap<>();
        }
    }

    /**
     * 哈希表实现前缀树
     */
    public static class TireImplByHashTable {
        private NodeImplByHashTable root;

        public TireImplByHashTable() {
            root = new NodeImplByHashTable();
        }

        /**
         * 插入某个字符
         * @param word 字符串
         */
        public void insert(String word) {
            if(word == null) { // 字符串为空，参数非法~！
                return;
            }
            char[] chars = word.toCharArray();
            NodeImplByHashTable node = root;
            node.pass++;
            int path = 0;
            for (int i = 0; i < chars.length; i++) { // 从左到右依次遍历字符
                path = (int) chars[i];
                if(!node.nexts.containsKey(path)) { // 之前没有对应的路，创建对应路
                    node.nexts.put(path, new NodeImplByHashTable());
                }
                node = node.nexts.get(path); // 下一个节点赋值给 node 变量
                node.pass++; // 因为通过该 node 节点，所以 pass++
            }
            node.end++; // 遍历完所有字符串，是以末尾的字符为结尾的，所以 end++
        }

        /**
         * 搜索 word 单词加入过几次
         * @param word
         */
        public int search(String word) {
            if(word == null) { // 单词为空，非法参数~！必然未加入过
                return 0;
            }
            char[] chars = word.toCharArray();
            NodeImplByHashTable node = root;
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = (int) chars[i];
                if(!node.nexts.containsKey(path)) { // 遍历单词中字符时，发现没有对应的路，说明字典树中没有该单词
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.end; // 以 word 单词末尾字符结尾的单词数
        }

        /**
         * 删除某个字符串
         * 删除时插入的逆过程
         * @param word
         */
        public void delete(String word) {
            if(search(word) == 0) { // 先去搜索字符串是否在字典树中，若存在，则进行删除操作。否则，不做任何操作。
                return;
            }
            char[] chars = word.toCharArray();
            NodeImplByHashTable node = root;
            node.pass--;
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = (int) chars[i];
                if(--node.nexts.get(path).pass == 0) { // 若首次发现 word 的某个 node 对应的下一个字符 pass 为 1，说明只有后续的子树只能是该 word 所构建的。只需要 node 下方的树置空即可。
                    node.nexts.remove(path);
                    return;
                }
                node = node.nexts.get(path);
            }
            node.end--; // 若成功走到最后，需要前缀树以该 node 结尾 end-- 即可。
        }

        /**
         * 加入前缀树的字符串中，以 str 字符串为前缀的字符串数量
         * 和 search 过程类似
         * @param str
         * @return
         */
        public int prefixNum(String str) {
            if(str == null) { // 单词为空，非法参数~！必然未加入过
                return 0;
            }
            char[] chars = str.toCharArray();
            NodeImplByHashTable node = root;
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = (int) chars[i];
                if(!node.nexts.containsKey(path)) {
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.pass;
        }
    }

    /**
     * 暴力方式实现的前缀树
     */
    public static class TireImplByVoliance {
        private HashMap<String, Integer> box;

        public TireImplByVoliance() {
            this.box = new HashMap<>();
        }

        public void insert(String word) {
            if(!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word)+1);
            }
        }

        public void delete(String word) {
            if(box.containsKey(word)) {
                if(box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word)-1);
                }
            }
        }

        public int search(String word) {
            if(!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNum(String str) {
            int count = 0;
            for (String curr : box.keySet()) {
                if(curr.startsWith(str)) {
                    count += box.get(curr);
                }
            }
            return count;
        }

    }

}
