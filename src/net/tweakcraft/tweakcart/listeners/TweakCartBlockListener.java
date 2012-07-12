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

package net.tweakcraft.tweakcart.listeners;

import net.tweakcraft.tweakcart.api.event.TweakVehicleDispenseEvent;
import net.tweakcraft.tweakcart.api.util.TweakPermissionsManager;
import net.tweakcraft.tweakcart.model.Direction;
import net.tweakcraft.tweakcart.util.VehicleUtil;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class TweakCartBlockListener implements Listener {

    /*
    @EventHandler
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
    }
     */

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) {
        Material type = event.getItem().getType();
        Dispenser dispenser = (Dispenser) event.getBlock().getState();
        TweakPermissionsManager manager = TweakPermissionsManager.getInstance();

        switch (type) {
            case MINECART:
            case STORAGE_MINECART:
            case POWERED_MINECART:
                Direction d = Direction.getDirection(event.getVelocity());
                System.out.println("Direction: " + d);
                if (VehicleUtil.canSpawn(dispenser)) {
                    manager.cartCanDispense(new TweakVehicleDispenseEvent(d, event.getBlock(), type));
                    if (!event.isCancelled()) {
                        VehicleUtil.spawnCartFromDispenser(dispenser, type);
                        dispenser.getInventory().removeItem(event.getItem());
                        event.setCancelled(true);
                    }
                }
                break;
        }
    }
}
