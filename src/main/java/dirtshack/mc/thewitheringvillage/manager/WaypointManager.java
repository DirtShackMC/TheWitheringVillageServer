package dirtshack.mc.thewitheringvillage.manager;

import dirtshack.mc.thewitheringvillage.manager.WaypointDisplay;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Matrix4f;

import java.util.*;

public final class WaypointManager implements  Listener {

    private final JavaPlugin plugin;
    private final Map<UUID, Map<String, WaypointDisplay>> markers = new HashMap<>();

    public WaypointManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void show(String Name, Player player, Location target, String WPText){
        World world = Objects.requireNonNull(target.getWorld());
        Location markerLocation = target.clone().add(0, 2.5, 0);

        TextDisplay markerText = world.spawn(
                markerLocation,
                TextDisplay.class,
                display -> {
                    display.text(Component.text(WPText));
                    display.setBillboard(Display.Billboard.CENTER);
                    display.setSeeThrough(true);
                    display.setBrightness(new Display.Brightness(15, 15));
                    display.setShadowed(true);
                    display.setDefaultBackground(true);
                    display.setBackgroundColor(Color.fromARGB(0, 0, 0, 125));
                    display.setPersistent(false);
                    display.setTransformationMatrix(new Matrix4f().scale(5.0f));
                    display.setTextOpacity((byte) 255);

                    display.setVisibleByDefault(false);
                }
        );

        if(!WPText.isEmpty()){
            markerLocation = target.clone().add(0, 3, 0);

            player.showEntity(plugin, markerText);

        }

        TextDisplay marker = world.spawn(
                markerLocation,
                TextDisplay.class,
                display -> {
                    display.text(Component.text("◆", NamedTextColor.GREEN));
                    display.setBillboard(Display.Billboard.CENTER);
                    display.setSeeThrough(true);
                    display.setBrightness(new Display.Brightness(15, 15));
                    display.setShadowed(false);
                    display.setDefaultBackground(false);
                    display.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
                    display.setPersistent(false);
                    display.setTransformationMatrix(new Matrix4f().scale(10.0F));
                    display.setTextOpacity((byte) 255);

                    display.setVisibleByDefault(false);
                }
        );

        TextDisplay markerOuter = world.spawn(
                markerLocation,
                TextDisplay.class,
                display -> {
                    display.text(Component.text("◇", NamedTextColor.GOLD));
                    display.setBillboard(Display.Billboard.CENTER);
                    display.setSeeThrough(true);
                    display.setBrightness(new Display.Brightness(15, 15));
                    display.setShadowed(false);
                    display.setDefaultBackground(false);
                    display.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
                    display.setPersistent(false);
                    display.setTransformationMatrix(new Matrix4f().scale(10.0F));
                    display.setTextOpacity((byte) 255);

                    display.setVisibleByDefault(false);
                }
        );
        player.showEntity(plugin, marker);
        player.showEntity(plugin, markerOuter);

        markers.computeIfAbsent(player.getUniqueId(), ignored -> new HashMap<>()
        ).put(
                normalizeName(Name),
                new WaypointDisplay(
                        Name,
                        marker,
                        markerOuter,
                        markerText)
        );
    }

    public WaypointDisplay get(Player player, String name){
        Map<String, WaypointDisplay> playerMarkers = markers.get(player.getUniqueId());

        if (playerMarkers == null){
            return null;
        }
        return playerMarkers.get(name);
    }

    public boolean setText(Player player, String name, String WPText){
        WaypointDisplay waypoint = get(player, name);



    }

    public void remove (Player player){
        WaypointDisplay marker = markers.remove(player.getUniqueId());

        if (marker != null && marker.marker() != null && marker.outer() != null && marker.text() != null){
            marker.marker().remove();
            marker.outer().remove();
            marker.text().remove();
        }
    }
    public void remove (Player player, String Name){
        for (Iterator<Map.Entry<UUID, WaypointDisplay>> it = markers.entrySet().iterator(); it.hasNext();){

        }
        WaypointDisplay marker = markers.remove(player.getUniqueId());

        if (marker != null && marker.marker() != null && marker.outer() != null && marker.text() != null){
            marker.marker().remove();
            marker.outer().remove();
            marker.text().remove();
        }
    }

    public void clear(){
        markers.values().forEach(Entity::remove);
        markers.clear();
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        remove(event.getPlayer());
    }


    private String normalizeName(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}