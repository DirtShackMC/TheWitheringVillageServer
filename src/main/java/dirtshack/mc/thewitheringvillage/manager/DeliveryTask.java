package dirtshack.mc.thewitheringvillage.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import java.util.UUID;

public record DeliveryTask (
    UUID playerId,
    Location chestLocation,
    Material requiredMaterial,
    int requirement
) {}
