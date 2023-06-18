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
   public static final HashMap<Integer, Integer[]> FUSED = new HashMap<>();
   public static final HashMap<Integer, Integer> MISSING_WIDGET_IDS = new HashMap<>();
   //todo: figure out when to load collection log data
   //  right now this is loaded whenever the class is used, which is when collection log is opened :|
   static
   {
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

      FUSED.put(ItemID.ABYSSAL_BLUDGEON, new Integer[]{ ItemID.BLUDGEON_SPINE, ItemID.BLUDGEON_CLAW, ItemID.BLUDGEON_AXON });
      FUSED.put(ItemID.RED_SLAYER_HELMET, new Integer[]{ ItemID.ABYSSAL_HEAD });
      FUSED.put(ItemID.RED_SLAYER_HELMET_I, new Integer[]{ ItemID.ABYSSAL_HEAD });
      FUSED.put(ItemID.DRAGON_HUNTER_LANCE, new Integer[]{ ItemID.ZAMORAKIAN_HASTA });
      FUSED.put(ItemID.BONECRUSHER_NECKLACE, new Integer[]{ ItemID.DRAGONBONE_NECKLACE });

      //TODO: figure out which barrows id to use
      FUSED.put(ItemID.KARILS_COIF, new Integer[]{ ItemID.KARILS_COIF });
      FUSED.put(ItemID.KARILS_LEATHERTOP, new Integer[]{ ItemID.KARILS_LEATHERTOP });
      FUSED.put(ItemID.KARILS_LEATHERSKIRT, new Integer[]{ ItemID.KARILS_LEATHERSKIRT });
      FUSED.put(ItemID.KARILS_CROSSBOW, new Integer[]{ ItemID.KARILS_CROSSBOW });

      FUSED.put(ItemID.AHRIMS_HOOD, new Integer[]{ ItemID.AHRIMS_HOOD });
      FUSED.put(ItemID.AHRIMS_ROBETOP, new Integer[]{ ItemID.AHRIMS_ROBETOP });
      FUSED.put(ItemID.AHRIMS_ROBESKIRT, new Integer[]{ ItemID.AHRIMS_ROBESKIRT });
      FUSED.put(ItemID.AHRIMS_STAFF, new Integer[]{ ItemID.AHRIMS_STAFF });

      FUSED.put(ItemID.DHAROKS_HELM, new Integer[]{ ItemID.DHAROKS_HELM });
      FUSED.put(ItemID.DHAROKS_PLATEBODY, new Integer[]{ ItemID.DHAROKS_PLATEBODY });
      FUSED.put(ItemID.DHAROKS_PLATELEGS, new Integer[]{ ItemID.DHAROKS_PLATELEGS });
      FUSED.put(ItemID.DHAROKS_GREATAXE, new Integer[]{ ItemID.DHAROKS_GREATAXE });

      FUSED.put(ItemID.GUTHANS_HELM, new Integer[]{ ItemID.GUTHANS_HELM });
      FUSED.put(ItemID.GUTHANS_PLATEBODY, new Integer[]{ ItemID.GUTHANS_PLATEBODY });
      FUSED.put(ItemID.GUTHANS_CHAINSKIRT, new Integer[]{ ItemID.GUTHANS_CHAINSKIRT });
      FUSED.put(ItemID.GUTHANS_WARSPEAR, new Integer[]{ ItemID.GUTHANS_WARSPEAR });

      FUSED.put(ItemID.TORAGS_HELM, new Integer[]{ ItemID.TORAGS_HELM });
      FUSED.put(ItemID.TORAGS_PLATEBODY, new Integer[]{ ItemID.TORAGS_PLATEBODY });
      FUSED.put(ItemID.TORAGS_PLATELEGS, new Integer[]{ ItemID.TORAGS_PLATELEGS });
      FUSED.put(ItemID.TORAGS_HAMMERS, new Integer[]{ ItemID.TORAGS_HAMMERS });

      FUSED.put(ItemID.VERACS_HELM, new Integer[]{ ItemID.VERACS_HELM });
      FUSED.put(ItemID.VERACS_BRASSARD, new Integer[]{ ItemID.VERACS_BRASSARD });
      FUSED.put(ItemID.VERACS_PLATESKIRT, new Integer[]{ ItemID.VERACS_PLATESKIRT });
      FUSED.put(ItemID.VERACS_FLAIL, new Integer[]{ ItemID.VERACS_FLAIL });

      FUSED.put(ItemID.BRYOPHYTAS_STAFF, new Integer[]{ ItemID.BRYOPHYTAS_ESSENCE });
      FUSED.put(ItemID.TYRANNICAL_RING_I, new Integer[]{ ItemID.TYRANNICAL_RING });
      FUSED.put(ItemID.ETERNAL_BOOTS, new Integer[]{ ItemID.ETERNAL_CRYSTAL, ItemID.INFINITY_BOOTS });
      FUSED.put(ItemID.PEGASIAN_BOOTS, new Integer[]{ ItemID.PEGASIAN_CRYSTAL, ItemID.RANGER_BOOTS });
      FUSED.put(ItemID.PRIMORDIAL_BOOTS, new Integer[]{ ItemID.PRIMORDIAL_CRYSTAL, ItemID.DRAGON_BOOTS });
      //todo add uncharged
      FUSED.put(ItemID.INFERNAL_AXE, new Integer[]{ ItemID.SMOULDERING_STONE, ItemID.DRAGON_AXE });
      FUSED.put(ItemID.INFERNAL_HARPOON, new Integer[]{ ItemID.SMOULDERING_STONE, ItemID.DRAGON_HARPOON });
      FUSED.put(ItemID.INFERNAL_PICKAXE, new Integer[]{ ItemID.SMOULDERING_STONE, ItemID.DRAGON_PICKAXE });
      FUSED.put(ItemID.ODIUM_WARD, new Integer[]{ ItemID.ODIUM_SHARD_1, ItemID.ODIUM_SHARD_2, ItemID.ODIUM_SHARD_3 });
      FUSED.put(ItemID.MALEDICTION_WARD, new Integer[]{ ItemID.MALEDICTION_SHARD_1, ItemID.MALEDICTION_SHARD_2, ItemID.MALEDICTION_SHARD_3  });
      FUSED.put(ItemID.ZARYTE_CROSSBOW, new Integer[]{ ItemID.ARMADYL_CROSSBOW });
      FUSED.put(ItemID.SARADOMIN_GODSWORD, new Integer[]{ ItemID.SARADOMIN_HILT, ItemID.GODSWORD_SHARD_1, ItemID.GODSWORD_SHARD_2, ItemID.GODSWORD_SHARD_3 });
      FUSED.put(ItemID.SARADOMINS_BLESSED_SWORD, new Integer[]{ ItemID.SARADOMIN_SWORD });
      FUSED.put(ItemID.STAFF_OF_LIGHT, new Integer[]{ ItemID.SARADOMINS_LIGHT, ItemID.STAFF_OF_THE_DEAD });

      FUSED.put(ItemID.ANCIENT_GODSWORD, new Integer[]{ ItemID.GODSWORD_SHARD_1, ItemID.GODSWORD_SHARD_2, ItemID.GODSWORD_SHARD_3 });
      FUSED.put(ItemID.BANDOS_GODSWORD, new Integer[]{ ItemID.BANDOS_HILT, ItemID.GODSWORD_SHARD_1, ItemID.GODSWORD_SHARD_2, ItemID.GODSWORD_SHARD_3 });
      FUSED.put(ItemID.ZAMORAK_GODSWORD, new Integer[]{ ItemID.ZAMORAKIAN_HASTA, ItemID.GODSWORD_SHARD_1, ItemID.GODSWORD_SHARD_2, ItemID.GODSWORD_SHARD_3 });
      FUSED.put(ItemID.ARMADYL_GODSWORD, new Integer[]{ ItemID.ARMADYL_HILT, ItemID.GODSWORD_SHARD_1, ItemID.GODSWORD_SHARD_2, ItemID.GODSWORD_SHARD_3 });
      FUSED.put(ItemID.ELYSIAN_SPIRIT_SHIELD, new Integer[]{ ItemID.ELYSIAN_SIGIL, ItemID.HOLY_ELIXIR, ItemID.SPIRIT_SHIELD });
      FUSED.put(ItemID.SPECTRAL_SPIRIT_SHIELD, new Integer[]{ ItemID.SPECTRAL_SIGIL, ItemID.HOLY_ELIXIR, ItemID.SPIRIT_SHIELD });
      FUSED.put(ItemID.ARCANE_SPIRIT_SHIELD, new Integer[]{ ItemID.ARCANE_SIGIL, ItemID.HOLY_ELIXIR, ItemID.SPIRIT_SHIELD });
      FUSED.put(ItemID.ELIDINIS_WARD_F, new Integer[]{ ItemID.ARCANE_SIGIL });
      FUSED.put(ItemID.BLESSED_SPIRIT_SHIELD, new Integer[]{ ItemID.HOLY_ELIXIR, ItemID.SPIRIT_SHIELD });
      FUSED.put(ItemID.BERSERKER_RING_I, new Integer[]{ ItemID.BERSERKER_RING });
      FUSED.put(ItemID.ARCHERS_RING_I, new Integer[]{ ItemID.ARCHERS_RING });
      FUSED.put(ItemID.SEERS_RING_I, new Integer[]{ ItemID.SEERS_RING });
      FUSED.put(ItemID.WARRIOR_RING_I, new Integer[]{ ItemID.WARRIOR_RING });
      FUSED.put(ItemID.GUARDIAN_BOOTS, new Integer[]{ ItemID.BANDOS_BOOTS, ItemID.BLACK_TOURMALINE_CORE });
      FUSED.put(ItemID.GRANITE_RING_I, new Integer[]{ ItemID.GRANITE_RING });
      FUSED.put(ItemID.GRANITE_CANNONBALL, new Integer[]{ ItemID.GRANITE_DUST });
      FUSED.put(ItemID.GREEN_SLAYER_HELMET, new Integer[]{ ItemID.KQ_HEAD });
      FUSED.put(ItemID.GREEN_SLAYER_HELMET_I, new Integer[]{ ItemID.KQ_HEAD });
      FUSED.put(ItemID.DRAGON_PLATEBODY, new Integer[]{ ItemID.DRAGON_CHAINBODY, ItemID.DRAGON_METAL_SHARD, ItemID.DRAGON_METAL_LUMP });
      FUSED.put(ItemID.BLACK_SLAYER_HELMET, new Integer[]{ ItemID.KBD_HEADS });
      FUSED.put(ItemID.BLACK_SLAYER_HELMET_I, new Integer[]{ ItemID.KBD_HEADS });
      FUSED.put(ItemID.DRAGON_HUNTER_CROSSBOW_B, new Integer[]{ ItemID.KBD_HEADS });
      FUSED.put(ItemID.ABYSSAL_TENTACLE, new Integer[]{ ItemID.KRAKEN_TENTACLE });
      FUSED.put(ItemID.TRIDENT_OF_THE_SWAMP, new Integer[]{ ItemID.TRIDENT_OF_THE_SEAS });
      FUSED.put(ItemID.TOXIC_STAFF_OF_THE_DEAD, new Integer[]{ ItemID.MAGIC_FANG, ItemID.STAFF_OF_THE_DEAD });
      FUSED.put(ItemID.STAFF_OF_BALANCE, new Integer[]{ ItemID.STAFF_OF_THE_DEAD });
      FUSED.put(ItemID.ZAMORAKIAN_HASTA, new Integer[]{ ItemID.ZAMORAKIAN_SPEAR });
      FUSED.put(ItemID.MYSTIC_STEAM_STAFF, new Integer[]{ ItemID.STEAM_BATTLESTAFF });
      FUSED.put(ItemID.PURPLE_SLAYER_HELMET, new Integer[]{ ItemID.DARK_CLAW });
      FUSED.put(ItemID.PURPLE_SLAYER_HELMET_I, new Integer[]{ ItemID.DARK_CLAW });
      FUSED.put(ItemID.MYSTIC_SMOKE_STAFF, new Integer[]{ ItemID.SMOKE_BATTLESTAFF });
      FUSED.put(ItemID.TREASONOUS_RING_I, new Integer[]{ ItemID.TREASONOUS_RING });
      FUSED.put(ItemID.RING_OF_THE_GODS_I, new Integer[]{ ItemID.RING_OF_THE_GODS });
      FUSED.put(ItemID.TURQUOISE_SLAYER_HELMET, new Integer[]{ ItemID.VORKATHS_HEAD });
      FUSED.put(ItemID.TURQUOISE_SLAYER_HELMET_I, new Integer[]{ ItemID.VORKATHS_HEAD });
      FUSED.put(ItemID.AVAS_ASSEMBLER, new Integer[]{ ItemID.VORKATHS_HEAD });
      FUSED.put(ItemID.DRAGONFIRE_SHIELD, new Integer[]{ ItemID.DRACONIC_VISAGE });
      FUSED.put(ItemID.DRAGONFIRE_WARD, new Integer[]{ ItemID.SKELETAL_VISAGE });
      FUSED.put(ItemID.TOXIC_BLOWPIPE, new Integer[]{ ItemID.TANZANITE_FANG });
      FUSED.put(ItemID.TOXIC_BLOWPIPE_EMPTY, new Integer[]{ ItemID.TANZANITE_FANG });
      FUSED.put(ItemID.SERPENTINE_HELM, new Integer[]{ ItemID.SERPENTINE_VISAGE });
      FUSED.put(ItemID.SERPENTINE_HELM_UNCHARGED, new Integer[]{ ItemID.SERPENTINE_VISAGE });
      FUSED.put(ItemID.TANZANITE_HELM, new Integer[]{ ItemID.SERPENTINE_VISAGE, ItemID.TANZANITE_MUTAGEN });
      FUSED.put(ItemID.TANZANITE_HELM_UNCHARGED, new Integer[]{ ItemID.SERPENTINE_VISAGE, ItemID.TANZANITE_MUTAGEN });
      FUSED.put(ItemID.MAGMA_HELM, new Integer[]{ ItemID.SERPENTINE_VISAGE, ItemID.MAGMA_MUTAGEN });
      FUSED.put(ItemID.MAGMA_HELM_UNCHARGED, new Integer[]{ ItemID.SERPENTINE_VISAGE, ItemID.MAGMA_MUTAGEN });
      FUSED.put(ItemID.KODAI_WAND, new Integer[]{ ItemID.KODAI_INSIGNIA, ItemID.MASTER_WAND, ItemID.TEACHER_WAND, ItemID.APPRENTICE_WAND, ItemID.BEGINNER_WAND });
      FUSED.put(ItemID.TWISTED_ANCESTRAL_HAT, new Integer[]{ ItemID.ANCESTRAL_HAT, ItemID.TWISTED_ANCESTRAL_COLOUR_KIT });
      FUSED.put(ItemID.TWISTED_ANCESTRAL_ROBE_TOP, new Integer[]{ ItemID.ANCESTRAL_ROBE_TOP, ItemID.TWISTED_ANCESTRAL_COLOUR_KIT });
      FUSED.put(ItemID.TWISTED_ANCESTRAL_ROBE_BOTTOM, new Integer[]{ ItemID.ANCESTRAL_ROBE_BOTTOM, ItemID.TWISTED_ANCESTRAL_COLOUR_KIT });
      FUSED.put(ItemID.AVERNIC_DEFENDER, new Integer[]{ ItemID.AVERNIC_DEFENDER_HILT, ItemID.DRAGON_DEFENDER });
      FUSED.put(ItemID.SANGUINE_SCYTHE_OF_VITUR, new Integer[]{ ItemID.SANGUINE_ORNAMENT_KIT, ItemID.SCYTHE_OF_VITUR_UNCHARGED });
      FUSED.put(ItemID.SCYTHE_OF_VITUR, new Integer[]{ ItemID.SCYTHE_OF_VITUR_UNCHARGED });
      FUSED.put(ItemID.SANGUINESTI_STAFF, new Integer[]{ ItemID.SANGUINESTI_STAFF_UNCHARGED });
      FUSED.put(ItemID.HOLY_GHRAZI_RAPIER, new Integer[]{ ItemID.HOLY_ORNAMENT_KIT, ItemID.GHRAZI_RAPIER });
      FUSED.put(ItemID.HOLY_SANGUINESTI_STAFF, new Integer[]{ ItemID.HOLY_ORNAMENT_KIT, ItemID.SANGUINESTI_STAFF_UNCHARGED });
      FUSED.put(ItemID.HOLY_SCYTHE_OF_VITUR, new Integer[]{ ItemID.HOLY_ORNAMENT_KIT, ItemID.SCYTHE_OF_VITUR_UNCHARGED });

      FUSED.put(ItemID.SPIRIT_ANGLER_HEADBAND, new Integer[]{ ItemID.ANGLER_HAT, ItemID.SPIRIT_FLAKES });
      FUSED.put(ItemID.SPIRIT_ANGLER_TOP, new Integer[]{ ItemID.ANGLER_TOP, ItemID.SPIRIT_FLAKES });
      FUSED.put(ItemID.SPIRIT_ANGLER_WADERS, new Integer[]{ ItemID.ANGLER_WADERS, ItemID.SPIRIT_FLAKES });
      FUSED.put(ItemID.SPIRIT_ANGLER_BOOTS, new Integer[]{ ItemID.ANGLER_BOOTS, ItemID.SPIRIT_FLAKES });
      FUSED.put(ItemID.ZENYTE_RING, new Integer[]{ ItemID.ZENYTE_SHARD });
      FUSED.put(ItemID.RING_OF_SUFFERING, new Integer[]{ ItemID.ZENYTE_SHARD });
      FUSED.put(ItemID.ZENYTE_NECKLACE, new Integer[]{ ItemID.ZENYTE_SHARD });
      FUSED.put(ItemID.NECKLACE_OF_ANGUISH, new Integer[]{ ItemID.ZENYTE_SHARD });
      FUSED.put(ItemID.ZENYTE_BRACELET, new Integer[]{ ItemID.ZENYTE_SHARD });
      FUSED.put(ItemID.TORMENTED_BRACELET, new Integer[]{ ItemID.ZENYTE_SHARD });
      FUSED.put(ItemID.ZENYTE_AMULET, new Integer[]{ ItemID.ZENYTE_SHARD });
      FUSED.put(ItemID.AMULET_OF_TORTURE, new Integer[]{ ItemID.ZENYTE_SHARD });

      FUSED.put(ItemID.RAW_TUNA, new Integer[]{ItemID.DRAGON_AXE});
   }
}
