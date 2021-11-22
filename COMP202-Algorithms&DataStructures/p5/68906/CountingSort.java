package code;

import given.AbstractArraySort;

import java.util.Arrays;

/*
 * Implement the c algorithm here. You can look at the slides for the pseudo-codes.
 * You do not have to use swap or compare from the AbstractArraySort here
 * 
 * You may need to cast any K to Integer
 * 
 */

public class CountingSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  //Add any fields here
  
  public CountingSort()
  {
    name = "Countingsort";
  }
  
  @Override
  public void sort(K[] inputArray) {
    
    if(inputArray == null)
    {
      System.out.println("Null array");
      return;
    }
    if(inputArray.length < 1)
    {
      System.out.println("Empty array");
      return;
    }   
    
    if(!(inputArray[0] instanceof Integer)) {
      System.out.println("Can only sort integers!");
      return;
    }
    
    //TODO:: Implement the counting-sort algorithm here


    Integer[] inputArrayInteger  = (Integer[]) inputArray;
    int max = inputArrayInteger[0]; int min = inputArrayInteger[0];
    for(int i = 1; i <inputArrayInteger.length; i++){
      if(inputArrayInteger[i] < min)
        min = inputArrayInteger[i];
      else if (inputArrayInteger[i] > max)
        max = inputArrayInteger[i];
    }


    int k = max - min + 1;
    Integer[] counts = new Integer[k];
    Arrays.fill(counts, 0);
    for (Integer integer : inputArrayInteger) {
      counts[integer - min]++;
    }

    int c = 0;
    for(int i = 0; i<k; i++){
      if(counts[i] != null){
        for(int j=0; j<counts[i];j++){
          inputArray[c] = (K)(Integer)(i + min);
          c++;
        }
      }
    }


  }

}
