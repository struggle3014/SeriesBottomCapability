package com.xiumei.datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 16:06 2020/9/24
 * @Version: 1.0
 * @Description: 序列化与反序列化 Tree
 * 1）深度优先遍历（先序，中序，后序）的方式
 * 2）广度优先遍历的方式
 **/
public class Code06_SerializeDeserializeTree {

    public static void main(String[] args) {
        // 1- 对数器基础参数设置
        int testTimes = 500000; // 校验次数
        int maxLevel = 3; // 最大深度
        int maxValue = 100; // 最大大小
        boolean isSuccess = true;
        // 2- 对数过程执行
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> serPreorder = serializeByPreorder(head);
            Queue<String> serPosorder = serializeByPostorder(head);
            Queue<String> serLevel = serializeByLevel(head);
            Node deserPreorder = deserializeByPreorder(serPreorder);
            Node deserPostorder = deserializeByPostorder(serPosorder);
            Node deserLevel = deserializeByLevel(serLevel);
            if(!isSameValueStructure(deserPreorder, deserPostorder) || !isSameValueStructure(deserPostorder, deserLevel)) {
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice！" : "Fucking fucked！");
    }

    /**
     * 生成随机二叉树
     * @param maxLevel 最大层数
     * @param maxValue 最大值
     * @return
     */
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    /**
     * 生成随机二叉树
     * @param level
     * @param maxLevel
     * @param maxValue
     * @return
     */
    public static Node generate(int level, int maxLevel, int maxValue) {
        if(level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level+1, maxLevel, maxValue);
        head.right = generate(level+1, maxLevel, maxValue);
        return head;
    }

    /**
     * 校验两个树结构及值是否一致
     * @param head1
     * @param head2
     * @return
     */
    public static boolean isSameValueStructure(Node head1, Node head2) {
        if(head1 == null && head2 != null) {
            return false;
        }
        if(head1 != null && head2 == null) {
            return false;
        }
        if(head1 == null && head2 == null) {
            return true;
        }
        if(head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    /**
     * 节点
     */
    public static class Node {
        public int value; // 节点值
        public Node left; // 左孩子
        public Node right; // 右孩子

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 先序方式进行序列化
     * @param head
     * @return
     */
    public static Queue<String> serializeByPreorder(Node head) {
        Queue<String> result = new LinkedList<>();
        serByPreorder(head, result);
        return result;
    }

    /**
     * 先序方式进行序列化
     * @param head
     * @param result
     */
    public static void serByPreorder(Node head, Queue<String> result) {
        if(head == null) {
            result.add(null);
        } else {
            result.add(String.valueOf(head.value));
            serByPreorder(head.left, result);
            serByPreorder(head.right, result);
        }
    }

    /**
     * 先序方式进行反序列化
     * @param list
     * @return
     */
    public static Node deserializeByPreorder(Queue<String> list) {
        if(list == null || list.size() == 0) {
            return null;
        }
        return deserByPreorder(list);
    }

    /**
     * 先序方式进行反序列化
     * @param list
     * @return
     */
    public static Node deserByPreorder(Queue<String> list) {
        String value = list.poll();
        if(value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.left = deserByPreorder(list);
        head.right = deserByPreorder(list);
        return head;
    }

    /**
     * 中序方式进行序列化
     * @param head
     * @return
     */
    public static Queue<String> serializeByInorder(Node head) {
        Queue<String> result = new LinkedList<>();
        serByInorder(head, result);
        return result;
    }

    /**
     * 中序方式进行序列化
     * @param head
     * @param result
     */
    public static void serByInorder(Node head, Queue<String> result) {
        if(head == null) {
            result.add(null);
        } else {
            serByInorder(head.left, result);
            result.add(String.valueOf(head.value));
            serByInorder(head.right, result);
        }
    }

    /**
     * 中序方式进行反序列化
     * @param list
     * @return
     */
    public static Node deserializeByInorder(Queue<String> list) {
        // TODO ... 待实现
        return null;
    }

    /**
     * 中序方式进行反序列化
     * @param list
     * @return
     */
    public static Node deserByInorder(Queue<String> list) {
        // TODO ... 待实现
        return null;
    }

    /**
     * 后序方式进行序列化
     * @param head
     * @return
     */
    public static Queue<String> serializeByPostorder(Node head) {
        Queue<String> result = new LinkedList<>();
        serByPostorder(head, result);
        return result;
    }

    /**
     * 后序方式进行序列化
     * @param head
     * @param result
     */
    public static void serByPostorder(Node head, Queue<String> result) {
        if(head == null) {
            result.add(null);
        } else {
            serByPostorder(head.left, result);
            serByPostorder(head.right, result);
            result.add(String.valueOf(head.value));
        }
    }

    /**
     * 后序方式进行反序列化
     * @param list
     * @return
     */
    public static Node deserializeByPostorder(Queue<String> list) {
        if(list == null || list.size() == 0) {
            return null;
        }
        // 后序遍历顺序（左右头） -> Stack（头右左）
        Stack<String> stack = new Stack<>();
        while (!list.isEmpty()) {
            stack.push(list.poll());
        }
        return deserByPostorder(stack);
    }

    /**
     * 后序方式进行反序列化
     * @param stack
     * @return
     */
    public static Node deserByPostorder(Stack<String> stack) {
        String value = stack.pop();
        if(value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.right = deserByPostorder(stack);
        head.left = deserByPostorder(stack);
        return head;
    }

    /**
     * 深度遍历序列化树
     * 1）左（右）孩子不为空，既序列化又加队列。
     * 2）左（右）孩子为空，只序列化不加队列。
     * @param head
     * @return
     */
    public static Queue<String> serializeByLevel(Node head) {
        Queue<String> result = new LinkedList<>();
        if(head == null) {
            result.add(null);
        } else {
            result.add(String.valueOf(head.value));
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll();
                if(head.left != null) {
                    result.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                } else {
                    result.add(null);
                }
                if(head.right != null) {
                    result.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                } else {
                    result.add(null);
                }
            }
        }
        return result;
    }

    /**
     * 宽度遍历进行反序列化
     * @param levelList
     * @return
     */
    public static Node deserializeByLevel(Queue<String> levelList) {
        if(levelList == null || levelList.size() == 0) {
            return null;
        }
        Node head = generateNode(levelList.poll());
        Queue<Node> queue = new LinkedList<>();
        if(head != null) {
            queue.add(head);
        }
        Node node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(levelList.poll());
            node.right = generateNode(levelList.poll());
            if(node.left != null) {
                queue.add(node.left);
            }
            if(node.right != null) {
                queue.add(node.right);
            }
        }
        return head;
    }

    /**
     * 生成节点
     * @param val
     * @return
     */
    private static Node generateNode(String val) {
        if(val == null) {
            return null;
        }
        return new Node(Integer.valueOf(val));
    }


}
