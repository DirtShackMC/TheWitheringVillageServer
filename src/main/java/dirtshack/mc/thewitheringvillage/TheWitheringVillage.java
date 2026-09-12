package dirtshack.mc.thewitheringvillage;
//Managers
import dirtshack.mc.thewitheringvillage.manager.WaypointManager;

//Commands
import dirtshack.mc.thewitheringvillage.manager.command.CommandManager;
import dirtshack.mc.thewitheringvillage.manager.command.WaypointCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TheWitheringVillage extends JavaPlugin {
    private WaypointManager waypointManager;
    @Override
    public void onEnable() {
        waypointManager =  new WaypointManager(this);
        CommandManager commands = new CommandManager(this, "village");

        commands.register(new WaypointCommand(waypointManager));
        getLogger().info("Initializing The Withering Village Plugin");
    }

    @Override
    public void onDisable() {
        if (waypointManager != null) {
            waypointManager.clear();
        }
    }

}
