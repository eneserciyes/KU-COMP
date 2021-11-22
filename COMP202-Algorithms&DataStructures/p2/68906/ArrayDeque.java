package code;

/*
 * ASSIGNMENT 2
 * AUTHOR:  Mehmet Enes Erciyes
 * Class : code.ArrayDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the Array Deque yourself
 *
 * MODIFY
 *
 * */

import given.iDeque;
import java.util.Arrays;
import java.util.Iterator;

import given.Util;


/*
 * You must have a circular implementation.
 *
 * WARNING: Modulo operator (%) might create issues with negative numbers.
 * Use Math.floorMod instead if you are having issues
 */
public class ArrayDeque<E> implements iDeque<E> {

    private E[] A; //Do not change this name!
    private int size = 0;
    private int front = -1;
    private int rear = -1;
    /*
     * ADD FIELDS IF NEEDED
     */

    public ArrayDeque() {
        this(1000);
        /*
         * ADD CODE IF NEEDED
         */
    }

    public ArrayDeque(int initialCapacity) {
        if(initialCapacity < 1)
            throw new IllegalArgumentException();
        A = createNewArrayWithSize(initialCapacity);
        /*
         * ADD CODE IF NEEDED
         */
    }

    // This is given to you for your convenience since creating arrays of generics is not straightforward in Java
    @SuppressWarnings({"unchecked" })
    private E[] createNewArrayWithSize(int size) {
        return (E[]) new Object[size];
    }

    //Modify this such that the dequeue prints from front to back!
    //Hint, after you implement the iterator, use that!
    public String toString() {

        /*
         * MODIFY THE BELOW CODE
         */

        StringBuilder sb = new StringBuilder(1000);

        Iterator<E> iter = this.iterator();
        if(isEmpty()){
            return "";
        }

        sb.append("[");
        while(iter.hasNext()) {

            E e = iter.next();
            if(e == null)
                continue;
            sb.append(e);
            if(!iter.hasNext())
                sb.append("]");
            else
                sb.append(", ");
        }
//        sb.append("\nFront: ");
//        sb.append(front);
//        sb.append("\nRear: ");
//        sb.append(rear);
        return sb.toString();
    }

    /*
     * ADD METHODS IF NEEDED
     */



    /*
     * Below are the interface methods to be overriden
     */

    private void doubleSize(){
        E[] N = createNewArrayWithSize(A.length * 2);
        for (int i  = front; i <= front + size; i++){
            N[i-front] = A[Math.floorMod(i,A.length)];
        }
        this.A  = N;
        front = 0;
        rear = size-1;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return size;
    }

    private boolean isFull(){
        return this.size() >= A.length;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return size == 0;
    }

    @Override
    public void addFront(E o) {
//        System.out.printf("Added front : %d\n", o);
//        System.out.printf("Size: %d\n", size());
//        System.out.printf("Front: %d, Rear: %d\n",front,rear);
//        System.out.println(this);

        // TODO Auto-generated method stub
        if(this.isFull()){
            doubleSize();
        }
        if(front==-1) {
            front = 0;
            rear = 0;
        }
        else
            front = Math.floorMod(front - 1,A.length);

        A[front] = o;
        size++;
    }


    @Override
    public E removeFront() {
//        System.out.println("Removed front");
//        System.out.printf("Size: %d\n", size());
//        System.out.printf("Front: %d, Rear: %d\n",front,rear);
//        System.out.println(this);

        // TODO Auto-generated method stub
        if(this.isEmpty())
            return null;
        else{
            E temp = A[front];
            A[front] = null;
            size--;

            if(!isEmpty())
                front = Math.floorMod(front + 1,A.length);
            else{
                front = -1;
                rear = -1;
            }

            return temp;

        }

    }

    @Override
    public E front() {
        // TODO Auto-generated method stub
        if(isEmpty())
            return null;
        else
            return A[front];
    }

    @Override
    public void addBehind(E o) {
//        System.out.printf("Added behind : %d\n",o);
//        System.out.printf("Size: %d\n", size());
//        System.out.printf("Front: %d, Rear: %d\n",front,rear);
//        System.out.println(this);

        // TODO Auto-generated method stub
        if(this.isFull())
            doubleSize();

        if(front==-1){
            front = 0;
            rear = 0;
        }else
            rear = Math.floorMod(rear + 1,A.length);
        A[rear] = o;
        size++;

    }

    @Override
    public E removeBehind() {
//        System.out.println("Removed behind:");
//        System.out.printf("Size: %d\n", size());
//        System.out.printf("Front: %d, Rear: %d\n",front,rear);
//        System.out.println(this);

        // TODO Auto-generated method stub
        if(this.isEmpty())
            return null;
        else{

            E temp = A[rear];
            A[rear] = null;
            size--;

            if(!isEmpty())
                rear = Math.floorMod(rear - 1,A.length);
            else{
                front = -1;
                rear = 0;
            }

            return temp;

        }
    }

    @Override
    public E behind() {
        // TODO Auto-generated method stub
        if(isEmpty())
            return null;
        else
            return A[rear];
    }

    @Override
    public void clear() {
        //System.out.println("Cleared");

        // TODO Auto-generated method stub
        for(int i = front; i < front + size; i++){
            int index = Math.floorMod(i, A.length);
            A[index] = null;
        }
        size = 0;
        front = -1;
        rear = -1;
    }

    //Must print from front to back
    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        //Hint: Fill in the ArrayDequeIterator given below and return a new instance of it
        return new ArrayDequeIterator();
    }

    private final class ArrayDequeIterator implements Iterator<E> {

        /*
         *
         * ADD A CONSTRUCTOR IF NEEDED
         * Note that you can freely access everything about the outer class!
         *
         */
        int iterStart;
        int iterEnd;
        int count;

        ArrayDequeIterator(){
            iterStart = front;
            iterEnd = rear;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return count < size;
        }

        @Override
        public E next() {
            // TODO Auto-generated method stub
            E current = A[iterStart];
            iterStart = Math.floorMod(iterStart+1,A.length);
            count++;
            return current;
        }
    }
}
