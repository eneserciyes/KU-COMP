package code;

import java.util.Comparator;
import java.util.List;

import given.iMap;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {
  
  /*
   * 
   * YOUR CODE BELOW THIS
   * 
   */
  
  @Override
  public Value get(Key k) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Value put(Key k, Value v) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Value remove(Key k) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iterable<Key> keySet() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public BinaryTreeNode<Key, Value> getRoot() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return node.getParent();
  }

  @Override
  public boolean isInternal(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return !node.isExternal();
  }

  @Override
  public boolean isExternal(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return node.isExternal();
  }

  @Override
  public boolean isRoot(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return node.isRoot();
  }

  @Override
  public BinaryTreeNode<Key, Value> getNode(Key k) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Value getValue(Key k) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setComparator(Comparator<Key> C) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Comparator<Key> getComparator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> ceiling(Key k) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> floor(Key k) {
    // TODO Auto-generated method stub
    return null;
  }
}
