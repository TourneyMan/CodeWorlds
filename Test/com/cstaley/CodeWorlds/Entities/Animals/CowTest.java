package com.cstaley.CodeWorlds.Entities.Animals;

import com.cstaley.CodeWorlds.Util.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Lee on 1/20/2017.
 */

/**
 * Make sure that horses move when you step them.
 */
public class CowTest {
   @Test
   public void LabelTest() {
      Cow c = new Cow(new Vector(0,0));

      Assert.assertTrue(c.getLabel().equals("Cow"));
         
   }
}
