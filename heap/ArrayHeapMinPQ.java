package heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> nodes;
    private HashMap<T, Integer> map;

    public ArrayHeapMinPQ() {
        nodes = new ArrayList<>();
        map = new HashMap<>();
    }

    private class Node {
        private T item;
        private double priority;

        Node(T e, double p) {
            this.item = e;
            this.priority = p;
        }
    }

    /*
    Here's a helper method and a method stub that may be useful. Feel free to change or remove
    them, if you wish.
     */

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        Node temp = nodes.get(a);
        map.put(nodes.get(b).item, a);
        map.put(nodes.get(a).item, b);
        nodes.set(a, nodes.get(b));
        nodes.set(b, temp);
    }

    /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing).
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (map.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node(item, priority);
        nodes.add(newNode);
        map.put(item, nodes.size() - 1);
        maintainMinSwim(nodes.size() - 1);
    }

    private void maintainMinSwim(int index) {
        int parent = (index - 1) / 2;
        if (index > 0 && nodes.get(index).priority < nodes.get(parent).priority) {
            swap(index, parent);
            maintainMinSwim(parent);
        }
    }

    private void maintainMinSink(int index) {
        int lChild = index * 2 + 1;
        int rChild = index * 2 + 2;
        if (lChild < nodes.size()) {
            if (nodes.get(index).priority > nodes.get(lChild).priority) {
                swap(index, lChild);
                maintainMinSink(lChild);
            }
        }
        if (rChild < nodes.size()) {
            if (nodes.get(index).priority > nodes.get(rChild).priority) {
                swap(index, rChild);
                maintainMinSink(rChild);
            }
        }
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (nodes.isEmpty()) {
            throw new NoSuchElementException();
        }
        return nodes.get(0).item;
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeSmallest() {
        if (nodes.isEmpty()) {
            throw new NoSuchElementException();
        }
        T ans = nodes.get(0).item;
        swap(0, nodes.size() - 1);
        map.remove(ans);
        nodes.remove(nodes.size() - 1);
        maintainMinSink(0);
        return ans;
    }
    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!map.containsKey(item)) {
            throw new NoSuchElementException();
        }
        double curr = nodes.get(map.get(item)).priority;
        nodes.get(map.get(item)).priority = priority;
        if (curr < priority) {
            maintainMinSink(map.get(item));
        } else if (curr > priority) {
            maintainMinSwim(map.get(item));
        }
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return nodes.size();
    }
}
