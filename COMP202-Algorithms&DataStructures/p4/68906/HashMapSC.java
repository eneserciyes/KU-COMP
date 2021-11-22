package code;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import given.AbstractHashMap;
import given.HashEntry;

/*
 * The file should contain the implementation of a hashmap with:
 * - Separate Chaining for collision handling
 * - Multiply-Add-Divide (MAD) for compression: (a*k+b) mod p
 * - Java's own linked lists for the secondary containers
 * - Resizing (to double its size) and rehashing when the load factor gets above a threshold
 *   Note that for this type of hashmap, load factor can be higher than 1
 *
 * Some helper functions are provided to you. We suggest that you go over them.
 *
 * You are not allowed to use any existing java data structures other than for the buckets (which have been
 * created for you) and the keyset method
 */

public class HashMapSC<Key, Value> extends AbstractHashMap<Key, Value> {

    // The underlying array to hold hash entry Lists
    private LinkedList<HashEntry<Key, Value>>[] buckets;

    // Note that the Linkedlists are still not initialized!
    @SuppressWarnings("unchecked")
    protected void resizeBuckets(int newSize) {
        // Update the capacity
        N = nextPrime(newSize);
        buckets = (LinkedList<HashEntry<Key, Value>>[]) Array.newInstance(LinkedList.class, N);
    }

    /*
     * ADD MORE FIELDS IF NEEDED
     *
     */

    // The threshold of the load factor for resizing
    protected float criticalLoadFactor;

    /*
     * ADD A NESTED CLASS IF NEEDED
     *
     */

    public int hashValue(Key key, int iter) {
        return hashValue(key);
    }

    public int hashValue(Key key) {
        // the same as the primaryHash method of HashMapDH
        int hashCode = Math.abs(key.hashCode());
        return (Math.abs(a*hashCode + b) % P) % N;
    }

    // Default constructor
    public HashMapSC() {
        this(101);
    }

    public HashMapSC(int initSize) {
        // High criticalAlpha for representing average items in a secondary container
        this(initSize, 10f);
    }

    public HashMapSC(int initSize, float criticalAlpha) {
        N = initSize;
        criticalLoadFactor = criticalAlpha;
        resizeBuckets(N);

        // Set up the MAD compression and secondary hash parameters
        updateHashParams();

        /*
         * ADD MORE CODE IF NEEDED
         *
         */
    }

    /*
     * ADD MORE METHODS IF NEEDED
     *
     */

    @Override
    public Value get(Key k) {
        if(k == null)
            return null;

        if(buckets[hashValue(k)]==null){
            return null;
        }else{
            for(HashEntry<Key,Value> entry : buckets[hashValue(k)]){
                if(entry.getKey().equals(k)){
                    return entry.getValue();
                }
            }
            return null;
        }
    }

    @Override
    public Value put(Key k, Value v) {
        if(k == null)
            return null;
        // Do not forget to resize if needed!
        // Note that the linked lists are not initialized!
        checkAndResize();

        int i = 0;

        if(buckets[hashValue(k)] == null){
            buckets[hashValue(k)] = new LinkedList<>();
            buckets[hashValue(k)].add(new HashEntry<>(k,v));
            n++;
            return null;
        }else{
            for(HashEntry<Key, Value> entry: buckets[hashValue(k)]){
                if(entry.getKey().equals(k)){
                    Value prevValue = entry.getValue();
                    entry.setValue(v);
                    return prevValue;
                }
            }
            buckets[hashValue(k)].add(new HashEntry<>(k,v));
            n++;
            return null;
        }
    }

    @Override
    public Value remove(Key k) {
        if(k == null)
            return null;

        if(buckets[hashValue(k)] == null){
            return null;
        }else{
            for(HashEntry<Key, Value> entry: buckets[hashValue(k)]){
                if(entry.getKey().equals(k)){
                    buckets[hashValue(k)].remove(entry);
                    n--;
                    return entry.getValue();
                }
            }
            return null;
        }
    }

    @Override
    public Iterable<Key> keySet() {
        // TODO Auto-generated method stub
        return Arrays.stream(buckets)
                .filter(Objects::nonNull)
                .flatMap(LinkedList::stream)
                .map(HashEntry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * checkAndResize checks whether the current load factor is greater than the
     * specified critical load factor. If it is, the table size should be increased
     * to 2*N and recreate the hash table for the keys (rehashing). Do not forget to
     * re-calculate the hash parameters and do not forget to re-populate the new
     * array!
     */
    protected void checkAndResize() {
        if (loadFactor() > criticalLoadFactor) {
            // TODO: Fill this yourself
            LinkedList<HashEntry<Key, Value>>[] tempBucket = buckets;

            resizeBuckets(2*N);
            updateHashParams();
            int prev_n = n;

            for(LinkedList<HashEntry<Key, Value>> listEntry: tempBucket){
                for (HashEntry<Key, Value> entry : listEntry){
                    this.put(entry.getKey(),entry.getValue());
                }
            }

            n = prev_n;
        }
    }

}
