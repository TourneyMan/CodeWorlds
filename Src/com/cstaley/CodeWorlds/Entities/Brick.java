package com.cstaley.CodeWorlds.Entities;

import com.cstaley.CodeWorlds.Util.Rectangle;
import com.cstaley.CodeWorlds.Util.SingleIterator;
import com.cstaley.CodeWorlds.Util.Vector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Observable;

// Fundamental unit of display.  A Brick is one cell in the display.
public abstract class Brick extends Observable implements Body {
   
   // Create an image of color |bg|, with |fg| dots at random location, with
   // one |fg| dot out of every |skip| dots.
   static public Image makeStippleImage(int size, Color bg, Color fg,
    int skip, int subSkip) {
      Graphics2D grp;
      int dotSize = Math.max(1, size/16);
      int cols = size/dotSize, numDots = cols * size/dotSize;
      BufferedImage img;

      img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
      grp = img.createGraphics();

      grp.setColor(bg);
      grp.fillRect(0, 0, size, size);
      grp.setColor(fg);
      for (int dot = 0; dot < numDots; dot ++)
         if (dot % skip == 0 || dot % subSkip == 0)
            grp.fillRect(dot % cols * dotSize, dot/cols * dotSize, dotSize,
             dotSize);
  
      grp.dispose();
      
      return img;      
   }

   abstract public Vector getLoc();
   abstract public Image getImage(int size);

   @Override
   public Iterator<Brick> getBricks() {return new SingleIterator<Brick>(this);}

   @Override
   public Iterator<Body> getBodies() {return new SingleIterator<Body>(this);}

   @Override
   public Rectangle getBounds() {return new Rectangle(getLoc(), 1, 1);}

   @Override
   public boolean isSteppable() {return false;}
   
   @Override
   public void step() {}
}
