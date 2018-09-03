/*
 * QuestItemHelper - Plugin for additional customization of quest items.
 * Copyright (C) 2018 Mr_Krab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuestItemHelper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
 
package mr_krab.questitemhelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.Dependency;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;

import com.google.inject.Inject;

import mr_krab.questitemhelper.listeners.ClickInventoryEventListener;
import mr_krab.questitemhelper.listeners.DeathListener;
import mr_krab.questitemhelper.listeners.DropListener;
import mr_krab.questitemhelper.listeners.ItemUseListener;
import mr_krab.questitemhelper.listeners.PickUpListener;
import mr_krab.questitemhelper.utils.ConfigUtil;
import mr_krab.questitemhelper.utils.LocaleUtil;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

@Plugin(id = "questitemhelper",
		name = "QuestItemHelper",
		version = "1.0",
		authors = "Mr_Krab",
		dependencies = {
		@Dependency(id = "spongeapi", version = "spongeapi@7.1.0-SNAPSHOT")
	})
public class QuestItemHelper {
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private Path defaultConfig;
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;
	@Inject
	@ConfigDir(sharedRoot = false)
	private File configFile;
	private ConfigurationNode rootNode;
	@Inject
	private Logger logger;
	private YAMLConfigurationLoader configLoader;

	private static QuestItemHelper instance;
	private static ConfigUtil configUtil;
	private static LocaleUtil localeUtil;
	
	boolean debug;

	public Path getConfigDir() {
		return configDir;
	}
	public File getConfigFile() {
		return configFile;
	}
	public ConfigurationNode getRootNode() {
		return rootNode;
	}
	public Logger getLogger() {
		return logger;
	}
	public static QuestItemHelper getInstance() {
		return instance;
	}
	public LocaleUtil getLocale() {
		return localeUtil;
	}

	public void load() {
		configLoader = YAMLConfigurationLoader.builder().setPath(configDir.resolve("config.yml")).setFlowStyle(FlowStyle.BLOCK).build();
		try {
			rootNode = configLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    localeUtil = new LocaleUtil(this, String.valueOf(rootNode.getNode("Lang").getValue()));
	    try {
			localeUtil.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Listener
	public void onPreInitialization(GamePreInitializationEvent event) throws IOException {
		logger = (Logger)LoggerFactory.getLogger("\033[33mQuestItemHelper\033[0m");
		instance = this;
		configUtil = new ConfigUtil();
		load();
		configUtil.checkConfigVersion();
		Sponge.getEventManager().registerListeners(this, new DeathListener(this));
		Sponge.getEventManager().registerListeners(this, new DropListener(this));
		Sponge.getEventManager().registerListeners(this, new PickUpListener(this));
		Sponge.getEventManager().registerListeners(this, new ItemUseListener(this));
		Sponge.getEventManager().registerListeners(this, new ClickInventoryEventListener(this));
	}
	
	@Listener
	public void onReload(GameReloadEvent event) {
		load();
	}


}