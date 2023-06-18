package com.collectionlogplus;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("CollectionLogPlus")
public interface CollectionLogPlusConfig extends Config
{
	@ConfigItem(
			keyName = "includeBanked",
			name = "Included Banked Items",
			description = "Banked items will be added to the collection log.",
			position = 1
	)
	default boolean includeBanked()
	{
		return true;
	}

	@ConfigItem(
		keyName = "removepets",
		name = "Remove Pets",
		description = "",
		position = 2
	)
	default boolean petsDisabled()
	{
		return true;
	}

	@ConfigItem(
			keyName = "removejars",
			name = "Remove Jars",
			description = "",
			position = 3
	)
	default boolean jarsDisabled()
	{
		return true;
	}

	@ConfigItem(
			keyName = "completedcolor",
			name = "Completed Color",
			description = "",
			position = 4
	)
	default Color completedColor()
	{
		return new Color(0x00ff00);
	}

	@ConfigItem(
			keyName = "hidepets",
			name = "Hide Pets",
			description = "",
			position = 5
	)
	default boolean hidePets()
	{
		return false;
	}

	@ConfigItem(
			keyName = "hidejars",
			name = "Hide Jars",
			description = "",
			position = 6
	)
	default boolean hideJars()
	{
		return false;
	}

}
