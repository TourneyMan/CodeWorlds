package com.cstaley.CodeWorlds.Displays;

import com.cstaley.CodeWorlds.Steppable;
import com.cstaley.CodeWorlds.Entities.Body;
import com.cstaley.CodeWorlds.Entities.Brick;
import com.cstaley.CodeWorlds.Entities.Animals.Cow;
import com.cstaley.CodeWorlds.Entities.Animals.Sloth;
import com.cstaley.CodeWorlds.Entities.Fixed.Ore;
import com.cstaley.CodeWorlds.Entities.Fixed.Tree;
import com.cstaley.CodeWorlds.Util.CWSException;
import com.cstaley.CodeWorlds.Util.Vector;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class GraphicsFrame extends JFrame {
   GraphicsPanel pnl;
   public GraphicsFrame(int xDim, int yDim) throws CWSException {
      super("GridWorlds V2");
      Container cp = getContentPane();

      pnl = new GraphicsPanel(xDim, yDim);
      pnl.setBorder(new BevelBorder(BevelBorder.LOWERED));
      cp.setLayout(new FlowLayout());
      cp.add(pnl);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      pack();
      setVisible(true);
   }
   
   public GraphicsPanel getPnl() {
      return pnl;
   }

   private static class SampleDsp extends Brick implements Steppable {
      private static int imgSize;
      private static BufferedImage img;
      
      public SampleDsp(Vector loc, Vector vlc) {this.loc = loc; this.vlc = vlc;}

      private Vector loc;
      private Vector vlc;
      
      @Override
      public Vector getLoc() {return loc;}

      @Override
      public Image getImage(int size) {
         Graphics2D grp;
         
         if (imgSize != size) {
            img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            imgSize = size;
            grp = img.createGraphics();
            
            // Draw green cross
            grp.setColor(Color.GREEN.darker());
            grp.fillRect(3*size/8, 0, size/4, size);
            grp.fillRect(0, 3*size/8, size, size/4);
            
            grp.dispose();  // Don't forget to do this!
         }
         return img;
      }

      @Override
      public void step() {
         Vector oldLoc = loc;
         
         loc = loc.plus(vlc);
         setChanged();
         notifyObservers(oldLoc);
      }

      @Override
      public Body clone(Vector offset) {return null;}
   }
   
   public static void main(String[] args) throws CWSException {
      GraphicsFrame frame = new GraphicsFrame(100, 100);
      SampleDsp dsps[] = new SampleDsp[10];
      Scanner in = new Scanner(System.in);
      int i;

      frame.getPnl().addDisplayable(new Tree(new Vector(5, 8)));
      frame.getPnl().addDisplayable(new Ore(new Vector(6, 8)));
      frame.getPnl().addDisplayable(new Cow(new Vector(7, 8)));
      frame.getPnl().addDisplayable(new Sloth(new Vector(8, 8)));

      for (i = 0; i < 10; i++) {
         dsps[i] = new SampleDsp(new Vector(5 + i*10, 5 + i*10 + i%2),
          new Vector(i%2*2 - 1, 1 - i%2*2));
         frame.getPnl().addDisplayable(dsps[i]);
      }
      while (in.hasNextLine()) {
         in.nextLine();
         for (i = 0; i < dsps.length; i++)
            dsps[i].step();
      }
   }   
}
