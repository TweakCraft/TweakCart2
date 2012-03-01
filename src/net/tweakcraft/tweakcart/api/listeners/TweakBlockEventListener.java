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

package net.tweakcraft.tweakcart.api.listeners;

import net.tweakcraft.tweakcart.api.event.*;

public abstract class TweakBlockEventListener {

    public void onVehicleBlockChange(TweakVehicleBlockChangeEvent event) {
        //Called when a vehicle enters a new block
    }

    public void onVehicleBlockCollision(TweakVehicleBlockCollisionEvent event) {
        //Called when a vehicle collides with a block
        //Is a default bukkit feature
    }

    public void onVehicleDispense(TweakVehicleDispenseEvent event) {
        //Called when tweakcart desides to dispense a cart
    }

    public void onVehicleDetect(TweakVehicleBlockDetectEvent event) {
        //Called when a cart is detected
        //Is a default bukkit feature
    }

    public void onVehicleCollect(TweakVehicleCollectEvent event) {
        // TODO Auto-generated method stub

    }

    @Deprecated
	public void onVehicleSlabInDispenserEvent(
			TweakSlabCartInDispenserEvent event) {
		// TODO Auto-generated method stub
		
	}


}
