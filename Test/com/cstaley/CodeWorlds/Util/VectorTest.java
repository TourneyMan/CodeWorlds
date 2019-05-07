package com.cstaley.CodeWorlds.Util;

import org.junit.Assert;
import org.junit.Test;

import com.cstaley.CodeWorlds.Entities.Animals.Cow;

/**
 * Created by Lee on 1/20/2017.
 */

/**
 * Check to make sure the math for vectors are being generated correctly.
 */
public class VectorTest {
	   @Test
	   public void BasicTest() {
	      Vector v = new Vector(-3,5);
	      Vector v2 = new Vector(15,-3);
	      Vector v3 = new Vector(-6,0);

	      Assert.assertTrue(v.getX() == -3);
	      Assert.assertTrue(v.getY() == 5);
	      Assert.assertTrue(v.toString().equals("[-3, 5]"));
	      Assert.assertTrue(v.equals(new Vector(-3,5)));
	      Assert.assertFalse(v.equals(new Vector(3,5)));
	      
	      Assert.assertTrue(v.plus(v2).equals(v2.plus(v)));
	      Assert.assertFalse(v.minus(v2).equals(v2.minus(v)));
	      Assert.assertTrue(v.scale(2.0).scale(.5).equals(v));
	      
	      Assert.assertTrue(v.turn90(true).turn90(false).equals(v));
	      Assert.assertEquals(v.turn90(true).turn90(false).toString(), v.turn90(false).turn90(true).toString());
	      Assert.assertEquals(v.turn90(true).turn90(true).turn90(true).turn90(true).toString(), v.toString());
	      Assert.assertEquals(v.turn90(false).turn90(false).turn90(false).turn90(false).toString(), v.toString());
	      Assert.assertEquals(v.turn90(false).turn90(false).turn90(false).turn90(false).toString(), v.toString());
	      Assert.assertEquals((new Vector(3,6)).turn90(true).toString(),(new Vector(-6,3)).toString());
	      Assert.assertEquals((new Vector(1,0)).turn90(true).toString(),(new Vector(0,1)).toString());
	     
	      Assert.assertEquals(v.turn90(false).turn90(false).turn90(false).turn90(false).toString(), v.toString());
	      Assert.assertTrue(v.length() * 3.5 - v.scale(3.5).length() < Vector.kEpsilon);

	      
	      Assert.assertEquals(v.minus(v).minus(v), v.scaleBy(-1));
	   }
	   
	   @Test
	   public void PlusMinusByTest() {
		 Vector v = new Vector(4,-7);
		 Vector v2 = new Vector(-4, -6);
		 Assert.assertEquals(v.toString(), v.plusBy(v2).minusBy(v2).toString());
		 Assert.assertEquals(v.plus(v).plus(v), v.plusBy(v).plusBy(v)); //Probably not a bug, but I thought I would include it just in case
	   }
	   
	   @Test
	   public void PlusByTest2() {
		 Vector v = new Vector(1,1);
		 Vector v2 = new Vector(1,1);
		 v.scaleBy(2).scaleBy(.5).scaleBy(4).scaleBy(.25);
		 Assert.assertEquals(v.toString(), (new Vector(1,1)).toString());
		 
		 Assert.assertEquals(v.plusBy(v2).plusBy(v2).plusBy(v2).toString(), (new Vector(4,4).toString()));
		 Assert.assertTrue((new Vector(2000000000,0)).scale(2).getX() > 2000000000); //minor bug
	   }
	   
	   @Test
	   public void MinusByTest2() {
		 Assert.assertTrue((new Vector(-2000000000,0)).minus((new Vector(-2000000000,0))).getX() < -2000000000); //minor bug
	   }
	   
	   
	   @Test
	   public void ScaleByTest() {
		   Vector v = new Vector(-3,4);
		   double length = v.length();
		   v.scaleBy(2);
		   Assert.assertTrue((length * 2) - v.length() < Vector.kEpsilon);
		   
		   Vector v2 = new Vector(1,1);
		   Assert.assertEquals(v2.scale(1.5).toString(), v2.scaleBy(1.5).toString()); //Bug, these should return the same
	   }
	   
	   @Test
	   public void Turn90ByTest() {
		 Vector v = new Vector(6,3);
		 Assert.assertEquals((v.turn90(true)).toString(),v.turn90By(true).toString()); //BUG with TurnBy
		 
		 Vector v2 = new Vector(6,3);
		 Assert.assertEquals((v.turn90By(true)).toString(), (new Vector(-3, 6)).toString());
	   }
}
