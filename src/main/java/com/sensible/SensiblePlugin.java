package com.sensible;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@PluginDescriptor(
	name = "Sensible"
)
public class SensiblePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;
	@Inject
	private ConfigManager configManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private SensibleConfig config;

	public HashMap<String, Integer> pages = new HashMap<>();
	public static final File MY_DATA = new File(RuneLite.RUNELITE_DIR, "myData");
	private BlackList blackList;

	private long accountHash = -1;
	private final int COLLECTION_DRAW_LOG_SCRIPT_ID = 2732;
	private final int STATUS_UNKNOWN = -1;
	private final int STATUS_NOT_OBTAINED = 0;
	private boolean BLACKLIST_PETS = true;
	private boolean BLACKLIST_JARS = true;
	private boolean BLACKLIST_RARES = true;

	private final Map<Integer, Integer> MISSING_WIDGET_IDS = new ImmutableMap.Builder<Integer, Integer>()
			.put(25617, 10859) // Tea flask
			.put(25618, 10877) // Red satchel
			.put(25619, 10878) // Green satchel
			.put(25620, 10879) // Red satchel
			.put(25621, 10880) // Black satchel
			.put(25622, 10881) // Gold satchel
			.put(25623, 10882) // Rune satchel
			.put(25624, 13273) // Unsired
			.put(25627, 12019) // Coal bag
			.put(25628, 12020) // Gem bag
			.put(25629, 24882) // Plank sack
			.put(25630, 12854) // Flamtaer bag
			.build();

	@Override
	protected void startUp()
	{
		blackList = new BlackList();

		if(!MY_DATA.exists() && !MY_DATA.mkdir())
		{
			log.error("Could not make directory!");
			shutDown();
			return;
		}
	}

	@Override
	protected void shutDown()
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if(gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			accountHash = client.getAccountHash();
			LoadFile();
		}
		if(gameStateChanged.getGameState() == GameState.HOPPING || gameStateChanged.getGameState() == GameState.LOGIN_SCREEN)
		{
			if(accountHash == -1)
				return;
			DataHandler.Serialize(CollectionLog.COLLECTION_LOG_DATA, accountHash, "collection-log");
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded widgetLoaded)
	{
		int widgetID = widgetLoaded.getGroupId();
		if(widgetID != WidgetID.COLLECTION_LOG_ID)
			return;

		InitializeCollectionLog();


//		int widgetID = widgetLoaded.getGroupId();
//		if(widgetID != WidgetID.COLLECTION_LOG_ID)
//			return;
//
//		Widget widget = client.getWidget(WidgetInfo.COLLECTION_LOG);
//		if(widget == null)
//			return;
//
//		//widget.chil
//
//		log.debug("widget found");
//		Widget[] children = widget.getStaticChildren();
//		if(children == null)
//			return;
//
//		log.debug("collection log opened!");
//		log.debug(String.valueOf(children.length));
	}

	private void UpdatePage()
	{
		Widget widget = client.getWidget(621, 36);
		if(widget == null || widget.getChildren() == null)
			return;

		Widget[] children = widget.getChildren();
		for(int i = 0; i < children.length; i++)
		{
			Widget child = widget.getChild(i);
			int itemId = child.getItemId();
			if(MISSING_WIDGET_IDS.containsKey(itemId))
				itemId = MISSING_WIDGET_IDS.get(itemId);

			if(CollectionLog.COLLECTION_LOG_DATA.containsKey(itemId))
			{
				int itemQuantity = CollectionLog.COLLECTION_LOG_DATA.get(itemId);
				if(itemQuantity < child.getItemQuantity())
				{
					CollectionLog.COLLECTION_LOG_DATA.put(itemId, child.getItemQuantity());
					itemQuantity = child.getItemQuantity();
				}

				if(itemQuantity > 0)
				{
					child.setOpacity(0);
					child.setItemQuantity(itemQuantity);
					//TODO: figure out difference between quantity 2 and 1
					child.setItemQuantityMode(2);
				}

			}

			child.revalidate();
		}
	}

	@Subscribe
	public void onScriptPostFired(ScriptPostFired scriptPostFired)
	{
		if(scriptPostFired.getScriptId() == ScriptID.BANKMAIN_BUILD)
		{
			SearchForMissingItems(WidgetInfo.BANK_ITEM_CONTAINER, InventoryID.BANK);
		}
		else if(scriptPostFired.getScriptId() == COLLECTION_DRAW_LOG_SCRIPT_ID)
		{
			CheckLog();
			clientThread.invokeAtTickEnd(this::UpdateText);
			clientThread.invokeAtTickEnd(this::UpdatePage);
		}
	}

	@Subscribe
	public void onWidgetClosed(WidgetClosed widgetClosed)
	{
		if(widgetClosed.getGroupId() != WidgetID.COLLECTION_LOG_ID)
			return;
		log.debug("collection log closed!");
	}

	@Provides
	SensibleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SensibleConfig.class);
	}

	private void LoadFile()
	{
		// reset collection log
		CollectionLog.COLLECTION_LOG_DATA = null;

		if(accountHash == -1)
		{
			log.warn("Not logged in?");
			return;
		}

		CollectionLog.COLLECTION_LOG_DATA = DataHandler.Deserialize(accountHash, "collection-log");
	}

	private void InitializeCollectionLog()
	{
//		if(CollectionLog.COLLECTION_LOG_DATA != null) {
//			log.debug("log already exists");
//			return;
//		}

		log.debug("creating new log");

		CollectionLog.COLLECTION_LOG_DATA = new HashMap<>();

		final int[] COLLECTION_LOG_TAB_STRUCT_IDS = {471, 472, 473, 474, 475};
		final int COLLECTION_LOG_TAB_ENUM_PARAM_ID = 683;
//		final int COLLECTION_LOG_PAGE_NAME_PARAM_ID = 689;
		final int COLLECTION_LOG_PAGE_ITEMS_ENUM_PARAM_ID = 690;

		for(int structId : COLLECTION_LOG_TAB_STRUCT_IDS)
		{
			StructComposition tabStruct = client.getStructComposition(structId);
			int tabEnumId = tabStruct.getIntValue(COLLECTION_LOG_TAB_ENUM_PARAM_ID);
			EnumComposition tabEnum = client.getEnum(tabEnumId);

			for(int pageStructId : tabEnum.getIntVals())
			{
				StructComposition pageStruct = client.getStructComposition(pageStructId);
//				String pageName = pageStruct.getStringValue(COLLECTION_LOG_PAGE_NAME_PARAM_ID);
				int pageItemsEnumId = pageStruct.getIntValue(COLLECTION_LOG_PAGE_ITEMS_ENUM_PARAM_ID);
				EnumComposition pageItemsEnum = client.getEnum(pageItemsEnumId);

				for(int pageItemId : pageItemsEnum.getIntVals())
				{
					ItemComposition itemComposition = itemManager.getItemComposition(pageItemId);
//					if(blackList.IsPet(itemComposition.getId()) || blackList.IsJar(itemComposition.getId()))
//						CollectionLog.COLLECTION_LOG_DATA.put(itemComposition.getId(), 0);
//					else
						CollectionLog.COLLECTION_LOG_DATA.put(itemComposition.getId(), 1);
				}
			}
		}
	}

	public void CheckLog()
	{
		final int[] COLLECTION_LOG_TAB_STRUCT_IDS = {471, 472, 473, 474, 475};
		final int COLLECTION_LOG_TAB_ENUM_PARAM_ID = 683;
		final int COLLECTION_LOG_PAGE_NAME_PARAM_ID = 689;
		final int COLLECTION_LOG_PAGE_ITEMS_ENUM_PARAM_ID = 690;

		for(int structId : COLLECTION_LOG_TAB_STRUCT_IDS)
		{
			StructComposition tabStruct = client.getStructComposition(structId);
			int tabEnumId = tabStruct.getIntValue(COLLECTION_LOG_TAB_ENUM_PARAM_ID);
			EnumComposition tabEnum = client.getEnum(tabEnumId);

			for(int pageStructId : tabEnum.getIntVals())
			{
				StructComposition pageStruct = client.getStructComposition(pageStructId);
				String pageName = pageStruct.getStringValue(COLLECTION_LOG_PAGE_NAME_PARAM_ID);
				int pageItemsEnumId = pageStruct.getIntValue(COLLECTION_LOG_PAGE_ITEMS_ENUM_PARAM_ID);
				EnumComposition pageItemsEnum = client.getEnum(pageItemsEnumId);

				int needed = 0;
				int obtained = 0;
				boolean validPage = true;
				for(int pageItemId : pageItemsEnum.getIntVals())
				{
					ItemComposition itemComposition = itemManager.getItemComposition(pageItemId);
					int itemId = itemComposition.getId();

					if(CollectionLog.COLLECTION_LOG_DATA.get(itemId) == STATUS_UNKNOWN)
					{
						validPage = false;
						break;
					}

					if(BLACKLIST_PETS && blackList.IsPet(itemId))
					{

					}
					else if(BLACKLIST_JARS && blackList.IsJar(itemId))
					{

					}
					else
					{
						needed++;
						if(CollectionLog.COLLECTION_LOG_DATA.get(itemId) > 0)
							obtained++;
					}
				}

				if(obtained >= needed && validPage)
					pages.put(pageName, 1);
			}
		}
	}

	public void UpdateText()
	{
		int[] tabs = {12, 16, 32, 35, 34};
		Widget logList = null;
		for(int i : tabs)
		{
			logList = client.getWidget(621, i);
			if(logList != null && !logList.isHidden())
				break;
		}

		if(logList == null)
		{
			log.debug("w null");
			return;
		}
		if(logList.getChildren() == null)
		{
			log.debug("children null");
			return;
		}

		Widget[] entries = logList.getChildren();
		for(int i = 0; i < entries.length; i++)
		{
			String pageName = entries[i].getText();
			if(pages.containsKey(pageName) && pages.get(pageName) > 0) {
				entries[i].setTextColor(0x00ff00);
				entries[i].revalidate();
			}
		}
	}

	public void SearchForMissingItems(WidgetInfo widgetInfo, InventoryID id)
	{
		final Widget widget = client.getWidget(widgetInfo);
		final ItemContainer itemContainer = client.getItemContainer(id);

		if(itemContainer == null || widget == null || widget.getChildren() == null)
			return;

		Widget[] children = widget.getChildren();
		for(int i = 0; i < itemContainer.size(); i++)
		{
			Widget child = children[i];
			if(child != null && !child.isSelfHidden() && child.getItemId() > -1)
			{
				int itemId = child.getItemId();

				// item is made from a component that is not in the collection log
				if(blackList.LegacyItems.containsKey(itemId))
					itemId = blackList.LegacyItems.get(itemId);

				// if the item is in the collection log, but it is not counted -> count it
				if(CollectionLog.COLLECTION_LOG_DATA.containsKey(itemId))
				{
					if(CollectionLog.COLLECTION_LOG_DATA.get(itemId) <= child.getItemQuantity())
					{
						CollectionLog.COLLECTION_LOG_DATA.put(itemId, child.getItemQuantity());
					}
				}
			}
		}
	}
}
