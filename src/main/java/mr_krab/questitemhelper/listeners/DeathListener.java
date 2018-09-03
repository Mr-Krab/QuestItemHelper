package mr_krab.questitemhelper.listeners;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.scheduler.Task;

import mr_krab.questitemhelper.QuestItemHelper;

public class DeathListener {
	
	private QuestItemHelper plugin;
	
	public DeathListener(QuestItemHelper plugin) {
		this.plugin = plugin;
	}
	
	@Listener
	public void onPlayerDeath(DestructEntityEvent event, @Getter("getTargetEntity") Player player) {
		Inventory inventory = player.getInventory();
	    Iterable<Slot> slots = inventory.slots();
	    for(Slot slot : slots) {
        	if(slot.totalItems() != 0) {
        		ItemStack itemStack = slot.peek().get();
        		DisplayNameData nameData = itemStack.getOrCreate(DisplayNameData.class).get();
        		String itemName = nameData.displayName().get().toPlain();
        		String itemID = slot.peek().get().getType().getId();
        		if(!plugin.getRootNode().getNode("Items", "Name", itemName).isVirtual()) {
        			if(plugin.getRootNode().getNode("Items", "Name", itemName, "SaveOnDeath").getBoolean()) {
        				slot.clear();
        				Task.builder().delay(5, TimeUnit.MILLISECONDS).execute(() -> {
        					slot.offer(itemStack);
        					player.sendMessage(plugin.getLocale().getString("save.on.death", itemName));
        				}).submit(plugin);
        			}
        		} else if(!plugin.getRootNode().getNode("Items", "ID", itemID).isVirtual()) {
        			if(plugin.getRootNode().getNode("Items", "ID", itemID, "SaveOnDeath").getBoolean()) {
        				slot.clear();
        				Task.builder().delay(5, TimeUnit.MILLISECONDS).execute(() -> {
        					slot.offer(itemStack);
        					player.sendMessage(plugin.getLocale().getString("save.on.death", itemID));
        				}).submit(plugin);
        			}
        		}
        	}
	    }
	}
}
