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

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.zones.Zones;
import com.zones.model.ZonesAccess;
import net.tweakcraft.tweakcart.TweakCart;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class TweakPermissionsManager {
    public enum PermissionType {
        REDSTONE(ZonesAccess.Rights.HIT),
        BUILD(ZonesAccess.Rights.BUILD),
        NODE(null),
        ALL(ZonesAccess.Rights.ALL);

        private ZonesAccess.Rights rights;

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

    public TweakPermissionsManager() {
        //TODO: load some kind of config here
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(new File("."));
        } catch (Exception e) {
            TweakCart.log(e.getMessage());
        }
        permissionsEnabled = config.getBoolean("permissions.use-permissions");
        zonesEnabled = config.getBoolean("permissions.use-zones");
        worldGuardEnabled = config.getBoolean("permissions.use-worldguard");
        if (zonesEnabled) {
            Plugin p = Bukkit.getServer().getPluginManager().getPlugin("zones");
            if (p != null && p instanceof Zones) {
                zones = (Zones) p;
            }
        }
        if (worldGuardEnabled) {
            Plugin p = Bukkit.getServer().getPluginManager().getPlugin("worldguard");
            if (p != null && p instanceof WorldGuardPlugin) {
                worldGuard = (WorldGuardPlugin) p;
            }
        }
    }

    public boolean canDo(Player player, PermissionType type, String node, Block block) {
        if (permissionsEnabled) {
            if (player.isOp()) {
                return true;
            }
            if (player.hasPermission("tweakcart." + node)) {
                if (zonesEnabled) {
                    //TODO: do something with the return argument
                    zones.getWorldManager(block.getWorld()).getActiveZone(block).getAccess(player).canDo(type.getRights());
                }
                if (worldGuardEnabled) {
                    //TODO: implement this part
                    worldGuard.getRegionManager(block.getWorld());
                }
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
