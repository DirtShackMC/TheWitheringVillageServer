package dirtshack.mc.thewitheringvillage.manager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.Objects;
public final class CommandManager implements CommandExecutor, TabCompleter
{
    private final Map<String, Subcommand> commands = new LinkedHashMap<>();
    public CommandManager(JavaPlugin plugin, String rootName)
    {
        PluginCommand root = Objects.requireNonNull(plugin.getCommand(rootName), rootName + " command not found");
        root.setExecutor(this);
        root.setTabCompleter(this);
    }

    public void register(Subcommand command){
        String name = command.name().toLowerCase(Locale.ROOT);

        if(commands.putIfAbsent(name, command) != null){
            throw new IllegalStateException("Command " + name + " is already registered!");
        }
    }

    private void showHelp(CommandSender sender){
        sender.sendMessage("The Withering Village commands:");
        commands.values().forEach(command ->
                sender.sendMessage("/village " + command.usage() + " - " + command.description())
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 0){
            showHelp(sender);
            return true;
        }
        Subcommand subcommand = commands.get(args[0].toLowerCase(Locale.ROOT));
        if (subcommand == null){
            sender.sendMessage("Unknown command " + args[0].toLowerCase(Locale.ROOT));
            showHelp(sender);
            return true;
        }
        String[] subcommandArgs = Arrays.copyOfRange(args, 1, args.length);

        if (!subcommand.execute(sender, subcommandArgs)){
            sender.sendMessage("Usage: /" + label + " " + subcommand.usage());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender, Command cmd, String label, String[] args
    ){
        if (args.length == 1){
            String enetered = args[0].toLowerCase(Locale.ROOT);

            return commands.keySet().stream()
                    .filter(name -> name.startsWith(enetered))
                    .toList();
        }
        Subcommand subcommand = commands.get(args[0].toLowerCase(Locale.ROOT));
        if (subcommand == null){
            return List.of();
        }
        return  subcommand.tabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
    }

}
