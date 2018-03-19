/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.impl;

import csci152.adt.SortedQueue;

/**
 * A Linked-List implementation of Sorted Queue.
 * Can sort in ascending order.
 * @author nurzhan.sakenov
 * @param <Type>
 */
public class LinkedListSortedQueue<Type extends Comparable> implements SortedQueue<Type> {
    
    private int total;
    private Node<Type> first;
    
    public LinkedListSortedQueue() {
        total = 0;
        first = null;
    }

    @Override
    public void insert(Type value) {
        
        Node<Type> prev = null;
        Node<Type> current = first;
        Node<Type> toInsert = new Node(value);
        
        // ascending sorting
        while (current != null && value.compareTo(current.getValue()) > 0) {
            prev = current;
            current = current.getNext();
        }
        
        if (prev == null) {
            // inserts the element at the head of the queue
            toInsert.setNext(first);
            first = toInsert;
            //System.out.println("Inserted " + value + ", first!");
            total++;
            
        } else {
            
            prev.setNext(toInsert);
            toInsert.setNext(current);
            total++;
            //System.out.println("Inserted " + value);
            
        }
    }

    @Override
    public Type dequeue() throws Exception {
        
        if (total == 0) throw new java.util.NoSuchElementException();
        Type toDelete = first.getValue();
        first = first.getNext();
        total--;
        return toDelete;
    }

    @Override
    public int getSize() {
        return total;
    }

    @Override
    public void clear() {
        total = 0;
        first = null;
    }
    
    public String toString() {
        if (getSize() == 0) return "";
        StringBuilder sb = new StringBuilder();
        Node<Type> temp = first;
        while (temp != null) {
            sb.append(temp.getValue() + "\n");
            temp = temp.getNext();
        }
        return sb.toString();
    }
    
}
