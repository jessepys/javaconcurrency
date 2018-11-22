package com.eyesee.algorithms.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * The {@code MergeTwoSortedLink} class represents .
 *
 * @author jessepi on 11/20/18
 */
public class MergeTwoSortedLink {
    private static final Logger LOG = LoggerFactory.getLogger(MergeTwoSortedLink.class);

    private List<Integer> result(Node node) {
        Node temp = node;
        List<Integer> results = new ArrayList<>();
        while (nonNull(temp)) {
            results.add(temp.getValue());
            temp = temp.getNextNode();
        }
        return results;
    }

    @Test
    public void testMergeTwoSortedLink() {
        Node tempNode = new Node(1, null);
        Node firstNode = tempNode;
        for (int i = 1; i < 10; i = i + 2) {
            Node node = new Node(i + 1, null);
            tempNode.setNextNode(node);
            tempNode = node;
        }
        LOG.info("the first node: {}", result(firstNode));
        Node temp2Node = new Node(5, null);
        Node secondNode = temp2Node;
        for (int i = 8; i <= 10; i ++) {
            Node node = new Node(i , null);
            temp2Node.setNextNode(node);
            temp2Node = node;
        }
        LOG.info("the second node: {}", result(secondNode));

        Node result = mergeTwoSortedLink(firstNode, secondNode);
        LOG.info("the result: {}", result(result));
    }

    // 1 -> 2 -> 3 -> 4
    // 5 -> 8 -> 9 -> 10 ->12
    public Node mergeTwoSortedLink(Node firstNode, Node secondNode) {
        if (Objects.isNull(firstNode)) {
            return secondNode;
        }
        if (Objects.isNull(secondNode)) {
            return firstNode;
        }
        Node firstTempNode = firstNode;
        Node secondTempNode = secondNode;
        Node result = null;
        Node resultHead = null;
        do {
            if (nonNull(firstTempNode) && nonNull(secondTempNode)) {
                Node tempResult = null;
                if (firstTempNode.getValue() <= secondTempNode.getValue()) {
                    tempResult = firstTempNode;
                    firstTempNode = firstTempNode.getNextNode();
                    tempResult.setNextNode(null);
                } else {
                    tempResult = secondTempNode;
                    secondTempNode = secondTempNode.getNextNode();
                    tempResult.setNextNode(null);
                }
                if (nonNull(result)) {
                    result.setNextNode(tempResult);
                    result = result.getNextNode();
                } else {
                    resultHead = tempResult;
                    result = tempResult;
                }
            } else if (nonNull(firstTempNode)) {
                Node tempResult = firstTempNode;
                firstTempNode = firstTempNode.getNextNode();
                tempResult.setNextNode(null);
                result.setNextNode(tempResult);
                result = result.getNextNode();
            } else if (nonNull(secondTempNode)) {
                Node tempResult = secondTempNode;
                secondTempNode = secondTempNode.getNextNode();
                tempResult.setNextNode(null);
                result.setNextNode(tempResult);
                result = result.getNextNode();
            }
        } while (nonNull(firstTempNode) || nonNull(secondTempNode));

        return resultHead;
    }

}
