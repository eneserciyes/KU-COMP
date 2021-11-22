package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 * 
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {
  
  /*
   * 
   * YOUR CODE HERE
   * 
   */
  // 0 - LEFT; 1- RIGHT
  private int leftOrRight;
  private boolean isExternal;
  private boolean isRoot;

  private BinaryTreeNode leftChild;

  private BinaryTreeNode rightChild;
  private BinaryTreeNode parent;


  public BinaryTreeNode(Key k, Value v) {
    super(k, v);

    /*
     *
     * This constructor is needed for the autograder. You can fill the rest to your liking.
     * YOUR CODE AFTER THIS:
     *
     */
  }

  public boolean isRoot() {
    return isRoot;
  }

  public void setRoot(boolean root) {
    isRoot = root;
  }

  public int getLeftOrRight() {
    return leftOrRight;
  }

  public void setLeftOrRight(int leftOrRight) {
    this.leftOrRight = leftOrRight;
  }

  public boolean isExternal() {
    return isExternal;
  }

  public void setExternal(boolean external) {
    isExternal = external;
  }

  public code.BinaryTreeNode getLeftChild() {
    return leftChild;
  }

  public void setLeftChild(code.BinaryTreeNode leftChild) {
    this.leftChild = leftChild;
  }

  public code.BinaryTreeNode getRightChild() {
    return rightChild;
  }

  public void setRightChild(code.BinaryTreeNode rightChild) {
    this.rightChild = rightChild;
  }

  public code.BinaryTreeNode getParent() {
    return parent;
  }

  public void setParent(code.BinaryTreeNode parent) {
    this.parent = parent;
  }
}
