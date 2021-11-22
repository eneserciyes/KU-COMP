package code;

import java.util.ArrayList;
import java.util.Comparator;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 * 
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {
  
  // Use this arraylist to store the nodes of the heap. 
  // This is required for the autograder. 
  // It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but then you do not have to deal with dynamic resizing
  protected ArrayList<Entry<Key,Value>> nodes;
  
  /*
   * 
   * YOUR CODE BELOW THIS
   * 
   */

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
  public void setComparator(Comparator<Key> C) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Comparator<Key> getComparator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void insert(Key k, Value v) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Entry<Key, Value> pop() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Entry<Key, Value> top() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Value remove(Key k) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Key replaceKey(Entry<Key, Value> entry, Key k) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Key replaceKey(Value v, Key k) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Value replaceValue(Entry<Key, Value> entry, Value v) {
    // TODO Auto-generated method stub
    return null;
  }
  
}
