package datastructures;

import java.util.EmptyStackException;

/**
 * Custom generic Stack implemented with a singly-linked list.
 * Supports LIFO ordering — used for undo functionality.
 */
public class VehicleStack<T> {

    private Node<T> top;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next; }
    }

    /** Push an element onto the top of the stack. */
    public void push(T item) {
        top = new Node<>(item, top);
        size++;
    }

    /** Pop and return the top element. */
    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /** Peek at the top element without removing it. */
    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return top.data;
    }

    public boolean isEmpty() { return size == 0; }
    public int     size()    { return size; }
}
