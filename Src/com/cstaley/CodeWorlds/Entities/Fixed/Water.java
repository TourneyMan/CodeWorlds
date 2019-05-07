package com.cstaley.CodeWorlds.Entities.Fixed;

import com.cstaley.CodeWorlds.Entities.Body;
import com.cstaley.CodeWorlds.Entities.Brick;
import com.cstaley.CodeWorlds.Util.Vector;

import java.awt.Color;
import java.awt.Image;

public class Water extends Brick {
   private static int imgSize;
   private static Image img;

   private Vector loc;

   public Water(Vector loc) {this.loc = loc;}

   @Override
   public Vector getLoc()  {return loc;}
   
   @Override
   public String toString() {return "Water";}
   
   @Override
   public Body clone(Vector offset) {return new Water(loc.plus(offset));}

   @Override
   public Image getImage(int size) {
      if (size != imgSize)
         img = makeStippleImage(size, new Color(64, 64, 255),
          new Color(128, 128, 255), 13, 11);

      return img;
   }
}