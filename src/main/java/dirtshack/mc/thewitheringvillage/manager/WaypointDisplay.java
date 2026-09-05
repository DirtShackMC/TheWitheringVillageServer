package dirtshack.mc.thewitheringvillage.manager;
import org.bukkit.entity.TextDisplay;

public record WaypointDisplay (String Name, TextDisplay marker, TextDisplay outer, TextDisplay text){}