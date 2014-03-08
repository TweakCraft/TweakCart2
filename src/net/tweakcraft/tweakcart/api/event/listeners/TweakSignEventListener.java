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

package net.tweakcraft.tweakcart.api.event.listeners;

import net.tweakcraft.tweakcart.api.event.TweakSignRedstoneEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleCollidesWithSignEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleDestroyEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehiclePassesSignEvent;

public abstract class TweakSignEventListener extends TweakBlockEventListener {

    /**
     * Could be overriden, when a cart passes a sign, this method should be called for all
     * registered subplugins
     *
     * @param event TweakVehiclePassesSignEvent with information of event
     */
    public void onSignPass(TweakVehiclePassesSignEvent event) {
    }

    /**
     * Could be overriden, when a cart collides with a sign, this method should be called for all
     * registered subplugins
     *
     * @param event onSignCollisionEvent with information of event
     */
    public void onSignCollision(TweakVehicleCollidesWithSignEvent event) {
    }

    public void onSignRedstone(TweakSignRedstoneEvent event){
    }
}
