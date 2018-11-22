package com.eyesee.algorithms.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code ReverseLink} class represents .
 *
 * @author jessepi on 11/20/18
 */
public class ReverseLink {

    @Test
    public void testReverseNode1() {
        Node head = new Node( 1, null);
        Node node = head;
        for (int i = 1; i < 10; i ++) {
            Node temp = new Node(i + 1, null);
            head.setNextNode(temp);
            head = temp;
        }

        List<Integer> results = result(reverseNode(node));
        IntStream.range(0, 10).forEach(s -> {
            assertThat(results.get(9 - s)).isEqualTo(s + 1);
        });
    }

    private List<Integer> result(Node node) {
        Node temp = node;
        List<Integer> results = new ArrayList<>();
        while (nonNull(temp)) {
            results.add(temp.getValue());
            temp = temp.getNextNode();
        }
        return results;
    }

    public Node reverseNode(Node node) {
        if (isNull(node) || isNull(node.getNextNode())) {
            return node;
        }
        Node head = node;
        Node result = null;
        do {
            Node temp = head.getNextNode();
            head.setNextNode(result);
            result = head;
            head = temp;
        } while (nonNull(head));

        return result;
    }

    //Node1 -> node2 --> node 3 --> node 4 --> 5 --> 6
    //Node1 <- node2 <-- node 3     node4 -->node 5 --> node 6

}
