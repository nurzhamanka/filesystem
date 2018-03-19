/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.adt;

/**
 * The interface for a queue of an arbitrary type.
 * @author nurzhan.sakenov
 * @param <Type> the given type of the data
 */
public interface Queue<Type> {
    
    /**
     * Adds an element to the queue.
     * @param element the value fed to the method
     * @return returns itself
     */
    public Queue<Type> enqueue(Type element);
    
    /**
     * Removes the first element of the queue, given that the queue is not empty.
     * @return returns the removed element.
     */
    public Type dequeue();
    
    /**
     * @return the size of the queue.
     */
    public int getSize();
    
    /**
     * Removes all elements from the queue.
     */
    public void clear();
    
}
