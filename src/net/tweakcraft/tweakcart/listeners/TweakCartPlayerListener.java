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

import net.tweakcraft.tweakcart.TweakPluginManager;
import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.event.TweakSlabCartInDispenserEvent;
import net.tweakcraft.tweakcart.util.VehicleUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TweakCartPlayerListener implements Listener{
	private TweakPluginManager manager = TweakPluginManager.getInstance();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled() || event.getAction() != Action.LEFT_CLICK_BLOCK){
			System.out.println("noes?");
			return;
		}
		if(event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Dispenser && VehicleUtil.isMinecart(event.getItem().getType())){
			Material type = event.getItem().getType();
			Block dispBlock = event.getClickedBlock();

			//TODO: call cancellable event
			if(manager.callEvent(TweakCartEvent.Block.VehicleSlabInDispenserEvent, new TweakSlabCartInDispenserEvent(type, dispBlock))){
				Dispenser disp = (Dispenser) dispBlock.getState();
				ItemStack inHand = event.getItem();
				if(disp.getInventory().addItem(new ItemStack(type, 1)).size() == 0){
					if(inHand.getAmount() == 1){
						inHand = null;
					}else{
						inHand.setAmount(inHand.getAmount()-1);
					}

					//TODO: dit netter maken, carts moeten stacken, plus dat het gebruik moet maken van een Inventory framework achtig iets

					if(inHand != null){
						event.getPlayer().getInventory().setItemInHand(inHand);
					}else{
						System.out.println("oioi");
						int slot = event.getPlayer().getInventory().getHeldItemSlot();
						System.out.println("slot is " + slot);
						event.getPlayer().getInventory().clear(slot);
					}
				}
			}
		}
	}
}
