/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.adt;

/**
 * Interface for a stack of an arbitrary type.
 * @author nurzhan.sakenov
 * @param <Type>
 */
public interface Stack<Type> {
    
    /**
     * Pushes a value of the given Type to the stack.
     * @param element
     * @return
     */
    public Stack<Type> push(Type element);
    
    /**
     * Pops the last pushed value from the stack, and returns the value, 
     * given the stack is not empty. If the stack is empty, throws an Exception.
     * @return
     */
    public Type pop() throws Exception;
    
    /**
     * @return the size of the stack
     */
    public int getSize();
    
    /**
     * Removes all elements from the stack.
     */
    public void clear();
    
}
