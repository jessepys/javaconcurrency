package com.eyesee.algorithms.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code MergeKSortedLists23} class represents .
 *
 * @author jessepi on 11/30/18
 */
public class MergeKSortedLists23 {

    @Test
    public void testMergeKLists() {
        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(4);
        listNode1.next.next = new ListNode(5);


        ListNode listNode2 = new ListNode(1);
        listNode2.next = new ListNode(3);
        listNode2.next.next = new ListNode(4);

        ListNode listNode3 = new ListNode(2);
        listNode3.next = new ListNode(6);

        ListNode expected = new ListNode(1);
        expected.next = new ListNode(1);
        expected.next.next = new ListNode(2);
        expected.next.next.next = new ListNode(3);
        expected.next.next.next.next = new ListNode(4);
        expected.next.next.next.next.next = new ListNode(5);
        expected.next.next.next.next.next.next = new ListNode(6);

        ListNode[] listNodes = {listNode1, listNode2, listNode3};
        assertListNode(mergeKLists(listNodes), expected);
    }

    private void assertListNode(ListNode result, ListNode expected) {
        if (result == null && null != expected) {
            Assertions.assertThat(true).isFalse();
        }
        while (null != result) {
            Assertions.assertThat(result.val).isEqualTo(expected.val);
            result = result.next;
            expected = expected.next;
        }
    }


    /**
     *  1->4->5,
     *  1->3->4,
     *  2->6
     *
     *  Output: 1->1->2->3->4->4->5->6
     * @param lists
     *
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeLists(Arrays.asList(lists));
    }


    private ListNode mergeLists(List<ListNode> listNodes) {
        int length = listNodes.size();

        if (length > 1) {
            int mid = length >> 1;
            if(length % 2 == 0) {
                ListNode left = mergeLists(listNodes.subList(0, mid));
                ListNode right = mergeLists(listNodes.subList(mid, length));
                return mergeTwoList(left, right);
            } else {
                ListNode left = mergeLists(listNodes.subList(0, mid + 1));
                ListNode right = mergeLists(listNodes.subList(mid + 1, length));
                return mergeTwoList(left, right);
            }
        }

        return mergeTwoList(listNodes.get(0), null);
    }

    private ListNode mergeTwoList(ListNode listNode1, ListNode listNode2) {
        return null;
    }


    public static class ListNode {
      int val;
      ListNode next;

      ListNode(int x) { val = x; }
    }
}
