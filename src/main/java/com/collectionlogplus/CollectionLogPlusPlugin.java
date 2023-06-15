package com.collectionlogplus;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@PluginDescriptor(
	name = "Collection Log Plus"
)
public class CollectionLogPlusPlugin extends Plugin
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
	private CollectionLogPlusConfig config;

	public HashSet<String> pages = new HashSet<>();
	public static final File COLLECTIONLOGPLUS_DIR = new File(RuneLite.RUNELITE_DIR, "CollectionLogPlus");

	private long accountHash = -1;
	private final int COLLECTION_DRAW_LOG_SCRIPT_ID = 2732;
	private final int STATUS_UNKNOWN = -1;
	private final int STATUS_NOT_OBTAINED = 0;
	private boolean BLACKLIST_PETS = true;
	private boolean BLACKLIST_JARS = true;
	private boolean BLACKLIST_RARES = true;
	public static HashMap<Integer, Integer> collectionLog = new HashMap<>();
	public boolean ignorePreFired = false;

	@Override
	protected void startUp()
	{
		loadCollectionLogFromFile();
	}

	@Override
	protected void shutDown()
	{
		saveCollectionLogToFile();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if(gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			accountHash = client.getAccountHash();
			loadCollectionLogFromFile();
		}
		else if(gameStateChanged.getGameState() == GameState.HOPPING || gameStateChanged.getGameState() == GameState.LOGIN_SCREEN)
		{
			saveCollectionLogToFile();
			//todo: does account hash need to be set to -1 here? does hopping trigger logged_in event?
		}
	}

	public void saveCollectionLogToFile()
	{
		if(collectionLog == null)
			return;
		if(accountHash == -1)
			return;

		DataHandler.Serialize(collectionLog, accountHash, "collection-log");
	}

	public void loadCollectionLogFromFile()
	{
		if(client.getGameState() == GameState.LOGGED_IN)
		{
			accountHash = client.getAccountHash();
		}
		else
		{
			return;
		}

		if(accountHash == -1)
		{
			log.debug("something went wrong, you need to login!");
			collectionLog = null;
			return;
		}

		collectionLog = DataHandler.Deserialize(accountHash, "collection-log");
	}

