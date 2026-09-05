package dirtshack.mc.thewitheringvillage.manager.command;

import dirtshack.mc.thewitheringvillage.manager.WaypointManager;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class WaypointCommand implements Subcommand {
    private final WaypointManager waypointManager;

    public WaypointCommand(WaypointManager waypointManager) {
        this.waypointManager = waypointManager;
    }

    @Override
    public final String name() {
        return "waypoint";
    }
    @Override
    public final String description() {
        return "Shows a waypoint at the given coordinates";
    }
    @Override
    public final String usage() {
        return "waypoint <x> <y> <z>";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Color.RED + "Only players can execute this command");
            return true;
        }
        if (args.length != 3) {
            return false;
        }
        try {
            double x = Integer.parseInt(args[0]);
            double y = Integer.parseInt(args[1]);
            double z = Integer.parseInt(args[2]);

            if (!Double.isFinite(x) && !Double.isFinite(y) && !Double.isFinite(z)) {
                throw new NumberFormatException("Coordinates must be finite");
            }

            Location target = new Location(player.getWorld(), x, y, z);

            waypointManager.show("awdasdadws", player, target, "");
            player.sendMessage(Color.GREEN + "Waypoint set.");
        } catch (NumberFormatException e) {
            player.sendMessage(Color.RED + "Coordinates must be a valid number");
        }

        return true;
    }
}
