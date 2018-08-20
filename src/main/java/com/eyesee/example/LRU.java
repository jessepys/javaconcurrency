package com.eyesee.example;

import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

public class LRU<K, V> {
    private ConcurrentHashMap<K, Node> map;
    private AtomicLong size;
    private Deque<Node> deque = new LinkedBlockingDeque<>();
    private long capacity;

    public static LRU from(int capacity) {
        LRU lru = new LRU();
        lru.setMap(new ConcurrentHashMap(capacity));
        lru.setCapacity(capacity);

        return lru;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node old = map.get(key);
            deque.remove(old);
            Node node = new Node(key, value);
            deque.addFirst(node);
            map.put(key, node);
        } else {
            if (size.get() + 1 > capacity) {
                deque.removeLast();
            }
            Node node = new Node(key, value);
            deque.addFirst(node);
            map.putIfAbsent(key, node);
            size.incrementAndGet();
        }
    }


    public class Node {
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public Object get(K key) {
        return map.get(key);
    }


    public void setMap(ConcurrentHashMap<K, Node> map) {
        this.map = map;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
}
