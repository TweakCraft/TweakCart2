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

/**
 * TODO: Is this really necessary?
 */

package net.tweakcraft.tweakcart.listeners;

import net.tweakcraft.tweakcart.TweakPluginManager;
import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleDispenseEvent;
import net.tweakcraft.tweakcart.model.Direction;
import net.tweakcraft.tweakcart.util.VehicleUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class TweakCartBlockListener extends BlockListener {
    private TweakPluginManager manager = TweakPluginManager.getInstance();

    //TODO: need hooks for this?
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
    }

    //TODO: implement this
    public void onBlockDispense(BlockDispenseEvent event) {
        Material type = event.getItem().getType();
        Dispenser disp = (Dispenser) event.getBlock().getState();
        switch (type) {
            case MINECART:
            case STORAGE_MINECART:
            case POWERED_MINECART:
                Block b = event.getBlock();
                Direction d = Direction.getDirection(new Location(b.getWorld(), 0, 0, 0), event.getVelocity().toLocation(b.getWorld()));
                //Tjongejonge was het echt nodig om ook een locaal event te maken :p
                //TODO: fix the null idd
                if (!manager.callCancelableBlockEvent(TweakCartEvent.Block.VehicleDispenseEvent, new TweakVehicleDispenseEvent(null, d, b, type))) {
                    VehicleUtil.spawnCartFromDispenser(disp, type);
                    disp.getInventory().removeItem(event.getItem());
                    event.setCancelled(true);
                }
                break;
        }
    }
}
