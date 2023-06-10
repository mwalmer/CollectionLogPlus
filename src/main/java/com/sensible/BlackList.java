package com.sensible;

import net.runelite.api.ItemID;

import java.util.HashMap;

public class BlackList {
   public HashMap<Integer, Integer> Pets = new HashMap<>();
   public HashMap<String, Integer> Jars;

   private final int PETS[] = {
           ItemID.ABYSSAL_ORPHAN
   };
   public BlackList()
   {
      for(int i = 0 ; i < PETS.length; i++)
      {
         Pets.put(PETS[i], 1);
      }
   }

   public boolean IsBlackListed(int id)
   {
      return Pets.containsKey(id);
   }
}
