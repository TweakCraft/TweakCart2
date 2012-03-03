/*
 * Copyright (c) 2012. 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.tweakcraft.tweakcart.api;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.zones.Zones;
import com.zones.model.ZonesAccess;
import net.tweakcraft.tweakcart.TweakCart;
import net.tweakcraft.tweakcart.api.event.TweakPlayerCollectEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleCollectEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleDispenseEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class TweakPermissionsManager {
    private static TweakPermissionsManager instance = new TweakPermissionsManager();

    public static TweakPermissionsManager getInstance() {
        return instance;
    }

    public enum PermissionType {
        REDSTONE(ZonesAccess.Rights.HIT),
        BUILD(ZonesAccess.Rights.BUILD),
        NODE(null),
        ALL(ZonesAccess.Rights.ALL);

        private ZonesAccess.Rights rights;

        //TODO: can such a system also be used with WorldGuard? It would save some cycles...
        private PermissionType(ZonesAccess.Rights rightsNeeded) {
            rights = rightsNeeded;
        }

        public ZonesAccess.Rights getRights() {
            return rights;
        }
    }

    private boolean zonesEnabled = false;
    private boolean worldGuardEnabled = false;
    private boolean permissionsEnabled = false;

    private Zones zones;
    private WorldGuardPlugin worldGuard;

    private ArrayList<TweakPermissionsHandler> permissionsHandlers = new ArrayList<TweakPermissionsHandler>();
    private YamlConfiguration config = TweakCart.getYamlConfig();

    private TweakPermissionsManager() {
        permissionsEnabled = config.getBoolean("permissions.use-permissions");
        zonesEnabled = config.getBoolean("permissions.use-zones");
        worldGuardEnabled = config.getBoolean("permissions.use-worldguard");
        if (zonesEnabled) {
            Plugin p = Bukkit.getServer().getPluginManager().getPlugin("zones");
            if (p != null && p instanceof Zones) {
                zones = (Zones) p;
            } else {
                zonesEnabled = false;
                TweakCart.log("Zones was enabled in the config but not found running on the server!", Level.SEVERE);
            }
        }
        if (worldGuardEnabled) {
            Plugin p = Bukkit.getServer().getPluginManager().getPlugin("worldguard");
            if (p != null && p instanceof WorldGuardPlugin) {
                worldGuard = (WorldGuardPlugin) p;
            } else {
                worldGuardEnabled = false;
                TweakCart.log("WorldGuard was enabled in the config but not found running on the server!", Level.SEVERE);
            }
        }
    }

    public boolean playerCanDo(Player player, PermissionType type, String node, Block block) {
        if (permissionsEnabled) {
            if (player.isOp()) {
                return true;
            }
            if (player.hasPermission("tweakcart." + node)) {
                if (zonesEnabled) {
                    if (!zones.getWorldManager(block.getWorld()).getActiveZone(block).getAccess(player).canDo(type.getRights())) {
                        return false;
                    }
                }
                if (worldGuardEnabled) {
                    LocalPlayer localPlayer = new BukkitPlayer(worldGuard, player);
                    ApplicableRegionSet regionSet = worldGuard.getRegionManager(block.getWorld()).getApplicableRegions(BukkitUtil.toVector(block));
                    switch (type) {
                        case ALL:
                            if (!regionSet.canBuild(localPlayer) || !regionSet.canUse(localPlayer)) {
                                return false;
                            }
                            break;
                        case BUILD:
                            if (!regionSet.canBuild(localPlayer)) {
                                return false;
                            }
                            break;
                        case REDSTONE:
                            if (!regionSet.canUse(localPlayer)) {
                                return false;
                            }
                            break;
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean cartCanCollect(TweakVehicleCollectEvent event) {
        if (event.getBlock().getState() instanceof Dispenser) {
            Dispenser dispenser = (Dispenser) event.getBlock().getState();
            for (TweakPermissionsHandler handler : permissionsHandlers) {
                if (!handler.canVehicleCollect(event.getMinecart(), dispenser)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean cartCanDispense(TweakVehicleDispenseEvent event) {
        if (event.getBlock().getState() instanceof Dispenser) {
            Dispenser dispenser = (Dispenser) event.getBlock();
            for (TweakPermissionsHandler handler : permissionsHandlers) {
                if (!handler.canDispense(dispenser)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean playerCanSlap(TweakPlayerCollectEvent event){
        for (TweakPermissionsHandler handler : permissionsHandlers) {
            if (!handler.canSlapCollect(event.getPlayer(), event.getDispenser())) {
                return false;
            }
        }
        return false;
    }

    public void registerPermissionsHandler(TweakPermissionsHandler handler) {
        //TODO: check if this works without overwriting .hashCode() & .equals()
        if (!permissionsHandlers.contains(handler)) {
            permissionsHandlers.add(handler);
        } else {
            TweakCart.log("TweakPermissionsHandler " + handler.getName() + " is already registered!", Level.WARNING);
        }
    }

    public void reloadConfig() {
        permissionsEnabled = config.getBoolean("permissions.use-permissions");
        zonesEnabled = config.getBoolean("permissions.use-zones");
        worldGuardEnabled = config.getBoolean("permissions.use-worldguard");

        if (permissionsEnabled) {
            if (zonesEnabled && zones == null) {
                Plugin p = Bukkit.getServer().getPluginManager().getPlugin("zones");
                if (p != null && p instanceof Zones) {
                    zones = (Zones) p;
                } else {
                    zonesEnabled = false;
                    TweakCart.log("Zones was enabled in the config but not found running on the server!", Level.SEVERE);
                }
            } else {
                zones = null;
            }
            if (worldGuardEnabled && worldGuard == null) {
                Plugin p = Bukkit.getServer().getPluginManager().getPlugin("worldguard");
                if (p != null && p instanceof WorldGuardPlugin) {
                    worldGuard = (WorldGuardPlugin) p;
                } else {
                    worldGuardEnabled = false;
                    TweakCart.log("WorldGuard was enabled in the config but not found running on the server!", Level.SEVERE);
                }
            } else {
                worldGuard = null;
            }
        } else {
            worldGuardEnabled = false;
            zonesEnabled = false;
            worldGuard = null;
            zones = null;
        }
    }

}
