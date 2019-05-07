package com.cstaley.CodeWorlds.Displays;

import com.cstaley.CodeWorlds.Entities.Brick;

import java.util.Observer;

public interface Display extends Observer {

   /* Add |dsp| to the list of Displayables to be shown in this Display */
   public abstract void addDisplayable(Brick dsp);

   /* Update the display to reflect current state of Displayables.  Assume we
    * are at time |time|. */
   public abstract void redraw(int time);
}