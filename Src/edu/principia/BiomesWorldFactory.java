/**************************************************************/
// Class: BiomesWorldFactory
// Author: Jacob Huss
// Class: CSCI 310
// Professor: Clint Staley
// Description: Used to generate a world that has a river running
// from the left side to the right, dividing the world into 2 biomes.
// One biome, is a forest, that has a lot of trees, but contains
// no trees surrounded on all sides by other trees.  The other
// is a rock field, which contains scattered patches of stones
// 76 Lines
/**************************************************************/

package edu.principia;

import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

import com.cstaley.CodeWorlds.Entities.Body;
import com.cstaley.CodeWorlds.Entities.CompositeBody;
import com.cstaley.CodeWorlds.Entities.Animals.Cow;
import com.cstaley.CodeWorlds.Entities.Animals.Horse;
import com.cstaley.CodeWorlds.Entities.Animals.Sloth;
import com.cstaley.CodeWorlds.Entities.Fixed.Ore;
import com.cstaley.CodeWorlds.Entities.Fixed.Stone;
import com.cstaley.CodeWorlds.Entities.Fixed.Tree;
import com.cstaley.CodeWorlds.Entities.Fixed.Water;
import com.cstaley.CodeWorlds.Factories.WorldFactory;
import com.cstaley.CodeWorlds.Util.CWSException;
import com.cstaley.CodeWorlds.Util.Vector;

public class BiomesWorldFactory implements WorldFactory {
   
   /**************************************************************/
   // Interface: EvaluatesALoc
   // Description: Simple Interface that requires all implementers
   // to have a method that does something at a given location
   // 1 Line
   /**************************************************************/
   interface EvaluatesALoc { public boolean evaluateLoc(int col, int row, int successCount); }

   /**************************************************************/
   // Inner class: AddATree
   // Description: Simple implementation of EvaluatesALoc
   // that randomly decides whether or not a tree should be put at
   // that location
   // 6 Lines
   /**************************************************************/
   class AddATree implements EvaluatesALoc {
      @Override
      public boolean evaluateLoc(int col, int row, int successCount) {
         if (rand.nextInt(4) != 3) {
            worldArray[col][row] = 2;
         }
         return false;
      }
   } // AddATree

   /**************************************************************/
   // Inner class: RemoveCrowdedTree
   // Description: Implementation of EvaluatesALoc
   // that removes a tree from the location if the 4 locations
   // adjacent to this location either don't exist or have a tree
   // 8 Lines
   /**************************************************************/
   class RemoveCrowdedTree implements EvaluatesALoc {
      @Override
      public boolean evaluateLoc(int col, int row, int successCount) {
         
         //If there are trees on all possible adjacent sides, remove the tree. It doesn't have enough sun.
         if ((col == 0 || worldArray[col - 1][row] == 2) && (row == 0 || worldArray[col][row - 1] == 2) &&
          (col == worldArray.length - 1 || worldArray[col + 1][row] == 2) &&
          (row == worldArray[0].length - 1 || worldArray[col][row + 1] == 2)) {
            worldArray[col][row] = 0;
         }
         return false;
      }
   }

   /**************************************************************/
   // Inner class: AddAStone
   // Description: Implementation of EvaluatesALoc
   // that decides if a stone should be added in that location
   // The more stones are already in the biome, the lower the odds
   // that a stone will be placed are. However, the more stones have
   // already been placed adjacent to this stone, the higher these odds.
   // 14 Lines
   /**************************************************************/
   class AddAStone implements EvaluatesALoc {
      @Override
      public boolean evaluateLoc(int col, int row, int successCount) {
         int numAdjStones = 0;
         
         if (col != 0 && worldArray[col - 1][row] == 3) {
            numAdjStones++;
         }
         
         if (row != 0 && worldArray[col][row - 1] == 3) {
            numAdjStones++;
         }
         
         if (row != worldArray[0].length - 1 && worldArray[col][row + 1] == 3) {
            numAdjStones++;
         }
         
         if (rand.nextInt(100) < Math.floor((.04 + (numAdjStones*.48))*
          (1 - (successCount*.25 / scanBiome(false, new CountLocs())))*100)) {
            worldArray[col][row] = 3;
         }
         
         return worldArray[col][row] == 3 ? true : false;
      }
   }

