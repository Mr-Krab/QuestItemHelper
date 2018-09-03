package mr_krab.questitemhelper.listeners;

import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;

import mr_krab.questitemhelper.QuestItemHelper;

public class PickUpListener {
	
	private QuestItemHelper plugin;
	
	public PickUpListener(QuestItemHelper plugin) {
		this.plugin = plugin;
	}

	@Listener
	public void onPlayerPickUpItem(ChangeInventoryEvent.Pickup.Pre event, @Root Player player) {
		if(!player.hasPermission("questitemhelper.ignore.pickup")) {
			event.getFinal().forEach(item -> {
				ItemStack itemStack = item.createStack();
				DisplayNameData nameData = itemStack.getOrCreate(DisplayNameData.class).get();
				String itemName = nameData.displayName().get().toPlain();
				String itemID = itemStack.getType().getId();
				if(!plugin.getRootNode().getNode("Items", "Name", itemName).isVirtual()) {
					int max = plugin.getRootNode().getNode("Items", "Name", itemName, "MaxStack").getInt();
					if(!plugin.getRootNode().getNode("Items", "Name", itemName, "PickUpIfExists").getBoolean()) {
						if(player.getInventory().contains(itemStack) && player.getInventory().query(QueryOperationTypes.ITEM_STACK_IGNORE_QUANTITY.of(itemStack)).totalItems() >= max) {
							player.sendMessage(plugin.getLocale().getString("pickup.canceled", itemName));
							event.setCancelled(true);
						}
					}
				} else if(!plugin.getRootNode().getNode("Items", "ID", itemID).isVirtual()) {
					int max = plugin.getRootNode().getNode("Items", "ID", itemID, "MaxStack").getInt();
					if(!plugin.getRootNode().getNode("Items", "ID", itemID, "PickUpIfExists").getBoolean()) {
						if(player.getInventory().contains(itemStack) && player.getInventory().query(QueryOperationTypes.ITEM_STACK_IGNORE_QUANTITY.of(itemStack)).totalItems() >= max) {
							player.sendMessage(plugin.getLocale().getString("pickup.canceled", itemID));
							event.setCancelled(true);
						}
					}
				}
			});
		}
	}
}
