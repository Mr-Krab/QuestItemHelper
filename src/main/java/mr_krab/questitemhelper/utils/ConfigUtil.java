package mr_krab.questitemhelper.utils;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.util.TypeTokens;

import mr_krab.questitemhelper.QuestItemHelper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ConfigUtil {
	
	private final static QuestItemHelper plugin = QuestItemHelper.getInstance();

	public static void saveConfig() throws ObjectMappingException, IOException {
		File config = new File(plugin.getConfigFile() + File.separator + "config.yml");
		if (!config.exists()) {
			plugin.getRootNode().getNode("Debug").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "Name", "TestItem", "SaveOnDeath").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "Name", "TestItem", "Use").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "Name", "TestItem", "Drop").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "Name", "TestItem", "PutToContainers").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "Name", "TestItem", "PickUpIfExists").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "Name", "TestItem", "MaxStack").setValue(TypeTokens.INTEGER_TOKEN, 1);
			
			plugin.getRootNode().getNode("Items", "ID", "minecraft:barrier", "SaveOnDeath").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "ID", "minecraft:barrier", "Use").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "ID", "minecraft:barrier", "Drop").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "ID", "minecraft:barrier", "PutToContainers").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "ID", "minecraft:barrier", "PickUpIfExists").setValue(TypeTokens.BOOLEAN_TOKEN, false);
			plugin.getRootNode().getNode("Items", "ID", "minecraft:barrier", "MaxStack").setValue(TypeTokens.INTEGER_TOKEN, 1);
			plugin.getConfigLoader().save(plugin.getRootNode());
		}
	}
}
