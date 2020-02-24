package deques;

public class LinkedDeque<T> implements Deque<T> {
    private int size;
    private Node front;
    private Node back;

    public LinkedDeque() {
        size = 0;
        front = null;
        back = null;

    }

    private class Node {
        private T value;
        private Node next;
        private Node prev;

        Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public void addFirst(T item) {
        Node temp = new Node(item, null, null);
        if (size == 0) {
            front = temp;
            back = temp;
        } else {
            temp.next = front;
            front.prev = temp;
            front = temp;
        }
        size += 1;
    }

    public void addLast(T item) {
        Node temp = new Node(item, null, null);
        if (size == 0) {
            front = temp;
            back = temp;
        } else {
            temp.prev = back;
            back.next = temp;
            back = temp;
        }
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T ans = front.value;
        if (size == 1) {
            front = null;
            back = null;
        } else {
            front = front.next;
            front.prev = null;
        }
        size -= 1;
        return ans;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T ans = back.value;
        if (size == 1) {
            front = null;
            back = null;
        } else {
            back = back.prev;
            back.next = null;
        }
        size -= 1;
        return ans;
    }

    public T get(int index) {
        if ((index > size) || (index < 0)) {
            return null;
        }
        Node temp;
        if (index < size - index) {
            temp = front;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        } else {
            temp = back;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
        }
        return temp.value;
    }

    public int size() {
        return size;
    }
}
