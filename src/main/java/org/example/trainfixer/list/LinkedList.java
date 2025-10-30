package org.example.trainfixer.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic singly linked list with O(1) append using tail.
 */
public class LinkedList<T> implements Iterable<T> {

    private static final class Node<T> {
        T value;
        Node<T> next;
        Node(T v) { this.value = v; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void addFirst(T value) {
        Node<T> n = new Node<>(value);
        if (head == null) {
            head = tail = n;
        } else {
            n.next = head;
            head = n;
        }
        size++;
    }

    public void addLast(T value) {
        Node<T> n = new Node<>(value);
        if (tail == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
    }

    public T removeFirst() {
        if (head == null) throw new NoSuchElementException("List is empty");
        T v = head.value;
        head = head.next;
        if (head == null) tail = null; // list became empty
        size--;
        return v;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    // Append another list and clear it
    public void appendList(LinkedList<T> other) {
        if (other == null || other.isEmpty()) return;
        if (this.isEmpty()) {
            this.head = other.head;
            this.tail = other.tail;
        } else {
            this.tail.next = other.head;
            this.tail = other.tail;
        }
        this.size += other.size;
        other.head = other.tail = null;
        other.size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> cur = head;
            @Override public boolean hasNext() { return cur != null; }
            @Override public T next() {
                if (cur == null) throw new NoSuchElementException();
                T v = cur.value;
                cur = cur.next;
                return v;
            }
        };
    }
}
