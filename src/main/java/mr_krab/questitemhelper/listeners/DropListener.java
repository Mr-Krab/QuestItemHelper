package mr_krab.questitemhelper.listeners;

import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.item.inventory.ItemStack;

import mr_krab.questitemhelper.QuestItemHelper;

public class DropListener {
	
	private QuestItemHelper plugin;
	
	public DropListener(QuestItemHelper plugin) {
		this.plugin = plugin;
	}
	
	@Listener
	public void onPlayerDropItem(DropItemEvent.Pre event, @First Player player) {
		if(!player.hasPermission("questitemhelper.ignore.drop")) {
			event.getDroppedItems().forEach(item -> {
				ItemStack itemStack = item.createStack();
				DisplayNameData nameData = itemStack.getOrCreate(DisplayNameData.class).get();
				String itemName = nameData.displayName().get().toPlain();
				String itemID = itemStack.getType().getId();
				if(!plugin.getRootNode().getNode("Items", "Name", itemName).isVirtual()) {
					if(!plugin.getRootNode().getNode("Items", "Name", itemName, "Drop").getBoolean()) {
						player.sendMessage(plugin.getOrDefaultLocale(player.getLocale()).getString("drop.canceled", itemName));
						event.setCancelled(true);
					}
				} else if(!plugin.getRootNode().getNode("Items", "ID", itemID).isVirtual()) {
					if(!plugin.getRootNode().getNode("Items", "ID", itemID, "Drop").getBoolean()) {
						player.sendMessage(plugin.getOrDefaultLocale(player.getLocale()).getString("drop.canceled", itemID));
						event.setCancelled(true);
					}
				}
			});
		}
	}

}