   /**************************************************************/
   // Inner class: CountLocs
   // Description:  Simple Implementation of EvaluatesALoc
   // that always returns true. Used to count locs in a biome
   // 3 Lines
   /**************************************************************/
   class CountLocs implements EvaluatesALoc {
      @Override
      public boolean evaluateLoc(int col, int row, int successCount) { return true; }
   }
   
   private Body world;
   private CompositeBody cBody = new CompositeBody();
   private Random rand = new Random();
   private int[][] worldArray = new int[rand.nextInt(75) + 75][rand.nextInt(75) + 75];

   /**************************************************************/
   // Inner class: getWorld
   // Description: Returns world
   // 1 Line
   /**************************************************************/
   @Override
   public Body getWorld() { return world; }

   /**************************************************************/
   // Inner class: build
   // Description: builds the biome world
   // 10 Lines
   /**************************************************************/
   @Override
   public WorldFactory build() throws CWSException {
      boolean isForestTop = rand.nextInt(2) == 0; // Decide whether the forest or rock field goes on top
      
      addRiver(); //Make the river
      scanBiome(isForestTop, new AddATree()); //Initial Forest
      scanBiome(isForestTop, new RemoveCrowdedTree()); //Thinning the forest
      scanBiome(!isForestTop, new AddAStone()); //Make the stone field

      populateCBody(); //Translate our array to a CompositeBody
      world = cBody;
      return null;
   }

   /**************************************************************/
   // Inner class: addRiver
   // Description: builds a river through the world, cutting from
   // the left side of the world to the right. Randomly moves up
   // and down as it goes.
   // 12 Lines
   /**************************************************************/
   private void addRiver() {
      int curX = 0, curY = rand.nextInt(worldArray[0].length), curYDirection = 0;
      
      //Initial river point
      worldArray[curX][curY] = 1;
      
      //Until we have reached the right side
      while (curX != worldArray.length - 1) {
         
         
         //No danger of going out of bounds, randomy decide if y should go inc, dec, or stay still
         if (curY !=  worldArray[0].length - 1 && curY != 0) {
            curYDirection = rand.nextInt(3) - 1;
         }
         
         //We are in danger of going out of bounds, carefully choose next y direction
         else {
            curYDirection = (curY == 0 ? rand.nextInt(2) : rand.nextInt(2) - 1);
         }
         
         //Move y's direction accordingly
         curY += curYDirection;
            
         //If y didn't move, move x
         if (curYDirection == 0) {
            curX++;
         }
         
         //Place our new water location
         worldArray[curX][curY] = 1;
      }
   }

   /**************************************************************/
   // Inner class: scanBiome
   // Description: Scans through each location of a biome and
   // performs the given EvaluatesALoc class' evaluateLoc function
   // at each location in the biome.  Returns the total number of these
   // function calls were considered a 'success'
   // 7 Lines
   /**************************************************************/
   private int scanBiome(boolean top, EvaluatesALoc doer) {
      
      //Counts 'successes' as defined by the doer's doSomething function
      int successCount = 0;
      
      //Traverses through the whole biome region
      for (int col = 0; col < worldArray.length; col++) {
         //Searches each column until water is found
         for (int row = (top ? 0 : worldArray[0].length - 1); worldArray[col][row] != 1;
          row = (top ? row + 1 : row - 1)) {
            //Does whatever is needed at each loc
            if (doer.evaluateLoc(col, row, successCount)) {
               successCount++;
            }
         }
      }
      return successCount;
   }

   /**************************************************************/
   // Inner class: populateCBody
   // Description: Populates the BiomesWorldFactory's CompositeBody
   // according to the values given for each location in the
   // two-dimensional array.
   // 9 Lines
   /**************************************************************/
   private void populateCBody() {
      //Traverse each location
      for (int col = 0; col < worldArray.length; col++) {
         for (int row = 0; row < worldArray[col].length; row++) {
            
            //Decide what brick should go there
            if (worldArray[col][row] == 1) {
               cBody.addChild(new Water(new Vector(col, row)));
            }
            
            else if (worldArray[col][row] == 2) {
               cBody.addChild(new Tree(new Vector(col, row)));
            }
            
            else if (worldArray[col][row] == 3) {
               cBody.addChild(new Stone(new Vector(col, row)));
            }
         }
      }
   }
}