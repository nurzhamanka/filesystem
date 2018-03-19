/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.impl;

/**
 * A generic Tree Node.
 * @author nurzhan.sakenov
 * @param <Type>
 */
public class TreeNode<Type> {
    
    private Type value;
    private TreeNode left;
    private TreeNode right;
    
    public TreeNode(Type value, TreeNode<Type> left, TreeNode<Type> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
    
    public TreeNode(Type value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /**
     * @return the value
     */
    public Type getValue() {
        return value;
    }

    /**
     * @return the left
     */
    public TreeNode<Type> getLeft() {
        return left;
    }

    /**
     * @return the right
     */
    public TreeNode<Type> getRight() {
        return right;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Type value) {
        this.value = value;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(TreeNode<Type> left) {
        this.left = left;
    }

    /**
     * @param right the right to set
     */
    public void setRight(TreeNode<Type> right) {
        this.right = right;
    }
    
    public String toString() {
        return value.toString();
    }
}
