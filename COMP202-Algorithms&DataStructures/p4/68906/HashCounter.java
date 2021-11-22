package code;

import given.AbstractHashMap;
import given.iPrintable;

import java.util.Objects;

/*
 * A class that counts the number of items with no removal or decrement. 
 * Each time an item is input to the class, it should increase its count.
 * When an item that has not been input is asked, it should return 0. 
 *    *   
 * When input the follow [a,b,a,a,b,c], the class should return:
 * - 4 for a
 * - 2 for b
 * - 1 for c
 * - 0 for d
 * 
 * It should also keep track of the number of items and the sum of all the items
 * 
 * It is setup as a composition based design instead of an inheritance based one.
 * This makes it easier to test multiple hashmaps and shows you another
 * way to achieve polymorphism
 * 
 * You are not allowed to use any existing java data structures other than for the keyset method
 * 
 */

public class HashCounter<Key> implements iPrintable<Key> {
  
  AbstractHashMap<Key,Integer> A;
  
  // Total count of all items
  int s;
  
  /*
   * ADD MORE FIELDS IF NEEDED
   * 
   */

  /*
   * ADD A NESTED CLASS IF NEEDED
   * 
   */
  
  // The hashmap to be used for counting will be supplied from the outside. 
  // Not the best choice for production code!
  public HashCounter(AbstractHashMap<Key,Integer> inContainer) {
    A = inContainer;
    
    /*
     * ADD MORE CODE IF NEEDED
     * 
     */
  }
  
  //Default constructor, feel free to change the default hash map implementation to HashMapSC
  public HashCounter() {
    this(new HashMapDH<Key, Integer>());
  }
  
  /*
   * ADD MORE METHODS IF NEEDED
   * 
   */
  
  public int size() {
    return A.size();
  }
  
  public boolean isEmpty() {
    return A.isEmpty();
  }
  
  public int total() {
    return s;
  }
  
  /*
   * Increments the count of the key if it is already in the counter, 
   * creates a new item with count 1 if not
   * 
   * Do not forget to update s 
   * 
   */
  public void increment(Key key) {
    if(A.get(key)==null)
      A.put(key, 1);
    else
      A.put(key,A.get(key)+1);
  }
  
  /*
   * Returns the count of the key if it is in the counter, 
   * or returns 0 if not
   * 
   */
  public int getCount(Key key) {
    //TODO: Implement this

    return Objects.nonNull(A.get(key)) ? A.get(key) : 0;
  }
  
  /*
   * Convenience function that counts all the keys in the given array
   * 
   */
  public void countAll(Key[] keys) {
    for (Key k : keys) {
      increment(k);
    }
  }
  
  /*
   * Convenience function that counts all the keys in the given iterable (e.g. list)
   * 
   */
  public void countAll(Iterable<Key> keys) {
    for (Key k : keys) {
      increment(k);
    }
  }
  
  /*
   * Return an iterable collection (e.g. an ArrayList) of the unique keys in the counter
   * 
   */
  public Iterable<Key> keySet() {
    return A.keySet();
  }
  
  @Override
  public Object get(Key key) {
    return getCount(key);
  }

}
