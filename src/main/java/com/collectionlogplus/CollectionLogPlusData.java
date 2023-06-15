package com.collectionlogplus;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;

import java.util.HashMap;
import java.util.HashSet;

@Slf4j
public class CollectionLogPlusData {
   public static final HashSet<Integer> PETS = new HashSet<>();
   public static final HashSet<Integer> JARS = new HashSet<>();
   public static final HashSet<Integer> RARES = new HashSet<>();
   public static final HashMap<Integer, Integer> FUSED = new HashMap<>();
   public static final HashMap<Integer, Integer> MISSING_WIDGET_IDS = new HashMap<>();
   //todo: figure out when to load collection log data
   //  right now this is loaded whenever the class is used, which is when collection log is opened :|
   static
   {
      //todo: remove
      PETS.add(ItemID.DRAGON_AXE);

      PETS.add(ItemID.ABYSSAL_ORPHAN);
      PETS.add(ItemID.IKKLE_HYDRA);
      PETS.add(ItemID.CALLISTO_CUB);
      PETS.add(ItemID.HELLPUPPY);
      PETS.add(ItemID.PET_CHAOS_ELEMENTAL);
      PETS.add(ItemID.PET_ZILYANA);
      PETS.add(ItemID.PET_DARK_CORE);
      PETS.add(ItemID.PET_DAGANNOTH_PRIME);
      PETS.add(ItemID.PET_DAGANNOTH_SUPREME);
      PETS.add(ItemID.PET_DAGANNOTH_REX);
      PETS.add(ItemID.TZREKJAD);
      PETS.add(ItemID.YOUNGLLEF);
      PETS.add(ItemID.PET_GENERAL_GRAARDOR);
      PETS.add(ItemID.BABY_MOLE);
      PETS.add(ItemID.NOON);
      PETS.add(ItemID.JALNIBREK);
      PETS.add(ItemID.KALPHITE_PRINCESS);
      PETS.add(ItemID.PRINCE_BLACK_DRAGON);
      PETS.add(ItemID.PET_KRAKEN);
      PETS.add(ItemID.PET_KREEARRA);
      PETS.add(ItemID.PET_KRIL_TSUTSAROTH);
      PETS.add(ItemID.NEXLING);
      PETS.add(ItemID.LITTLE_NIGHTMARE);
      PETS.add(ItemID.MUPHIN);
      PETS.add(ItemID.SRARACHA);
      PETS.add(ItemID.SCORPIAS_OFFSPRING);
      PETS.add(ItemID.SKOTOS);
      PETS.add(ItemID.TINY_TEMPOR);
      PETS.add(ItemID.PET_SMOKE_DEVIL);
      PETS.add(ItemID.VENENATIS_SPIDERLING);
      PETS.add(ItemID.VETION_JR);
      PETS.add(ItemID.VORKI);
      PETS.add(ItemID.PHOENIX);
      PETS.add(ItemID.SMOLCANO);
      PETS.add(ItemID.PET_SNAKELING);
      PETS.add(ItemID.OLMLET);
      PETS.add(ItemID.LIL_ZIK);
      PETS.add(ItemID.TUMEKENS_GUARDIAN);
      PETS.add(ItemID.BLOODHOUND);
      PETS.add(ItemID.PET_PENANCE_QUEEN);
      PETS.add(ItemID.ABYSSAL_PROTECTOR);
      PETS.add(ItemID.LIL_CREATOR);
      PETS.add(ItemID.CHOMPY_CHICK);

      JARS.add(ItemID.JAR_OF_MIASMA);
      JARS.add(ItemID.JAR_OF_CHEMICALS);
      JARS.add(ItemID.JAR_OF_SOULS);
      JARS.add(ItemID.JAR_OF_SPIRITS);
      JARS.add(ItemID.JAR_OF_STONE);
      JARS.add(ItemID.JAR_OF_SAND);
      JARS.add(ItemID.JAR_OF_DIRT);
      JARS.add(ItemID.JAR_OF_DREAMS);
      JARS.add(ItemID.JAR_OF_EYES);
      JARS.add(ItemID.JAR_OF_DARKNESS);
      JARS.add(ItemID.JAR_OF_SMOKE);
      JARS.add(ItemID.JAR_OF_DECAY);
      JARS.add(ItemID.JAR_OF_SWAMP);

      RARES.add(ItemID.MAGMA_MUTAGEN);
      RARES.add(ItemID.TANZANITE_MUTAGEN);

      MISSING_WIDGET_IDS.put(25617, 10859); // Tea flask
      MISSING_WIDGET_IDS.put(25618, 10877); // Red satchel
      MISSING_WIDGET_IDS.put(25619, 10878); // Green satchel
      MISSING_WIDGET_IDS.put(25620, 10879); // Red satchel
      MISSING_WIDGET_IDS.put(25621, 10880); // Black satchel
      MISSING_WIDGET_IDS.put(25622, 10881); // Gold satchel
      MISSING_WIDGET_IDS.put(25623, 10882); // Rune satchel
      MISSING_WIDGET_IDS.put(25624, 13273); // Unsired
      MISSING_WIDGET_IDS.put(25627, 12019); // Coal bag
      MISSING_WIDGET_IDS.put(25628, 12020); // Gem bag
      MISSING_WIDGET_IDS.put(25629, 24882); // Plank sack
      MISSING_WIDGET_IDS.put(25630, 12854); // Flamtaer bag

      FUSED.put(ItemID.ARCANE_SPIRIT_SHIELD, ItemID.ARCANE_SIGIL);

      // todo: remove
      FUSED.put(ItemID.BURNT_PAGE, ItemID.DRAGON_AXE);
   }
}
