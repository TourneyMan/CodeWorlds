package com.cstaley.CodeWorlds.Factories;

import com.cstaley.CodeWorlds.Entities.Body;
import com.cstaley.CodeWorlds.Entities.CompositeBody;
import com.cstaley.CodeWorlds.Entities.Animals.Cow;
import com.cstaley.CodeWorlds.Entities.Animals.Horse;
import com.cstaley.CodeWorlds.Entities.Animals.Sloth;
import com.cstaley.CodeWorlds.Entities.Fixed.*;
import com.cstaley.CodeWorlds.Util.CWSException;
import com.cstaley.CodeWorlds.Util.Vector;

import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class InputStreamWorldFactory implements WorldFactory {
   private Map<String, Body> subWorlds = new TreeMap<String, Body>();
   private Body world;
   private Scanner in; 
   private static final Pattern startDef = Pattern.compile("\\(");
   private static final Pattern endDef = Pattern.compile("\\)");
   private static final Pattern namePtn = Pattern.compile("[a-zA-Z]\\w*");
      
   public InputStreamWorldFactory(InputStream str) {
      subWorlds.put("Cow",   new Cow(new Vector()));
      subWorlds.put("Horse", new Horse(new Vector()));
      subWorlds.put("Sloth", new Sloth(new Vector()));
      subWorlds.put("Tree",  new Tree(new Vector()));
      subWorlds.put("Stone", new Stone(new Vector()));
      subWorlds.put("Ore",   new Ore(new Vector()));
      subWorlds.put("Water", new Water(new Vector()));
      in = new Scanner(str);
   }

   @Override
   public Body getWorld() {return world;}

   @Override
   public WorldFactory build() throws CWSException {
      String name;

      // Read definitions first
      while (in.hasNext(startDef)) {
         in.next(startDef);
         if (!in.hasNext(namePtn))
            throw new CWSException("Bad pattern name " + in.next());
         name = in.next(namePtn);
         System.out.println("Reading " + name);
         subWorlds.put(name, readWorld(name));
         in.next(endDef);
      }
      
      world = readWorld("global");
      
      return this;
   }
         
   private Body readWorld(String parentName) throws CWSException {
      String name;
      CompositeBody rtn = new CompositeBody();
      Vector loc;
      
      try {
         while (in.hasNext(namePtn)) {
            name = in.next(namePtn);
            System.out.println("Using " + name);
            loc = new Vector(in.nextInt(), in.nextInt());
            if (subWorlds.containsKey(name)) {   // Existing pattern
               if (!rtn.addChild(subWorlds.get(name).clone(loc)))
                  throw new CWSException(String.format
                   ("Pattern %s overlaps earlier parts of %s", name, parentName));
            }
            else
               throw new CWSException("Unknown pattern " + name);
         }
      }
      catch (NoSuchElementException err) {
         throw new CWSException("File format error" + err.getMessage());
      }
      
      return rtn;
   }

   /**
    * @param args
    */
   public static void main(String[] args) {
      // TODO Auto-generated method stub

   }

}
