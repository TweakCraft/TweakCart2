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

import net.tweakcraft.tweakcart.api.event.TweakSignRedstoneEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleDispenseEvent;
import net.tweakcraft.tweakcart.api.model.TweakCartEvent;
import net.tweakcraft.tweakcart.permissions.TweakPermissionsManager;
import net.tweakcraft.tweakcart.model.Direction;
import net.tweakcraft.tweakcart.util.BlockUtil;
import net.tweakcraft.tweakcart.util.TweakPluginManager;
import net.tweakcraft.tweakcart.util.VehicleUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.block.Sign;

public class TweakCartBlockListener implements Listener {

    private TweakPluginManager manager = TweakPluginManager.getInstance();

    /*
     @EventHandler
     public void onBlockRedstoneChange(BlockRedstoneEvent event) {
     }
     */
    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) {
        Material type = event.getItem().getType();

        TweakPermissionsManager manager = TweakPermissionsManager.getInstance();

        if (event.getBlock().getState() instanceof Dispenser) {
            Dispenser dispenser = (Dispenser) event.getBlock().getState();
            switch (type) {
                case MINECART:
                case STORAGE_MINECART:
                case POWERED_MINECART:
                    Direction d = Direction.getDirection(event.getVelocity());
                    //System.out.println("Direction: " + d);
                    if (VehicleUtil.canSpawn(dispenser)) {
                        manager.cartCanDispense(new TweakVehicleDispenseEvent(d, event.getBlock(), type));
                        if (!event.isCancelled()) {
                            event.setCancelled(true);
                        }
                    }
                    break;
            }
        }

    }

    @EventHandler
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
        if (event.getNewCurrent() == event.getOldCurrent() || event.getNewCurrent() > 0 && event.getOldCurrent() > 0 || event.getBlock().getType() != Material.REDSTONE_WIRE) {
            return;
        }
        Block block = event.getBlock();
        Sign sign;
        for (int dx = -1; dx <= 1; dx++) {
            if (BlockUtil.isSign(block.getRelative(dx, 0, 0))) {
                sign = (Sign) block.getRelative(dx, 0, 0).getState();
                if (sign != null) {
                    String keyword = sign.getLine(0);
                    manager.callEvent(TweakCartEvent.Sign.RedstoneEvent, new TweakSignRedstoneEvent(sign, keyword, event.getNewCurrent()));
                }
            }
        }
        for (int dz = -1; dz <= 1; dz++) {
            if (BlockUtil.isSign(block.getRelative(0, 0, dz))) {
                sign = (Sign) block.getRelative(0, 0, dz).getState();
                if (sign != null) {
                    String keyword = sign.getLine(0);
                    manager.callEvent(TweakCartEvent.Sign.RedstoneEvent, new TweakSignRedstoneEvent(sign, keyword, event.getNewCurrent()));
                }
            }
        }
        if (BlockUtil.isSign(block.getRelative(0, 1, 0))) {
            sign = (Sign) block.getRelative(0, 1, 0).getState();
            if (sign != null) {
                String keyword = sign.getLine(0);
                manager.callEvent(TweakCartEvent.Sign.RedstoneEvent, new TweakSignRedstoneEvent(sign, keyword, event.getNewCurrent()));
            }
        }

    }
}