//	@Subscribe
//	public void onWidgetLoaded(WidgetLoaded widgetLoaded)
//	{
//		int widgetID = widgetLoaded.getGroupId();
//		if(widgetID != WidgetID.COLLECTION_LOG_ID)
//			return;
//
//		initializeCollectionLog();
//	}

	@Subscribe
	public void onScriptPostFired(ScriptPostFired scriptPostFired)
	{
		if(scriptPostFired.getScriptId() == ScriptID.BANKMAIN_BUILD)
		{
			SearchForMissingItems(WidgetInfo.BANK_ITEM_CONTAINER, InventoryID.BANK);
		}
		else if(scriptPostFired.getScriptId() == COLLECTION_DRAW_LOG_SCRIPT_ID)
		{
			initializeCollectionLog();
			clientThread.invokeAtTickEnd(this::UpdateText);
			clientThread.invokeAtTickEnd(this::UpdatePage);
		}
	}
	int[] pageStack;
	int[] listStack;
	@Subscribe
	public void onScriptPreFired(ScriptPreFired scriptPreFired)
	{
		if (ignorePreFired)
		{
			ignorePreFired = false;
			return;
		}
		if(scriptPreFired.getScriptId() == COLLECTION_DRAW_LOG_SCRIPT_ID)
		{
			log.debug("fired!!!!1");
			pageStack = client.getIntStack().clone();
			for (int i = 0; i < 3; i++)
				log.debug(String.valueOf(pageStack[i]) + ", ");
		}
//		} else if (scriptPreFired.getScriptId() == 2389) {
//			listStack = client.getIntStack().clone();
//			for(int i = 0; i < 3; i++)
//				log.debug(String.valueOf(listStack[i]) + ", ");
//		}
	}

	@Subscribe
	public void onWidgetClosed(WidgetClosed widgetClosed)
	{
		if(widgetClosed.getGroupId() != WidgetID.COLLECTION_LOG_ID)
			return;
		log.debug("collection log closed!");
	}

	@Provides
	CollectionLogPlusConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CollectionLogPlusConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged configChanged)
	{
		switch (configChanged.getKey())
		{
			case "hidepets":
			case "hidejars":
				clientThread.invoke(this::refreshPage);
				break;
			case "removepets":
			case "removejars":
			case "completedColor":
			default:
//				clientThread.invoke(this::refreshPage);
				clientThread.invoke(this::refreshList);
				break;
		}

	}

	public void refreshPage()
	{

		Widget collectionLog = client.getWidget(621, 0);
		if(collectionLog == null || collectionLog.isHidden())
			return;

		ignorePreFired = true;
//		tabEnumId, pagestructid, child num
		client.runScript(COLLECTION_DRAW_LOG_SCRIPT_ID, pageStack[0], pageStack[1], pageStack[2]);
//		initializeCollectionLog();
//		UpdatePage();
	}

	public void refreshList()
	{

		Widget collectionLog = client.getWidget(621, 0);
		if(collectionLog == null || collectionLog.isHidden())
			return;

		ignorePreFired = true;
//		tabEnumId, pagestructid, child num
//		client.runScript(2389, listStack[0]);
//
		clientThread.invokeAtTickEnd(this::initializeCollectionLog);
		clientThread.invokeAtTickEnd(this::UpdateText);
	}

	private void initializeCollectionLog()
	{
		if(collectionLog == null) {
			collectionLog = new HashMap<>();
		}

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

				int neededItems = 0;
				int obtainedItems = 0;
				boolean validPage = true;
				for(int pageItemId : pageItemsEnum.getIntVals())
				{
					ItemComposition itemComposition = itemManager.getItemComposition(pageItemId);
					int itemId = itemComposition.getId();
					if(!collectionLog.containsKey(itemId))
					{
						collectionLog.put(itemId, STATUS_UNKNOWN);
						validPage = false;
					}
					else
					{
						// blacklists the "All Pets" tab as pets always need to be obtained to complete it
						if(config.petsDisabled() && CollectionLogPlusData.PETS.contains(itemId) && !pageName.equals("All Pets"))
						{

						}
						else if(config.jarsDisabled() && CollectionLogPlusData.JARS.contains(itemId))
						{

						}
						else
						{
							neededItems++;
							if(collectionLog.get(itemId) > 0)
							{
								obtainedItems++;
							}
						}
					}
				}

				if(validPage && obtainedItems >= neededItems)
				{
					pages.add(pageName);
				}
				else
				{
					pages.remove(pageName);
				}
			}
		}

	}

	public void UpdateText()
	{
		int[] tabs = {12, 16, 32, 35, 34};
		Widget logList = null;
		for(int tabId : tabs)
		{
			logList = client.getWidget(621, tabId);
			if(logList != null && !logList.isHidden())
				break;
		}

		if(logList == null || logList.getChildren() == null)
			return;

		Widget[] children = logList.getChildren();
		for(int i = 0; i < children.length; i++)
		{
			Widget child = children[i];
			String pageName = child.getText();
			if(pages.contains(pageName))
			{
				child.setTextColor(config.completedColor().getRGB());
			}
			else
			{
				child.setTextColor(0xff981f);
			}
			child.revalidate();
		}
	}

	private void UpdatePage()
	{
		Widget widget = client.getWidget(621, 36);
		if(widget == null || widget.getChildren() == null)
			return;

		// blacklist's the 'All Pets' tab because pets should still show up there even if hidden;
		Widget header = client.getWidget(621, 19);
		if(header != null && header.getChildren() != null && header.getChildren()[0].getText().equals("All Pets"))
			return;

		Widget[] children = widget.getChildren();
		for(int i = 0; i < children.length; i++)
		{
			Widget child = widget.getChild(i);
			int itemId = child.getItemId();
			if(CollectionLogPlusData.MISSING_WIDGET_IDS.containsKey(itemId))
				itemId = CollectionLogPlusData.MISSING_WIDGET_IDS.get(itemId);

			if(collectionLog.containsKey(itemId))
			{
				int itemQuantity = collectionLog.get(itemId);
				if(itemQuantity < child.getItemQuantity() && child.getOpacity() == 0)
				{
					collectionLog.put(itemId, child.getItemQuantity());
					itemQuantity = child.getItemQuantity();
				}

				if((config.hidePets() && CollectionLogPlusData.PETS.contains(itemId)) ||
				   (config.hideJars() && CollectionLogPlusData.JARS.contains(itemId)))
				{
					if(child.isHidden())
						continue;
					child.setHidden(true);

					// shift items over to fill space left by hidden item
					for(int j = children.length - 1; j != i; j--)
					{
						Widget previousChild = children[j - 1];
						children[j].setPos(previousChild.getRelativeX(), previousChild.getRelativeY());
						children[j].revalidate();
					}
				}
				else if(itemQuantity > 0)
				{
					child.setOpacity(0);
					child.setItemQuantity(itemQuantity);
					//TODO: figure out difference between quantity 2 and 1 and 0
					child.setItemQuantityMode(2);
				}

			}

			child.revalidate();
		}
	}

	public void SearchForMissingItems(WidgetInfo widgetInfo, InventoryID id)
	{
		//TODO: maybe init cl/fused here?
		if(collectionLog == null)
			return;

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
				if(CollectionLogPlusData.FUSED.containsKey(itemId))
					itemId = CollectionLogPlusData.FUSED.get(itemId);

				// if the item is in the collection log, but it is not counted -> count it
				if(collectionLog.containsKey(itemId))
				{
					if(collectionLog.get(itemId) <= child.getItemQuantity())
					{
						collectionLog.put(itemId, child.getItemQuantity());
					}
				}
			}
		}
	}
}
