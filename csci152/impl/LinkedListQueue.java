/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.impl;

import csci152.adt.Queue;

/**
 *
 * @author nurzhan.sakenov
 */
public class LinkedListQueue<Type> implements Queue<Type> {
    
    private int total;
    private Node<Type> first, last;
    
    public LinkedListQueue() {
        total = 0;
        first = null;
        last = null;
    }

    @Override
    public Queue<Type> enqueue(Type element) {
        
        Node current = last;
        last = new Node(element);
        
        if (total++ == 0) first = last;
        else current.setNext(last);
        
        return this;
    }

    @Override
    public Type dequeue(){
        
        if (total == 0) throw new java.util.NoSuchElementException();
        Type element = first.getValue();
        first = first.getNext();
        if (--total == 0) last = null;
        return element;  
    }
    
    public int getSize() {
        return total;
    }
    
    public void clear() {
        total = 0;
        first = null;
        last = null;
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