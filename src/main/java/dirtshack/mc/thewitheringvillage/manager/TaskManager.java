package dirtshack.mc.thewitheringvillage.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import dirtshack.mc.thewitheringvillage.manager.DeliveryTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TaskManager implements Listener {
    private final WaypointManager waypointManager;
    private final Map<Location, DeliveryTask> tasksByChest = new HashMap<>();
    public TaskManager(final JavaPlugin plugin, WaypointManager waypointManager) {
        this.waypointManager = waypointManager;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public void assign(Player player, String taskName, Chest chest, Material material, int amount){
        Location location = chest.getLocation().getBlock().getLocation();
        tasksByChest.put(location, new DeliveryTask(player.getUniqueId(), location, material, amount));

        waypointManager.remove(player);
        waypointManager.show(taskName, player, location, material.name() + ": 0/" + amount);
    }

    @EventHandler
    public void onChestClose(InventoryCloseEvent event){
        if (!(event.getPlayer() instanceof Player player)){
            return;
        }

        if (!(event.getInventory().getHolder() instanceof Chest chest)){
            return;
        }

        Location location = chest.getLocation().getBlock().getLocation();
        DeliveryTask task = tasksByChest.get(location);

        if (task == null || !task.playerId().equals(player.getUniqueId())){
            return;
        }

        for (ItemStack item : chest.getInventory().getContents()){
            if (item == null){
                continue;
            }
            if (item.getType() == task.requiredMaterial()){
                if(item.getAmount() >= task.requirement()){

                }
            }
        }
    }

}
