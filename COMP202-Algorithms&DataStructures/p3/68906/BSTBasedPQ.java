package code;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

  /*
   * 
   * YOUR CODE BELOW THIS
   * 
   */
  
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
