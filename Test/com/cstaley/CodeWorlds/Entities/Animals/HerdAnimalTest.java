package com.cstaley.CodeWorlds.Entities.Animals;

import com.cstaley.CodeWorlds.Util.Vector;

import java.util.ArrayList;

import org.junit.*;

public class HerdAnimalTest {
	private HerdAnimal a;
	private HerdAnimal a2;
	private HerdAnimal a3;
	private HerdAnimal a4;
	private HerdAnimal a5;
	private HerdAnimal a6;
	private HerdAnimal a7;
	private HerdAnimal a8;
	
	@Before
	public void SetAnimals() {
		a = new Cow(new Vector(5,3));
		a2 = new Cow(new Vector(4,2));
		a3 = new Cow(new Vector(3,7));
		a4 = new Horse(new Vector(5,3));
		a5 = new Horse(new Vector(4,2));
		a6 = new Horse(new Vector(6,1));
		a7 = new Horse(new Vector(8,2));
		a8 = new Cow(new Vector(8,2));
	}
	
	@After
	public void ClearAnimals() {
		HerdAnimal.setAll(new ArrayList());
	}
	
   @Test
   public void BasicMoveCowNoLeader() {
	  a.vlc = new Vector(1,-1);
      a.step();
      Assert.assertEquals(a.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a.toString(), "Cow");
      Assert.assertEquals(a.getMyFollowers().size(), 0);
      Assert.assertNull(a.getMyLeader());
   }
   
   @Test
   public void BasicMoveHorseNoLeader() {
	  a4.vlc = new Vector(1,-1);
      a4.step();
      Assert.assertEquals(a4.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a4.toString(), "Horse");
      Assert.assertEquals(a4.getMyFollowers().size(), 0);
      Assert.assertNull(a4.getMyLeader());
   }
   
   @Test
   public void CheckDoesMakeHorseLeader() {
	  a4.vlc = new Vector(1,-1);
	  a5.vlc = new Vector(2,0);
      a5.step();
      a4.step();
      Assert.assertEquals(a4.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a5.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a4.getMyFollowers().size(), 0);
      Assert.assertEquals(a5.getMyFollowers().size(), 1);
      Assert.assertNotNull(a4.getMyLeader());
      Assert.assertNull(a5.getMyLeader());
      Assert.assertSame(a4.getMyLeader(), a5);
   }
   
   @Test
   public void CheckHorseFollowsLeader() {
	  a4.vlc = new Vector(1,-1);
	  a5.vlc = new Vector(2,0);
      a5.step();
      a4.step();
      a5.step();
      Assert.assertEquals(a4.loc.toString(), (new Vector(8,2)).toString());
   }
   
   @Test
   public void CheckDoesNotMakeHorseLeader() {
	  a4.vlc = new Vector(1,-1);
	  a5.vlc = new Vector(2,0);
      a4.step();
      a5.step();
      Assert.assertEquals(a4.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a5.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a4.getMyFollowers().size(), 0);
      Assert.assertEquals(a5.getMyFollowers().size(), 0);
      Assert.assertNull(a4.getMyLeader());
      Assert.assertNull(a5.getMyLeader());
   }
   
   @Test
   public void CheckDoesMakeCowLeader() {
      a.vlc = new Vector(1,-1);
      a2.vlc = new Vector(2,0);
      a.step();
      a2.step();
      Assert.assertEquals(a.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a2.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a.getMyFollowers().size(), 1);
      Assert.assertEquals(a2.getMyFollowers().size(), 0);
      Assert.assertNull(a.getMyLeader());
      Assert.assertNotNull(a2.getMyLeader());
      Assert.assertSame(a2.getMyLeader(), a);
   }

   @Test
   public void CheckCowFollowsLeader() {
      a.vlc = new Vector(1,-1);
      a2.vlc = new Vector(2,0);
      a2.step();
      a.step();
      a2.step();
      Assert.assertEquals(a.loc.toString(), (new Vector(8,2)).toString());
   }
   
   @Test
   public void CheckCollectsMultipleFollowers() {
      a4.vlc = new Vector(1,-1);
      a5.vlc = new Vector(2,0);
      a6.vlc = new Vector(0,1);
      a5.step();
      a4.step();
      a6.step();
      Assert.assertEquals(a4.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a5.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a6.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a4.getMyFollowers().size(), 0);
      Assert.assertEquals(a5.getMyFollowers().size(), 2);
      Assert.assertEquals(a6.getMyFollowers().size(), 0);
      Assert.assertNotNull(a4.getMyLeader());
      Assert.assertNotNull(a6.getMyLeader());
      Assert.assertNull(a5.getMyLeader());
      Assert.assertSame(a4.getMyLeader(), a5);
      Assert.assertSame(a6.getMyLeader(), a5);
   }
   
   @Test
   public void CheckHandsOffFollowers() {
      a4.vlc = new Vector(1,-1);
      a5.vlc = new Vector(2,0);
      a6.vlc = new Vector(0,1);
      a7.vlc = new Vector(-3,0);
      a5.step();
      a4.step();
      a6.step();
      a5.step();
      Assert.assertEquals(a5.loc.toString(), (new Vector(8,2)).toString());
      Assert.assertEquals(a7.loc.toString(), (new Vector(8,2)).toString());
      Assert.assertEquals(a5.getMyFollowers().size(), 0);
      Assert.assertEquals(a7.getMyFollowers().size(), 3);
      Assert.assertNotNull(a5.getMyLeader());
      Assert.assertNull(a7.getMyLeader());
      Assert.assertSame(a5.getMyLeader(), a7);
   }
   
   @Test
   public void CheckFollowingNewLeader() {
      a4.vlc = new Vector(1,-1);
      a5.vlc = new Vector(2,0);
      a6.vlc = new Vector(0,1);
      a7.vlc = new Vector(-3,0);
      a5.step();
      a4.step();
      a6.step();
      a5.step();
      a7.step();
      Assert.assertEquals(a4.loc.toString(), (new Vector(5,2)).toString());
      Assert.assertEquals(a5.loc.toString(), (new Vector(5,2)).toString());
      Assert.assertEquals(a6.loc.toString(), (new Vector(5,2)).toString());
      Assert.assertEquals(a7.loc.toString(), (new Vector(5,2)).toString());
   }
   
   @Test
   public void CheckHorsesFollowHorses() {
	  a.vlc = new Vector(1,-1);
	  a5.vlc = new Vector(1,0);
      a.step();
      a5.step();
      a5.step();
      Assert.assertEquals(a.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a5.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a.getMyFollowers().size(), 0);
      Assert.assertEquals(a5.getMyFollowers().size(), 0);
   }
   
   @Test
   public void CheckCowsFollowCows() {
	  a.vlc = new Vector(1,-1);
	  a5.vlc = new Vector(1,0);
      a5.step();
      a5.step();
      a.step();
      Assert.assertEquals(a.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a5.loc.toString(), (new Vector(6,2)).toString());
      Assert.assertEquals(a.getMyFollowers().size(), 0);
      Assert.assertEquals(a5.getMyFollowers().size(), 0);
   }
}