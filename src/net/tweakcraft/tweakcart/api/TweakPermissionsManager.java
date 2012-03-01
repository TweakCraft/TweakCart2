package net.tweakcraft.tweakcart.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface TweakPermissionsManager {
    public boolean canDispense(Player p, Location location);
    
    public boolean canSlabCollect(Player p, Location location);
}
