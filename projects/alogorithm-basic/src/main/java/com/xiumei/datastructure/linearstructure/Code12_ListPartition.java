package com.xiumei.datastructure.linearstructure;

import com.xiumei.util.AlogrithmUtil;
import com.xiumei.util.LogarithmUtil;

/**
 * @Author: yue_zhou
 * @Email: yue_zhou@xinyan.com
 * @Date: 10:51 2020/9/20
 * @Version: 1.0
 * @Description: 将单向链表按某值划分成左边小，中间相等，右边大的形式
 **/
public class Code12_ListPartition {

    public static void main(String[] args) throws Exception {
        int maxSize = 10;
        int maxValue = 5;
        Node head = LogarithmUtil.generateRandomList(Node.class, maxSize, maxValue, false);

//        Node head1 = new Node(4);
//        head1.next = new Node(4);
//        head1.next.next = new Node(2);
//        head1.next.next.next = new Node(1);
        printNode(head);
        Node newH1 = listPartition1(head, 3);
        Node newH2 = listPartition2(head, 3);
        printNode(newH1);
        printNode(newH2);
    }

    /**
     * 链表
     */
    public static class Node {
        public int value;
        public Node next;

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 打印链表元素
     * @param head
     */
    private static void printNode(Node head) {
        if(head == null) {
            System.out.print("null");
            return;
        }
        while (head != null) {
            System.out.print(head.value + "\t");
            head = head.next;
        }
        System.out.println("");
    }

    /**
     * 将链表中的元素放入数组，在数组上做 partition（笔试用）
     * @param head 头节点
     * @param partitionPoint 划分点
     * @return
     */
    public static Node listPartition1(Node head, int partitionPoint) {
        if(head == null) {
            return head;
        }
        // 1- 将链表元素装进数组
        // 1.1- 获取链表长度
        Node curr = head;
        int len = 0; // 链表长度
        while (curr != null) {
            len++;
            curr = curr.next;
        }
        // 1.2- 创建数组，并将链表中元素依次放入数组
        curr = head; // 将 curr 重新指向 head
        Node[] nodeArr = new Node[len];
        for (int i = 0; i < nodeArr.length; i++) {
            nodeArr[i] = curr;
            curr = curr.next;
        }

        // 2- 在数组上玩荷兰国旗问题
        netherlandsFlag(nodeArr, partitionPoint);

        // 3- 将划分的结果值重新组织成链表
        for (int i = 0; i < nodeArr.length-1; i++) {
            nodeArr[i].next = nodeArr[i+1];
        }
        nodeArr[nodeArr.length-1].next = null;
        return nodeArr[0];

    }

    /**
     * 以 partitionPoint 为划分值，在数组 arr[L...R] 范围上玩荷兰国旗问题
     * @param arr 数组
     * @param partitionPoint 划分点
     * @return
     */
    public static void netherlandsFlag(Node[] arr, int partitionPoint) {
        int less = -1; // <区 的右边界
        int more = arr.length; // >区 的左边界
        int index = 0;
        while(index < more) {
            if(arr[index].value == partitionPoint) {
                index++;
            } else if(arr[index].value < partitionPoint) { // <
                AlogrithmUtil.swap(arr, index++, ++less);
            } else { // >
                AlogrithmUtil.swap(arr, index, --more);
            }
        }
    }

    /**
     * 分为小，中，大三部分，再把各部分串起来（面试用）
     * @param head 头节点
     * @param partitionPoint 划分点
     * @return
     */
    public static Node listPartition2(Node head, int partitionPoint) {
        Node sH = null; // small head
        Node sT = null; // small tail
        Node eH = null; // equal head
        Node eT = null; // equal tail
        Node mH = null; // more head
        Node mT = null; // more tail
        Node next = null;

        // 1- 正确设置小，中，大三部分的 head 和 tail
        while (head != null) {
            next = head.next; // 记录下一个变量的环境
            head.next = null; // 切断 head 在原链表中的指向信息，排除对后过程的影响
            if(head.value < partitionPoint) { // 小于区域
                if(sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sT.next = head;
                    sT = head;
                }
            } else if(head.value == partitionPoint) { // 等于区域
                if(eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            } else { // 大于区域
                if(mH == null) {
                    mH = head;
                    mT = head;
                } else {
                    mT.next = head;
                    mT = head;
                }
            }
            head = next;
        }

        // 2- 小，中，大三部分连接
        if(sT != null) { // 存在小于区域
            sT.next = eH;
            eT = eT == null ? sT: eT; // 谁去连大于区域的头，谁就变成 eT
        }
        if(eT != null) { // 小于区域和等于区域至少存在一个
            eT.next = mH;
        }
        return sH != null ? sH : (eH != null ? eH : mH);
    }



}
