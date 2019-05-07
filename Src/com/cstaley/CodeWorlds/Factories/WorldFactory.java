package com.cstaley.CodeWorlds.Factories;

import com.cstaley.CodeWorlds.Entities.Body;
import com.cstaley.CodeWorlds.Util.CWSException;

public interface WorldFactory {
   public Body getWorld();               // Retrieve the World w/o rebuilding
   public WorldFactory build() throws CWSException;     // Build the World
}
