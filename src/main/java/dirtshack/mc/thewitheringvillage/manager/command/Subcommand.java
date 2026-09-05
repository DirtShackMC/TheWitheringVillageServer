package dirtshack.mc.thewitheringvillage.manager.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface Subcommand {
    String name();
    String usage();
    String description();

    boolean execute(CommandSender sender, String[] args);

    default List<String> tabComplete(CommandSender sender, String[] args){
        return List.of();
    }
}
