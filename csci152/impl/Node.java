/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.impl;

/**
 *
 * @author nurzhan.sakenov
 */
public class Node<Type> {
    
    private Type value;
    private Node next, prev;
    private int priority;
    
    // generic node
    public Node(Type value) {
        this.value = value;
        next = null;
        prev = null;
    }
    
    // for priority queue
    public Node(Type value, int key) {
        this.value = value;
        this.priority = key;
        next = null;
        prev = null;
    }

    /**
     * @return the value
     */
    public Type getValue() {
        return value;
    }

    /**
     * @return the next
     */
    public Node getNext() {
        return next;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Type value) {
        this.value = value;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Node next) {
        this.next = next;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the previous link
     */
    public Node getPrev() {
        return prev;
    }

    /**
     * @param prev the previous link to set
     */
    public void setPrev(Node prev) {
        this.prev = prev;
    }
}
