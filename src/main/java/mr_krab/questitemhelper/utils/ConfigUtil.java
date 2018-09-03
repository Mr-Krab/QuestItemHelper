package mr_krab.questitemhelper.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;

import mr_krab.questitemhelper.QuestItemHelper;

public class ConfigUtil {
	
	private final static QuestItemHelper plugin = QuestItemHelper.getInstance();

	public ConfigUtil() {
		this.saveConfig();
	}

	public void saveConfig() {
		String path = "/assets/" + "questitemhelper" + "/";
		File config = new File(plugin.getConfigFile() + File.separator + "config.yml");
		if (!config.exists()) {
        	try {
        		plugin.getLogger().info("Save config");
        		URI u = getClass().getResource(path + "config.yml").toURI();
				FileSystem jarFS = FileSystems.newFileSystem(URI.create(u.toString().split("!")[0]), new HashMap<>());
				Files.copy(jarFS.getPath(u.toString().split("!")[1]), new File(plugin.getConfigFile() + File.separator + "config.yml").toPath());
				jarFS.close();
        	} catch (IOException ex) {
        		plugin.getLogger().error("Failed to save config");
        		ex.printStackTrace();
        	} catch (URISyntaxException e) {
        		plugin.getLogger().error("Failed to save config");
				e.printStackTrace();
			}
		}
	}

  	// Check the configuration version.
	public void checkConfigVersion() {
		String path = "/assets/" + "questitemhelper" + "/";
		File oldConfig = new File(plugin.getConfigDir() + File.separator + "config.yml");
		File renamedConfig = new File(plugin.getConfigDir() + File.separator + "ConfigOld.yml");
		if (!plugin.getRootNode().getNode("Config-Version").isVirtual()) {
			// This part can be supplemented.
			if (plugin.getRootNode().getNode("Config-Version").getInt() != 1) {
				plugin.getLogger().warn("Attention!!! The version of your configuration file does not match the current one!");
				if(oldConfig.exists()) {
					oldConfig.renameTo(renamedConfig);
				}
				plugin.getLogger().warn("Your config has been replaced with the default config. Old config see in the file ConfigOld.yml.");
	        	try {
	        		URI u = getClass().getResource(path + "config.conf").toURI();
					FileSystem jarFS = FileSystems.newFileSystem(URI.create(u.toString().split("!")[0]), new HashMap<>());
					Files.copy(jarFS.getPath(u.toString().split("!")[1]), new File(plugin.getConfigFile() + File.separator + "config.yml").toPath());
					jarFS.close();
	        	} catch (IOException ex) {
	        		ex.printStackTrace();
	        	} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		} else {
			// Action in the absence of the configuration version.
			plugin.getLogger().warn("Attention!!! The version of your configuration file does not match the current one!");
			if(oldConfig.exists()) {
				oldConfig.renameTo(renamedConfig);
			}
			plugin.getLogger().warn("Your config has been replaced with the default config. Old config see in the file ConfigOld.yml.");
        	try {
        		URI u = getClass().getResource(path + "config.yml").toURI();
				FileSystem jarFS = FileSystems.newFileSystem(URI.create(u.toString().split("!")[0]), new HashMap<>());
				Files.copy(jarFS.getPath(u.toString().split("!")[1]), new File(plugin.getConfigFile() + File.separator + "config.yml").toPath());
				jarFS.close();
        	} catch (IOException ex) {
        		ex.printStackTrace();
        	} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void checkLocaleVersion() {
		String path = "/assets/" + "questitemhelper" + "/";
		File saved = new File(plugin.getConfigDir() + File.separator + "default-lang.properties");
		int fromLocaleConfig = Integer.valueOf(plugin.getLocale().getString("version").toPlain().toString().trim());
		if (fromLocaleConfig != 1) {
			plugin.getLogger().info(plugin.getLocale().getString("warning").toPlain());
        	try {
        		URI u = getClass().getResource(path + "en_US.properties").toURI();
        		FileSystem jarFS = FileSystems.newFileSystem(URI.create(u.toString().split("!")[0]), new HashMap<>());
        		if(saved.exists()) {
        			saved.delete();
        		}
        		Files.copy(jarFS.getPath(u.toString().split("!")[1]), saved.toPath());
        		jarFS.close();
        	} catch (IOException ex) {
        		plugin.getLogger().error("Failed to save \"" + "en_US.properties\"" + ex);
        	} catch (URISyntaxException e) {
        		plugin.getLogger().error("Locale \"{0}\" does not exists!\"" + "en_US.properties" + e);
        	}
		}
	}
}
