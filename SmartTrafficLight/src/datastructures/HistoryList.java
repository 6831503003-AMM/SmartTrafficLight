package datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom generic Doubly-Linked List used to store processed vehicle history.
 * Supports forward iteration and recursive display.
 */
public class HistoryList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public static class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;
        Node(T data) { this.data = data; }
    }

    /** Append element to the end of the list. */
    public void addLast(T item) {
        Node<T> node = new Node<>(item);
        if (tail != null) {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        if (head == null) head = node;
        size++;
    }

    /** Remove the last element (used for undo). */
    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException("History is empty.");
        T data = tail.data;
        tail = tail.prev;
        if (tail != null) tail.next = null; else head = null;
        size--;
        return data;
    }

    public Node<T> getHead()  { return head; }
    public boolean isEmpty()  { return size == 0; }
    public int     size()     { return size; }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node<T> cur = head;
            public boolean hasNext() { return cur != null; }
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T d = cur.data;
                cur = cur.next;
                return d;
            }
        };
    }
}
