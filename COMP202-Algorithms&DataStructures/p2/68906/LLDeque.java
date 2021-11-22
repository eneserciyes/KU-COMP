package code;

/*
 * ASSIGNMENT 2
 * AUTHOR:  Mehmet Enes Erciyes
 * Class : code.LLDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the linked list yourself
 * Note that it should be a doubly linked list
 *
 * MODIFY
 *
 * */

import given.iDeque;
import java.util.Iterator;
import given.Util;

//If you have been following the class, it should be obvious by now how to implement a Deque wth a doubly linked list
public class LLDeque<E> implements iDeque<E> {

    //Use sentinel nodes. See slides if needed
    private Node<E> header;
    private Node<E> trailer;

    /*
     * ADD FIELDS IF NEEDED
     */
    private int size = 0;

    // The nested node class, provided for your convenience. Feel free to modify
    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;
        /*
         * ADD FIELDS IF NEEDED
         */

        Node(T d, Node<T> n, Node<T> p) {
            element = d;
            next = n;
            prev = p;
        }

        void delete(){
            next = null;
            prev = null;
        }

        @Override
        public String toString() {
            if(next == null || prev == null)
                return String.format("I AM ERROR -- VALUE: %d", element);
            else
                return "NO PROBLEM";
        }
        /*
         * ADD METHODS IF NEEDED
         */
    }

    public LLDeque() {
        //Remember how we initialized the sentinel nodes
        header  = new Node<E>(null, null, null);
        trailer = new Node<E>(null, null, null);
        header.prev = header;
        header.next = trailer;
        trailer.next = trailer;
        trailer.prev = header;

        /*
         * ADD CODE IF NEEDED
         */
    }

    public String toString() {
        if(isEmpty())
            return "";
        StringBuilder sb = new StringBuilder(1000);
        sb.append("[");
        Iterator<E> iter = this.iterator();
        E tmp = null;
        while(iter.hasNext()) {
            tmp = iter.next();
            sb.append(tmp.toString());
            if(iter.hasNext())
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /*
     * ADD METHODS IF NEEDED
     */

    /*
     * Below are the interface methods to be overriden
     */

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return header.next.equals(trailer);
    }

    @Override
    public void addFront(E o) {
//        System.out.printf("Added front : %d\n", o);
//        System.out.printf("Size: %d\n", size());
//        System.out.println(this);

        // TODO Auto-generated method stub
        Node<E> node = new Node<>(o,header.next,header);
        node.prev.next = node;
        node.next.prev = node;
        //System.out.println(node);
        size++;

    }

    @Override
    public E removeFront() {
//        System.out.println("Removed front");
//        System.out.printf("Size: %d\n", size());
//        System.out.println(this);
        // TODO Auto-generated method stub
        if(this.isEmpty()) {
            return null;
        }
        else {
            Node<E> nodeRemoved =  header.next;
            nodeRemoved.prev.next = nodeRemoved.next;
            nodeRemoved.next.prev = nodeRemoved.prev;
            E removedElement = nodeRemoved.element;
            //System.out.println(nodeRemoved);
            nodeRemoved.delete();
            size--;

            return removedElement;
        }
    }

    @Override
    public E front() {
        // TODO Auto-generated method stub
        if(isEmpty())
            return null;
        else
            return header.next.element;
    }

    @Override
    public void addBehind(E o) {
//        System.out.printf("Added behind : %d\n",o);
//        System.out.printf("Size: %d\n", size());
//        System.out.println(this);

        // TODO Auto-generated method stub
        Node<E> nodeAdded = new Node<>(o,trailer,trailer.prev);
        nodeAdded.prev.next = nodeAdded;
        nodeAdded.next.prev = nodeAdded;

        size++;

        //System.out.println(nodeAdded);
    }

    @Override
    public E removeBehind() {
//        System.out.println("Removed behind:");
//        System.out.printf("Size: %d\n", size());
//        System.out.println(this);

        // TODO Auto-generated method stub
        if(isEmpty())
            return null;
        else{
            Node<E> nodeRemoved = trailer.prev;
            E elementRemoved = nodeRemoved.element;
            nodeRemoved.next.prev = nodeRemoved.prev;
            nodeRemoved.prev.next = nodeRemoved.next;
            //System.out.println(nodeRemoved);
            //nodeRemoved.delete();
            size--;

            return elementRemoved;
        }
    }

    @Override
    public E behind() {
        // TODO Auto-generated method stub
        if(isEmpty())
            return null;
        else
            return trailer.prev.element;
    }

    @Override
    public void clear() {
//        System.out.println("Cleared");
//        System.out.printf("Size: %d\n", size());
        // TODO Auto-generated method stub
        header.next = trailer;
        trailer.prev = header;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        //Hint: Fill in the LLDequeIterator given below and return a new instance of it
        return new LLDequeIterator();
    }

    private final class LLDequeIterator implements Iterator<E> {

        /*
         *
         * ADD A CONSTRUCTOR IF NEEDED
         * Note that you can freely access everything about the outer class!
         *
         */
        Node<E> current;

        LLDequeIterator() {
            current = header;
        }

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            if(isEmpty())
                return false;
            return !(current.next.equals(trailer));
        }

        @Override
        public E next() {
            // TODO Auto-generated method stub
            current = current.next;
            return current.element;
        }
    }

}
