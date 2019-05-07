package com.cstaley.CodeWorlds.Util;
import java.util.Iterator;

public class SingleIterator<T> implements Iterator<T> {
   private T item;
   private boolean done;
   
   public SingleIterator(T t) {item = t;}

   @Override
   public boolean hasNext() {return !done;}

   @Override
   public T next() {done = true; return item;}

   @Override
   public void remove() {throw new UnsupportedOperationException();}
}
