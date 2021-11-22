package code;

import given.AbstractArraySort;

import java.lang.reflect.Array;

/*
 * Implement the merge-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 * 
 * You may need to create an Array of K (Hint: the auxiliary array). 
 * Look at the previous assignments on how we did this!
 * 
 */

public class MergeSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  //private final static int N = 100;
  // Add any fields here
  private K[] auxArray;

  public MergeSort() {
    name = "Mergesort";

    // Initialize anything else here

  }

  @Override
  public void sort(K[] inputArray) {
    // TODO: Implement the merge-sort algorithm
    auxArray = (K[])Array.newInstance(Comparable.class, inputArray.length);
    mergeSort(inputArray, 0, inputArray.length-1);
  }

  private void mergeSort(K[] input, int lo, int hi){
    if(lo<hi){
      int mid = (lo+hi)/2;

      mergeSort(input, lo, mid);
      mergeSort(input, mid+1, hi);

      merge(input, lo,mid,hi);

    }

  }

  // Public since we are going to check its output!
  public void merge(K[] inputArray, int lo, int mid, int hi) {
    // TODO: Implement the merging algorithm
    copyToAux(inputArray, lo, hi);
    int i = lo; //first partition index
    int j = mid+1; // second partition index
    int k = lo; // original array index

    while (k<=hi){
      if ((j>hi) || (i<=mid && compare(auxArray[i],auxArray[j])<0)){
        inputArray[k] = auxArray[i];
        k++; i++;
      }else{
       inputArray[k] = auxArray[j];
       k++; j++;
      }
    }
  }

  private void copyToAux(K[] inputArray, int lo, int hi) {
    for(int i = lo; i < hi+1; i++){
      auxArray[i] = inputArray[i];

    }

  }

  // Feel free to add more methods
}
