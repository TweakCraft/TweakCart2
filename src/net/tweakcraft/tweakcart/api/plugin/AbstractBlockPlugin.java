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

package net.tweakcraft.tweakcart.api.plugin;

import net.tweakcraft.tweakcart.TweakCart;
import net.tweakcraft.tweakcart.api.event.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

//TODO: I'm not happy with the naming here. Couldn't we refactor and make it something like AbstractBlockEventListener?
//TODO: ATM it's more like an event listener than anything else.
//TODO: Also, should move onEnable() to the real 'plugin', because a listener can't tell the pluginmanager which events should be called.
//TODO: Create a plugin base for any TweakCart plugins. ATM we need to have a (Bukkit)plugin, and a TweakCart plugin.
//TODO: These to should be put togeter. E.G.: TweakCartPlugin extends JavaPlugin
//TODO: The above could override .onEnable() and do stuff like fetching the running TweakCart plugin from the server.
public abstract class AbstractBlockPlugin {
    protected TweakCart plugin;

    public AbstractBlockPlugin() {
        Plugin tweakCart = Bukkit.getServer().getPluginManager().getPlugin("TweakCart");
        if (tweakCart != null) {
            plugin = (TweakCart) tweakCart;
        }
    }

    /**
     * Called when this plugin is found and turned on
     */
    public abstract void onEnable();

    public abstract String getPluginName();

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


}
