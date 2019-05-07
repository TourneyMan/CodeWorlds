package com.cstaley.CodeWorlds;

import com.cstaley.CodeWorlds.Displays.Display;
import com.cstaley.CodeWorlds.Displays.DumpDisplay;
import com.cstaley.CodeWorlds.Displays.GraphicsFrame;
import com.cstaley.CodeWorlds.Entities.Body;
import com.cstaley.CodeWorlds.Entities.Brick;
import com.cstaley.CodeWorlds.Entities.Animals.Animal;
import com.cstaley.CodeWorlds.Factories.InputStreamWorldFactory;
import com.cstaley.CodeWorlds.Util.CWSException;
import com.cstaley.CodeWorlds.Util.Logger;
import com.cstaley.CodeWorlds.Util.Rectangle;
import edu.principia.BiomesWorldFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CodeWorlds {
   public static void main(String[] args) {
      Display dsp;
      Scanner in = new Scanner(System.in);
      Body world, body;
      int time = 0;
      Rectangle bounds;
      List<Body> steppables = new LinkedList<Body>();

      try {
         if (args.length < 1 || args.length > 2
          || !args[0].equals("D") && !args[0].equals("G")
           && !args[0].equals("S"))
            throw new CWSException("Usage: CodeWorlds (D|G) [entityFile]");
         
         BiomesWorldFactory biomeWorld = new BiomesWorldFactory();
         biomeWorld.build();
         world = biomeWorld.getWorld();
         
         bounds = world.getBounds();
         System.out.printf("Bounds %s\n", bounds);
         
         dsp = args[0].equals("G") ? new GraphicsFrame
          (bounds.getRight(), bounds.getBottom()).getPnl() : new DumpDisplay();
         
 
         Animal.setRange(world.getBounds());

         for (Iterator<Brick> itr = world.getBricks(); itr.hasNext();)
            dsp.addDisplayable(itr.next()); 
            
         for (Iterator<Body> itr = world.getBodies(); itr.hasNext();) {
            body = itr.next();
            if (body.isSteppable())
               steppables.add(body); 
         }
         
         System.out.print("Keep hitting return"); 
         while (in.hasNextLine()) {
            for (Body stp: steppables)
               stp.step();
            dsp.redraw(time++);
            in.nextLine();
         }
      }
      catch (CWSException err) {
         Logger.getLogger().log("Error: %s\n", err.getMessage());
      }
      finally {
         in.close();
      }
   }
}