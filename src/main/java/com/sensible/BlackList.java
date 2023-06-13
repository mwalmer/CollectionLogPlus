package com.sensible;

import net.runelite.api.ItemID;

import java.util.HashMap;

public class BlackList {
   public HashMap<Integer, Integer> Pets = new HashMap<>();
   public HashMap<Integer, Integer> Jars = new HashMap<>();
   public HashMap<Integer, Integer> LegacyItems = new HashMap<>();

   private final int[] PETS = {
           ItemID.ABYSSAL_ORPHAN,
           ItemID.IKKLE_HYDRA,
           ItemID.CALLISTO_CUB,
           ItemID.HELLPUPPY,
           ItemID.PET_CHAOS_ELEMENTAL,
           ItemID.PET_ZILYANA,
           ItemID.PET_DARK_CORE,
           ItemID.PET_DAGANNOTH_PRIME,
           ItemID.PET_DAGANNOTH_SUPREME,
           ItemID.PET_DAGANNOTH_REX,
           ItemID.TZREKJAD,
           ItemID.YOUNGLLEF,
           ItemID.PET_GENERAL_GRAARDOR,
           ItemID.BABY_MOLE,
           ItemID.NOON,
           ItemID.JALNIBREK,
           ItemID.KALPHITE_PRINCESS,
           ItemID.PRINCE_BLACK_DRAGON,
           ItemID.PET_KRAKEN,
           ItemID.PET_KREEARRA,
           ItemID.PET_KRIL_TSUTSAROTH,
           ItemID.NEXLING,
           ItemID.LITTLE_NIGHTMARE,
           ItemID.MUPHIN,
           ItemID.SRARACHA,
           ItemID.SCORPIAS_OFFSPRING,
           ItemID.SKOTOS,
           ItemID.TINY_TEMPOR,
           ItemID.PET_SMOKE_DEVIL,
           ItemID.VENENATIS_SPIDERLING,
           ItemID.VETION_JR,
           ItemID.VORKI,
           ItemID.PHOENIX,
           ItemID.SMOLCANO,
           ItemID.PET_SNAKELING,
           ItemID.OLMLET,
           ItemID.LIL_ZIK,
           ItemID.TUMEKENS_GUARDIAN,
           ItemID.BLOODHOUND,
           ItemID.PET_PENANCE_QUEEN,
           ItemID.ABYSSAL_PROTECTOR,
           ItemID.LIL_CREATOR,
           ItemID.CHOMPY_CHICK
   };

   private final int[] JARS = {
           ItemID.JAR_OF_MIASMA,
           ItemID.JAR_OF_CHEMICALS,
           ItemID.JAR_OF_SOULS,
           ItemID.JAR_OF_SPIRITS,
           ItemID.JAR_OF_STONE,
           ItemID.JAR_OF_SAND,
           ItemID.JAR_OF_DIRT,
           ItemID.JAR_OF_DREAMS,
           ItemID.JAR_OF_EYES,
           ItemID.JAR_OF_DARKNESS,
           ItemID.JAR_OF_SMOKE,
           ItemID.JAR_OF_DECAY,
           ItemID.JAR_OF_SWAMP
   };

   private final int[] legacyItems = {
      ItemID.AIR_RUNE
   };

   private final int RARES[] = {
           ItemID.ABYSSAL_ORPHAN
   };

   public BlackList()
   {
      for(int i = 0 ; i < legacyItems.length; i++)
      {
         LegacyItems.put(legacyItems[i], 1);
      }

      for(int i = 0 ; i < PETS.length; i++)
      {
         Pets.put(PETS[i], 1);
      }

      for(int i = 0 ; i < JARS.length; i++)
      {
         Jars.put(JARS[i], 1);
      }
   }

   public boolean IsPet(int id)
   {
      return Pets.containsKey(id);
   }
   public boolean IsJar(int id)
   {
      return Jars.containsKey(id);
   }

   public boolean IsLegacy(int id)
   {
      return LegacyItems.containsKey(id);
   }
//   public boolean IsRare(int id)
//   {
//      return Rares.containsKey(id);
//   }
}
