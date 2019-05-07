package com.cstaley.CodeWorlds.Entities;

import com.cstaley.CodeWorlds.Steppable;
import com.cstaley.CodeWorlds.Util.Rectangle;
import com.cstaley.CodeWorlds.Util.Vector;

import java.util.Iterator;

public interface Body extends Steppable {
   Iterator<Brick> getBricks();
   Iterator<Body> getBodies();
   boolean isSteppable();
   Rectangle getBounds();
   Body clone(Vector offset);
}
