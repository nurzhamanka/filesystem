/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci152.impl;

import csci152.adt.Set;

/**
 *
 * @author nurzhan.sakenov
 * @param <Type>
 */
public class BSTSet<Type extends Comparable> implements Set<Type> {
    
    private TreeNode<Type> root, max, min;
    private int size;
    
    public BSTSet() {
        root = null;
        size = 0;
    }
    
    @Override
    public void add(Type value) {
        root = addHelper(root, value);
    }

    private TreeNode<Type> addHelper(TreeNode<Type> node, Type value) {
        
        if (node == null) {
            node = new TreeNode(value);
            size++;
            return node;
        }
        
        if (value.compareTo(node.getValue()) < 0) {
            
            if (node.getLeft() == null) {
                node.setLeft(new TreeNode(value));
                size++;
            }
            else 
                addHelper(node.getLeft(), value);
            
        } else if (value.compareTo(node.getValue()) > 0) {
            
            if (node.getRight() == null) {
                node.setRight(new TreeNode(value));
                size++;
            }
            else
                addHelper(node.getRight(), value);
        }
        
        return node;
    }
    
    @Override
    public boolean contains(Type value) {
        
        return containsHelper(root, value);
    }

    private boolean containsHelper(TreeNode<Type> node, Type value) {
        
        if (node == null) 
            return false;
        else
            return (node.getValue().equals(value)) || (containsHelper(node.getLeft(), value))
                    || (containsHelper(node.getRight(), value));
    }

    @Override
    public boolean remove(Type value) {
        
        if ((size == 0) || (!contains(value)))
            return false;
        else {
            root = removeHelper(root, value);
            return true;
        }
        
    }
    
    private TreeNode removeHelper(TreeNode<Type> node, Type value) {
        
        // base case (empty subtree)
        if (node == null) {
            return node;
        }
        
        // recurse down
        if (value.compareTo(node.getValue()) < 0)
            node.setLeft(removeHelper(node.getLeft(), value));
        else if (value.compareTo(node.getValue()) > 0)
            node.setRight(removeHelper(node.getRight(), value));
        
        // when the node to be deleted is found
        else {
            // one or no children
            if (node.getLeft() == null) {
                size--;
                return node.getRight();
            }
            else if (node.getRight() == null) {
                size--;
                return node.getLeft();
            }
            
            // two children
            // replace that node with the minimum value of the right subtree
            node.setValue(minimum(node.getRight()));
            // remove that minimum from the right subtree
            node.setRight(removeHelper(node.getRight(), node.getValue()));
        }
        
        return node;
    }
    
    private Type minimum(TreeNode<Type> node) {
        
        Type min = node.getValue();
        while (node.getLeft() != null) {
            min = node.getLeft().getValue();
            node = node.getLeft();
        }
        return min;
    }

    @Override
    public Type removeAny() {
        Type removed = root.getValue();
        remove(removed);
        return removed;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    
    @Override
    public String toString() {
        if (size == 0) return "The set is empty.";
        return toStringHelper(root);
    }
    
    private String toStringHelper(TreeNode<Type> node) {
        
        if (node == null) return "";
        return toStringHelper(node.getLeft()) + " " + node.getValue() + " " + 
                toStringHelper(node.getRight());
    }
    
}
