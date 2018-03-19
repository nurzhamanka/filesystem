/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.impl;

import csci152.adt.Stack;

/**
 * A Linked-List implementation of Stack.
 * Starts with a node, then adds more as the stack grows.
 * Outputs its contents using StringBuilder.
 * @author nurzhan.sakenov
 * @param <Type>
 */
public class LinkedListStack<Type> implements Stack<Type> {
    
    private Node<Type> first; // the first node of the stack
    private int total; // the number of items inside the stack
    
    /**
     *
     */
    public LinkedListStack() {
        total = 0;
    }
    
    public LinkedListStack<Type> push(Type element) {
        
        Node current = first; // writes first into current
        first = new Node(element); // first becomes something new
        first.setNext(current); // linking first with previous (current)
        total++;
        return this;
    }

    public Type pop() throws Exception {
        
        if (first == null) throw new Exception("Cannot pop from an empty stack!");
        Type element = first.getValue(); // picks the top
        first = first.getNext(); // throws away the top
        total--;
        return element;
    }
    
    public int getSize() {
        return total;
    }
    
    public void clear() {
        total = 0;
        first = null;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        Node<Type> temp = first;
        while (temp != null) {
            sb.append(temp.getValue() + "\n");
            temp = temp.getNext();
        }
        return sb.toString();
    }
    
}
