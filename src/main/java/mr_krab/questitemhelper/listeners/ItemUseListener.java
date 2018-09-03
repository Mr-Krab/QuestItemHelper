package mr_krab.questitemhelper.listeners;

import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.item.inventory.ItemStack;

import mr_krab.questitemhelper.QuestItemHelper;

public class ItemUseListener {
	
	private QuestItemHelper plugin;
	
	public ItemUseListener(QuestItemHelper plugin) {
		this.plugin = plugin;
	}

	@Listener
	public void onPlayerUseItem(UseItemStackEvent.Start event, @First Player player) {
		if(!player.hasPermission("questitemhelper.ignore.use")) {
			ItemStack itemStack = event.getItemStackInUse().createStack();
			DisplayNameData nameData = itemStack.getOrCreate(DisplayNameData.class).get();
			String itemName = nameData.displayName().get().toPlain();
			String itemID = itemStack.getType().getId();
			if(!plugin.getRootNode().getNode("Items", "Name", itemName).isVirtual()) {
				if(!plugin.getRootNode().getNode("Items", "Name", itemName, "Use").getBoolean()) {
					player.sendMessage(plugin.getLocale().getString("use.canceled", itemName));
					event.setCancelled(true);
				}
			} else if(!plugin.getRootNode().getNode("Items", "ID", itemID).isVirtual()) {
				if(!plugin.getRootNode().getNode("Items", "ID", itemID, "Use").getBoolean()) {
					player.sendMessage(plugin.getLocale().getString("use.canceled", itemID));
					event.setCancelled(true);
				}
			}
		}
	}
}
