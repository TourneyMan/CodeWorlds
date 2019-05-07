package com.cstaley.CodeWorlds.Entities;

import com.cstaley.CodeWorlds.Util.Rectangle;
import com.cstaley.CodeWorlds.Util.Vector;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CompositeBody implements Body {
   private class BrickItr implements Iterator<Brick> {
      Iterator<Body> myItr;     // Top-level iterator across each child
      Iterator<Brick> childItr; // Iterator within current child.
      
      // Start iterators, moving them to first valid data, if any.
      public BrickItr() {myItr = children.iterator(); advance();}

      // Advance iterators to the next spot where there is data, if any.  Move
      // childItr to start of next child when current child is exhausted, or
      // if we are just starting up (childItr == null).
      private void advance() {
         while (myItr.hasNext() && (childItr == null || !childItr.hasNext()))
            childItr = myItr.next().getBricks();
      }
      
      // Return true if most recent |advance| found valid data.
      @Override
      public boolean hasNext() {
         return childItr != null && childItr.hasNext();
      }

      @Override
      // Return currently valid data, and advance preemptively to the next.
      public Brick next() {
         Brick toRtn = childItr.next();
         
         advance();
         return toRtn;
      }

      @Override
      public void remove() {childItr.remove();}
   }
   
   private class StpItr implements Iterator<Body> {
      Iterator<Body> myItr;
      Iterator<Body> childItr;
      
      public StpItr() {myItr = children.iterator();}

      // Move childItr to the first spot where there is data, if any.  Otherwise
      // leave childItr at the "end", with hasNext() returning false.
      private void advance() {
         while (myItr.hasNext() && (childItr == null || !childItr.hasNext()))
            childItr = myItr.next().getBodies();
      }
      
      @Override
      public boolean hasNext() {
         return childItr != null && childItr.hasNext();
      }

      @Override
      public Body next() {
         Body rtn = childItr.next();
         
         advance();
         return rtn;
      }

      @Override
      public void remove() {childItr.remove();}
   }

   private List<Body> children = new LinkedList<Body>();
   private Rectangle bounds = null;
   private boolean childrenSteppable;

   @Override
   public Iterator<Brick> getBricks() {return new BrickItr();}

   @Override
   public Iterator<Body> getBodies() {return new StpItr();}

   @Override
   public Rectangle getBounds() {
      //if (childrenSteppable)
         computeBounds();
      
      return bounds;
   }
   
   
   @Override
   public boolean isSteppable() {return false;}

   @Override
   public void step() {}

   @Override
   public Body clone(Vector offset) {
      CompositeBody rtn = new CompositeBody();
      
      for (Body child: children)
         rtn.addChild(child.clone(offset));
      
      return rtn;
   }

   private void computeBounds() {
      bounds = null;
      
      for (Body child: children)
         if (bounds == null)
            bounds = new Rectangle(child.getBounds());
         else
            bounds.unionBy(child.getBounds());
   }

   public boolean addChild(Body other) {
      Iterator<Brick> myBricks, hisBricks = other.getBricks();
      Brick toCheck, existing;
      
      while (hisBricks.hasNext()) {
         toCheck = hisBricks.next();
         childrenSteppable = childrenSteppable || toCheck.isSteppable();
         for (myBricks = getBricks(); myBricks.hasNext();) {
            existing = myBricks.next();
            if (existing.getClass() != toCheck.getClass()
             && existing.getLoc().equals(toCheck.getLoc()))
               return false;
         }
      }
      children.add(other);
      if (bounds == null)
         bounds = new Rectangle(other.getBounds());
      else
         bounds.unionBy(other.getBounds());
         
      return true;
   }
}