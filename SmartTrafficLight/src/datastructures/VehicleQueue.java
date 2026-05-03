package datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Custom generic Queue implemented with a singly-linked list.
 * Supports FIFO ordering, iteration, and peek.
 */
public class VehicleQueue<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    /** Add an element to the rear of the queue. */
    public void enqueue(T item) {
        Node<T> node = new Node<>(item);
        if (tail != null) tail.next = node;
        tail = node;
        if (head == null) head = node;
        size++;
    }

    /** Remove and return the element at the front. */
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty.");
        T data = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return data;
    }

    /** Return the front element without removing it. */
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty.");
        return head.data;
    }

    public boolean isEmpty() { return size == 0; }
    public int     size()    { return size; }

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
