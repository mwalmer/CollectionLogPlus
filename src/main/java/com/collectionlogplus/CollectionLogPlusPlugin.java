package com.collectionlogplus;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.ItemQuantityMode;
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
import java.util.*;

@Slf4j
@PluginDescriptor(
	name = "Collection Log Plus",
	description = "Lets banked items be counted in the collection log",
	tags = {"collection", "log"}
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

	public HashMap<String, PageStatus> pages = new HashMap();
	public static final File COLLECTIONLOGPLUS_DIR = new File(RuneLite.RUNELITE_DIR, "CollectionLogPlus");

	private long accountHash = -1;
	private final int COLLECTION_DRAW_LOG_SCRIPT_ID = 2732;
	private final int STATUS_UNKNOWN = -1;
	private final int STATUS_NOT_OBTAINED = 0;
	private enum PageStatus {
		MISSING,
		NOT_COMPLETED,
		COMPLETED
	};

	public static HashMap<Integer, ItemData> collectionLog = new HashMap<>();
	public static HashMap<Integer, Integer> seenInventories = new HashMap<>();
	public boolean ignorePreFired = false;
	private int[] scriptArgs = new int[3];
	private final int DEFAULT_TEXT_COLOR = 0xff981f;

	@Override
	protected void startUp()
	{
		loadDataFromFile();
	}

	@Override
	protected void shutDown()
	{
		saveDataToFile();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if(gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			accountHash = client.getAccountHash();
			loadDataFromFile();
		}
		else if(gameStateChanged.getGameState() == GameState.HOPPING || gameStateChanged.getGameState() == GameState.LOGIN_SCREEN)
		{
			saveDataToFile();
		}
	}

	public void saveDataToFile()
	{
		if(collectionLog == null)
			return;
		if(accountHash == -1)
		{
			return;
		}

		DataHandler.Serialize(collectionLog, accountHash, "collection-log");
		DataHandler.Serialize(seenInventories, accountHash, "config");
	}

	public void loadDataFromFile()
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
			collectionLog = null;
			return;
		}

		collectionLog = DataHandler.Deserialize(accountHash, "collection-log");
		seenInventories = DataHandler.Deserialize(accountHash, "config");
		if(seenInventories == null)
			seenInventories = new HashMap<>();
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
			initializeCollectionLog();
			clientThread.invokeAtTickEnd(this::UpdateListText);
			clientThread.invokeAtTickEnd(this::UpdatePage);
		}
	}

	@Subscribe
	public void onScriptPreFired(ScriptPreFired scriptPreFired)
	{
		if (ignorePreFired)
		{
			ignorePreFired = false;
		}
		else if(scriptPreFired.getScriptId() == COLLECTION_DRAW_LOG_SCRIPT_ID)
		{
			scriptArgs = Arrays.copyOfRange(client.getIntStack(), 0, 3);
		}
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
			case "includeBanked":
				clientThread.invoke(this::refreshPage);
				clientThread.invoke(this::refreshList);
				break;
			case "removepets":
			case "removejars":
				clientThread.invoke(this::refreshList);
				break;
			default:
				break;
		}
	}

	@Provides
	CollectionLogPlusConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CollectionLogPlusConfig.class);
	}

	public void refreshPage()
	{
		Widget collectionLog = client.getWidget(WidgetInfo.COLLECTION_LOG);
		if(collectionLog == null || collectionLog.isHidden())
			return;

		ignorePreFired = true;
		// tabEnumId, pagestructid, child num
		client.runScript(COLLECTION_DRAW_LOG_SCRIPT_ID, scriptArgs[0], scriptArgs[1], scriptArgs[2]);
	}

	public void refreshList()
	{
		Widget collectionLog = client.getWidget(WidgetInfo.COLLECTION_LOG);
		if(collectionLog == null || collectionLog.isHidden())
			return;

		clientThread.invokeAtTickEnd(this::initializeCollectionLog);
		clientThread.invokeAtTickEnd(this::UpdateListText);
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
						collectionLog.put(itemId, new ItemData(STATUS_UNKNOWN, 0, 0));
						validPage = false;
					}
					else
					{
						boolean isPet = CollectionLogPlusData.PETS.contains(itemId);
						boolean isJar = CollectionLogPlusData.JARS.contains(itemId);
						// blacklists the "All Pets" tab as pets always need to be obtained to complete it
						if((config.petsDisabled() && isPet && !pageName.equals("All Pets")) || (config.jarsDisabled() && isJar))
							continue;

						neededItems++;
						ItemData itemData = collectionLog.get(itemId);

						int itemQuantity = itemData.collectionLogCount + itemData.addedCount;
						if(config.includeBanked())
						{
							itemQuantity = Math.max(itemData.collectionLogCount, itemData.storedCount) + itemData.addedCount;
						}

						if(itemQuantity > 0)
						{
							obtainedItems++;
						}
						else if(itemData.collectionLogCount == -1)
						{
							validPage = false;
						}
					}
				}

				if(validPage)
				{
					if(obtainedItems >= neededItems)
					{
						pages.put(pageName, PageStatus.COMPLETED);
					}
					else
					{
						pages.put(pageName, PageStatus.NOT_COMPLETED);
					}
				}
				else
				{
					pages.put(pageName, PageStatus.MISSING);
				}
			}
		}

	}

	public void UpdateListText()
	{
		// bosses, raids, clues, minigames, other
		int[] lists = {12, 16, 32, 35, 34};
		Widget logList = null;
		for(int listId : lists)
		{
			logList = client.getWidget(WidgetID.COLLECTION_LOG_ID, listId);
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
			PageStatus pageStatus = pages.get(pageName);
			if(pageStatus == null)
			{
				child.setTextColor(DEFAULT_TEXT_COLOR);
			}
			else if (pageStatus == PageStatus.MISSING)
			{
				child.setTextColor(DEFAULT_TEXT_COLOR);
				child.setText(child.getText() + "*");
			}
			else if(pageStatus == PageStatus.NOT_COMPLETED)
			{
				child.setTextColor(DEFAULT_TEXT_COLOR);
			}
			else
			{
				child.setTextColor(config.completedColor().getRGB());
			}

			child.revalidate();
		}
	}

	private void UpdatePage()
	{
		Widget widget = client.getWidget(WidgetID.COLLECTION_LOG_ID, 36);
		if(widget == null || widget.getChildren() == null)
			return;

		int needed = 0;
		int obtained = 0;
		Widget[] children = widget.getChildren();
		for(int i = 0; i < children.length; i++)
		{
			Widget child = widget.getChild(i);
			int itemId = child.getItemId();
			// This makes sure that the widget id equals the collection log id
			if(CollectionLogPlusData.MISSING_WIDGET_IDS.containsKey(itemId))
				itemId = CollectionLogPlusData.MISSING_WIDGET_IDS.get(itemId);

			if(collectionLog.containsKey(itemId))
			{
				ItemData itemData = collectionLog.get(itemId);

				// updates the collection log count, placeholder items in widgets have an item quantity of 1 so they
				// have to be set to zero manually
				itemData.collectionLogCount = 0;
				if(child.getItemQuantityMode() != 0 && child.getOpacity() == 0)
				{
					itemData.collectionLogCount = child.getItemQuantity();
				}
				collectionLog.put(itemId, itemData);

				int itemQuantity = itemData.collectionLogCount + itemData.addedCount;
				if(config.includeBanked())
				{
					itemQuantity = Math.max(itemData.collectionLogCount, itemData.storedCount) + itemData.addedCount;
				}

				// blacklist's the 'All Pets' tab because pets should still show up there even if hidden;
				int ENTRY_HEADER = 19;
				Widget header = client.getWidget(WidgetID.COLLECTION_LOG_ID, ENTRY_HEADER);
				boolean isAllPetsTab = header != null && header.getChildren() != null && header.getChildren()[0].getText().equals("All Pets");

				if((config.hidePets() && CollectionLogPlusData.PETS.contains(itemId) && !isAllPetsTab) ||
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
					obtained++;
					needed++;
					child.setOpacity(0);
					child.setItemQuantity(itemQuantity);
					child.setItemQuantityMode(ItemQuantityMode.STACKABLE);
				}
				else
				{
					needed++;
				}

			}

			child.revalidate();
		}

		if(obtained >= needed)
		{
			Widget header = client.getWidget(WidgetInfo.COLLECTION_LOG_ENTRY_HEADER);
			if(header == null || header.getChildren() == null)
				return;
			Widget obtainedText = header.getChildren()[1];
			obtainedText.setText("Obtained: <col=" + Integer.toHexString(config.completedColor().getRGB() - 0xff000000) + ">"+ obtained + "/" + needed+"</col>");
			obtainedText.setOriginalWidth(obtainedText.getWidth() + 6);
			obtainedText.revalidate();
		}

	}

	public void SearchForMissingItems(WidgetInfo widgetInfo, InventoryID id)
	{
		if(!config.includeBanked() || seenInventories.containsKey(id.getId()))
			return;

		if(collectionLog == null)
			return;

		final Widget widget = client.getWidget(widgetInfo);
		final ItemContainer itemContainer = client.getItemContainer(id);

		if(itemContainer == null || widget == null || widget.getChildren() == null)
			return;

		seenInventories.put(id.getId(), 1);

		Widget[] children = widget.getChildren();
		for(int i = 0; i < itemContainer.size(); i++)
		{
			Widget child = children[i];
			if(child != null && !child.isSelfHidden() && child.getItemId() > -1)
			{
				int itemId = child.getItemId();

				// item is made from a component that is not in the collection log
				if(CollectionLogPlusData.FUSED.containsKey(itemId))
				{
//					log.debug("contains key");
//					log.debug(child.getName());
//					log.debug("" + child.getItemQuantity());
					Integer[] components = CollectionLogPlusData.FUSED.get(itemId);
					for(Integer component : components)
					{
						// if the item is in the collection log, but it is not counted -> count it
						if(collectionLog.containsKey(component))
						{
							ItemData itemData = collectionLog.get(component);
							itemData.storedCount += child.getItemQuantity();
							collectionLog.put(component, itemData);
						}
					}
				}
				else if(collectionLog.containsKey(itemId))
				{
					ItemData itemData = collectionLog.get(itemId);
					if(child.getOpacity() == 0)
					{
						itemData.storedCount += child.getItemQuantity();
						collectionLog.put(itemId, itemData);
					}
				}
			}
		}
	}
}
