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

import net.tweakcraft.tweakcart.api.event.TweakPlayerCollectEvent;
import net.tweakcraft.tweakcart.api.util.TweakPermissionsManager;
import net.tweakcraft.tweakcart.util.InventoryManager;
import net.tweakcraft.tweakcart.util.VehicleUtil;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TweakCartPlayerListener implements Listener {
    private TweakPermissionsManager permissionsManager = TweakPermissionsManager.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled() || event.getAction() != Action.LEFT_CLICK_BLOCK || event.getItem() == null) {
            return;
        }
        if (event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Dispenser && VehicleUtil.isMinecart(event.getItem().getType())) {
            Dispenser dispenser = (Dispenser) event.getClickedBlock().getState();
            TweakPlayerCollectEvent collectEvent = new TweakPlayerCollectEvent(event.getPlayer(), dispenser);
            permissionsManager.playerCanSlap(collectEvent);
            if (!collectEvent.isCancelled()) {
                ItemStack inHand = event.getItem();
                ItemStack[] leftOver = InventoryManager.putContents(dispenser.getInventory(), inHand);
                event.getPlayer().setItemInHand(leftOver[0]);
                event.setCancelled(true);
            }
        }
    }
}
