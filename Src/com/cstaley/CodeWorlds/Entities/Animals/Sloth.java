package com.cstaley.CodeWorlds.Entities.Animals;

import com.cstaley.CodeWorlds.Entities.Body;
import com.cstaley.CodeWorlds.Util.Rectangle;
import com.cstaley.CodeWorlds.Util.Vector;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Sloth extends Animal {
   private static int imgSize;
   private static BufferedImage img;

   public Sloth(Vector loc) {super(loc, new Vector(0,0));}

   @Override
   public String getLabel() {return "Sloth";}
   
   @Override
   public void step() {
      step();
      
      vlc.scaleBy(.5);  // Sloths just move more and more slowly
   }
   
   @Override
   public Image getImage(int size) {
      Graphics2D grp;
      
      if (size != imgSize) {
         img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
         grp = img.createGraphics();

         grp.setColor(Color.DARK_GRAY);
         grp.fillOval(3*size/10, 3*size/10, size/5, size/5);
         
         grp.dispose();
      }
      
      return img;
   }
   
   @Override
   public Body clone(Vector offset) {return new Sloth(getLoc().plus(offset));}

   // Test main
   public static void main(String[] args) {
      Animal anm = new Sloth(new Vector(2, 3));

      Animal.setRange(new Rectangle(0, 10, 0, 10));
      
      for (int i = 0; i < 5; i++) {
         System.out.printf("%s is at %s moving %s\n", anm.toString(),
          anm.getLoc(), anm.getVlc());
         anm.step();
      }
   }
}