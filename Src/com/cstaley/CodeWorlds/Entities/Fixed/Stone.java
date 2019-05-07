package com.cstaley.CodeWorlds.Entities.Fixed;

import com.cstaley.CodeWorlds.Entities.Body;
import com.cstaley.CodeWorlds.Entities.Brick;
import com.cstaley.CodeWorlds.Util.Vector;

import java.awt.Color;
import java.awt.Image;

public class Stone extends Brick {
   private static int imgSize;
   private static Image img;

   private Vector loc;

   public Stone(Vector loc) {this.loc = loc;}

   @Override
   public Vector getLoc()  {return loc;}
   
   @Override
   public String toString() {return "Stone";}
   
   @Override
   public Body clone(Vector offset) {return new Stone(loc.plus(offset));}

   @Override
   public Image getImage(int size) {
      if (size != imgSize)
         img = makeStippleImage(size, new Color(128, 110, 90),
          new Color(64, 55, 45), 13, 11);

      return img;
   }
}