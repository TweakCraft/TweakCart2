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

import net.tweakcraft.tweakcart.api.event.TweakVehicleCollidesWithSignEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehiclePassesSignEvent;

//TODO: I'm not happy with the naming here. Couldn't we refactor and make it something like AbstractSignEventListener?
//TODO: ATM it's more like an event listener than anything else.
//TODO: Also, should move onEnable() to the real 'plugin', because a listener can't tell the pluginmanager which events should be called.
//TODO: Create a plugin base for any TweakCart plugins. ATM we need to have a (Bukkit)plugin, and a TweakCart plugin.
//TODO: These to should be put togeter. E.G.: TweakCartPlugin extends JavaPlugin
//TODO: The above could override .onEnable() and do stuff like fetching the running TweakCart plugin from the server.
public abstract class AbstractSignPlugin extends AbstractBlockPlugin {

    public AbstractSignPlugin() {
        super();
    }

    /**
     * To be implemented by a plugin, register events etc.
     */
    public abstract void onEnable();

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
}
