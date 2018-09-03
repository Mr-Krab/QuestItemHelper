package mr_krab.questitemhelper.listeners;

import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;

import mr_krab.questitemhelper.QuestItemHelper;

public class ClickInventoryEventListener {
	
	private QuestItemHelper plugin;
	
	public ClickInventoryEventListener(QuestItemHelper plugin) {
		this.plugin = plugin;
	}

	@Listener
	public void onPlayerInventoryClick(ClickInventoryEvent.Primary event, @First Player player) {
		if(!player.hasPermission("questitemhelper.ignore.container")) {
			for(SlotTransaction transaction : event.getTransactions()) {
				Slot slot = transaction.getSlot();
				if(slot.totalItems() != 0) {
					ItemStack itemStack = slot.peek().get();
					DisplayNameData nameData = itemStack.getOrCreate(DisplayNameData.class).get();
					String itemName = nameData.displayName().get().toPlain();
					String itemID = slot.peek().get().getType().getId();
        			if(!plugin.getRootNode().getNode("Items", "Name", itemName).isVirtual()) {
        				if(!plugin.getRootNode().getNode("Items", "Name", itemName, "PutToContainers").getBoolean()) {
        					if(event.getTargetInventory() instanceof Container) {
        						Container container = event.getTargetInventory();
        							if(container.isViewedSlot(transaction.getSlot().transform())) {
        								player.sendMessage(plugin.getLocale().getString("container.click.canceled", itemName));
        								event.setCancelled(true);
        							}
        					}
        				}
        			} else if(!plugin.getRootNode().getNode("Items", "ID", itemID).isVirtual()) {
        				if(!plugin.getRootNode().getNode("Items", "ID", itemID, "PutToContainers").getBoolean()) {
        					if(event.getTargetInventory() instanceof Container) {
        						Container container = event.getTargetInventory();
        						if(container.isViewedSlot(transaction.getSlot().transform())) {
        							player.sendMessage(plugin.getLocale().getString("container.click.canceled", itemID));
        							event.setCancelled(true);
        						}
        					}
        				}
        			}
        		}
        	}
	    };
	}

	@Listener
	public void onPlayerInventoryClick(ClickInventoryEvent.Secondary event, @First Player player) {
		if(!player.hasPermission("questitemhelper.ignore.container")) {
			for(SlotTransaction transaction : event.getTransactions()) {
				Slot slot = transaction.getSlot();
				if(slot.totalItems() != 0) {
					ItemStack itemStack = slot.peek().get();
					DisplayNameData nameData = itemStack.getOrCreate(DisplayNameData.class).get();
					String itemName = nameData.displayName().get().toPlain();
					String itemID = slot.peek().get().getType().getId();
        			if(!plugin.getRootNode().getNode("Items", "Name", itemName).isVirtual()) {
        				if(!plugin.getRootNode().getNode("Items", "Name", itemName, "PutToContainers").getBoolean()) {
        					if(event.getTargetInventory() instanceof Container) {
        						Container container = event.getTargetInventory();
        							if(container.isViewedSlot(transaction.getSlot().transform())) {
        								player.sendMessage(plugin.getLocale().getString("container.click.canceled", itemName));
        								event.setCancelled(true);
        							}
        					}
        				}
        			} else if(!plugin.getRootNode().getNode("Items", "ID", itemID).isVirtual()) {
        				if(!plugin.getRootNode().getNode("Items", "ID", itemID, "PutToContainers").getBoolean()) {
        					if(event.getTargetInventory() instanceof Container) {
        						Container container = event.getTargetInventory();
        						if(container.isViewedSlot(transaction.getSlot().transform())) {
        							player.sendMessage(plugin.getLocale().getString("container.click.canceled", itemID));
        							event.setCancelled(true);
        						}
        					}
        				}
        			}
        		}
        	}
	    };
	}
}
