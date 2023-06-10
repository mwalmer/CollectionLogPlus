package com.sensible;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
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
	private ItemManager im;

	@Inject
	private SensibleConfig config;

	public static final File MY_DATA = new File(RuneLite.RUNELITE_DIR, "myData");
	private BlackList blackList;

	private final int COLLECTION_DRAW_LOG_SCRIPT_ID = 2732;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
		blackList = new BlackList();

		if(!MY_DATA.exists() && !MY_DATA.mkdir())
		{
			log.error("Could not make directory!");
			shutDown();
			return;
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if(gameStateChanged.getGameState() == GameState.LOGGED_IN)
			LoadFile();
	}

	private void LoadFile()
	{
		// reset collection log
		CollectionLog.COLLECTION_LOG_DATA = null;

		long hash = client.getAccountHash();
		if(hash == -1)
		{
			log.warn("Not logged in?");
			return;
		}

		CollectionLog.COLLECTION_LOG_DATA = null;
		CollectionLog.COLLECTION_LOG_DATA = DataHandler.Deserialize(hash, "collection-log");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded widgetLoaded)
	{
		int widgetID = widgetLoaded.getGroupId();
		if(widgetID != WidgetID.COLLECTION_LOG_ID)
			return;

//		clientThread.invokeLater(() -> {
//			Widget w = client.getWidget(621, 36);
//			if(w == null)
//			{
//				log.debug("w null");
//				return;
//			}
//			if(w.getChildren() == null)
//			{
//				log.debug("children null");
//				return;
//			}
//			log.debug("children size: " + String.valueOf(w.getChildren().length));
//			Widget item = w.getChild(0);
//			item.setHidden(true);
//
//			for(int i = 1; i < w.getChildren().length; i++)
//			{
//				Widget test = w.getChild(i);
//				test.setPos(test.getOriginalX() - test.getWidth(), test.getOriginalY());
//				test.revalidate();
//			}
//		});
		int tab = 471;

		StructComposition sc = client.getStructComposition(tab);
		if(sc == null)
			return;
		log.debug(sc.getStringValue(682));

		int tabEnumId = sc.getIntValue(683);
		EnumComposition tabEnum = client.getEnum(tabEnumId);
		for(Integer psi : tabEnum.getIntVals())
		{
			StructComposition pageStruct = client.getStructComposition(psi);
			String pageName = pageStruct.getStringValue(689);
			int pageItemsEnumId = pageStruct.getIntValue(690);
			EnumComposition pageItemsEnum = client.getEnum(pageItemsEnumId);



			for(Integer pageItemId : pageItemsEnum.getIntVals())
			{
				ItemComposition ic = im.getItemComposition(pageItemId);
			}
		}

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

	@Subscribe
	public void onScriptPostFired(ScriptPostFired scriptPostFired)
	{
		if(scriptPostFired.getScriptId() == ScriptID.COLLECTION_DRAW_LIST)
		{
			clientThread.invokeAtTickEnd(() -> {
				Widget pageEntries = client.getWidget(621, 36);
				if(pageEntries == null)
					return;

				Widget[] entries = pageEntries.getChildren();
				if(entries == null)
					return;

				int needed = 0;
				int obtained = 0;
				for(int i = 0; i < entries.length; i++)
				{
					Widget entry = entries[i];

					if(entry.getItemQuantity() >= 1 && entry.getOpacity() == 0)
						obtained++;
					if(!blackList.IsBlackListed(entry.getItemId()))
						needed++;
				}

				log.debug("needed: " + needed + "\nobtained: " + obtained);
			});
		}
		else if(scriptPostFired.getScriptId() == COLLECTION_DRAW_LOG_SCRIPT_ID)
		{
//			clientThread.invokeLater(() -> {
//				Widget logList = client.getWidget(621, 11);
//				if(logList == null)
//				{
//					log.debug("w null");
//					return;
//				}
//				if(logList.getChildren() == null)
//				{
//					log.debug("children null");
//					return;
//				}
//				log.debug("children size: " + String.valueOf(logList.getChildren().length));
//
//				Widget[] entries = logList.getChildren();
//				for(int i = 0; i < entries.length; i++)
//				{
//					String s = entries[i].getName();
//					entries[i].setName(s.replace("ff9040", "0dc10d"));
//					entries[i].setTextColor(0x0000ff);
//					entries[i].revalidate();
//				}
//			});

			clientThread.invokeAtTickEnd(() -> {
				Widget logList = client.getWidget(621, 12);
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
				log.debug("children size: " + String.valueOf(logList.getChildren().length));

				Widget[] entries = logList.getChildren();
				for(int i = 0; i < entries.length; i++)
				{
					entries[i].setTextColor(0x00ff00);
					entries[i].revalidate();
				}
			});
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
}
