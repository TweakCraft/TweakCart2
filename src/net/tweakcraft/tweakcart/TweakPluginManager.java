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

package net.tweakcraft.tweakcart;

import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.TweakPermissionsHandler;
import net.tweakcraft.tweakcart.api.TweakPermissionsManager;
import net.tweakcraft.tweakcart.api.event.*;
import net.tweakcraft.tweakcart.api.listeners.TweakBlockEventListener;
import net.tweakcraft.tweakcart.api.listeners.TweakSignEventListener;
import net.tweakcraft.tweakcart.util.StringUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TweakPluginManager {
    private static TweakPluginManager instance = new TweakPluginManager();
    private Map<TweakCartEvent.Block, List<TweakBlockEventListener>> blockEventPluginMap = new HashMap<TweakCartEvent.Block, List<TweakBlockEventListener>>();
    private Map<Entry<TweakCartEvent.Sign, String>, TweakSignEventListener> signEventPluginMap = new HashMap<Entry<TweakCartEvent.Sign, String>, TweakSignEventListener>();

    private TweakPermissionsManager permissionsManager = TweakPermissionsManager.getInstance();

    public void callEvent(TweakCartEvent.Block type, TweakEvent event) {
        List<TweakBlockEventListener> eventListenerList = blockEventPluginMap.get(type);
        if (eventListenerList != null) {
            for (TweakBlockEventListener eventListener : eventListenerList) {
                switch (type) {
                    case VehicleBlockChangeEvent:
                        if (event instanceof TweakVehicleBlockChangeEvent) {
                            eventListener.onVehicleBlockChange((TweakVehicleBlockChangeEvent) event);
                        } else {
                            //Something went wrong, debug info(?)
                        }
                        break;
                    case VehicleBlockCollisionEvent:
                        if (event instanceof TweakVehicleBlockCollisionEvent) {
                            eventListener.onVehicleBlockCollision((TweakVehicleBlockCollisionEvent) event);
                        } else {
                            //Something went wrong, debug info(?)
                        }
                        break;
                    case VehicleBlockDetectEvent:
                        if (event instanceof TweakVehicleBlockDetectEvent) {
                            eventListener.onVehicleDetect((TweakVehicleBlockDetectEvent) event);
                        } else {
                            //Something went wrong, debug info(?)
                        }
                        break;
                    case VehicleDispenseEvent:
                        if (event instanceof TweakVehicleDispenseEvent) {
                            permissionsManager.cartCanDispense((TweakVehicleDispenseEvent) event);
                        } else {
                            //Something went wrong, debug info(?)
                        }
                        break;
                    case VehicleCollectEvent:
                        if (event instanceof TweakVehicleCollectEvent) {
                            permissionsManager.cartCanCollect((TweakVehicleCollectEvent) event);
                        } else {
                            //Something went wrong, debug info(?)
                        }
                        break;

                    case VehicleSlabInDispenserEvent:
                        break;
                }
            }
        }
    }

    public boolean callEvent(TweakCartEvent.Sign type, VehicleSignEvent event) {
        System.out.println("Calling event " + type);
        String keyword = StringUtil.stripBrackets(event.getKeyword()).toLowerCase();
        TweakSignEventListener plugin = signEventPluginMap.get(new SimpleEntry<TweakCartEvent.Sign, String>(type, keyword));
        if (plugin != null) {
            //TODO: cast values of event (need to update TweakSignEventListener first)
            switch (type) {
                case VehiclePassesSignEvent:
                    if (event instanceof TweakVehiclePassesSignEvent) {
                        plugin.onSignPass((TweakVehiclePassesSignEvent) event);
                    } else {
                        //Something went wrong, debug info(?)
                    }
                case VehicleCollidesWithSignEvent:
                    if (event instanceof TweakVehicleCollidesWithSignEvent) {
                        System.out.println("Still working!");
                        plugin.onSignCollision((TweakVehicleCollidesWithSignEvent) event);
                    } else {
                        //Something went wrong, debug info(?)
                    }
            }
        }
        return event instanceof CancellableEvent && ((CancellableEvent) event).isCancelled();
    }

    public void registerEvent(TweakBlockEventListener eventListener, TweakCartEvent.Block... events) {
        for (TweakCartEvent.Block event : events) {
            addBlockEvent(event, eventListener);
        }
    }

    public void registerEvent(TweakSignEventListener plugin, TweakCartEvent.Sign type, String... keywords) {
        for (String keyword : keywords) {
            signEventPluginMap.put(new SimpleEntry<TweakCartEvent.Sign, String>(type, keyword), plugin);
        }
    }

    private void addBlockEvent(TweakCartEvent.Block type, TweakBlockEventListener eventListener) {
        List<TweakBlockEventListener> eventListenerList = blockEventPluginMap.get(type);
        if (eventListenerList != null) {
            eventListenerList.add(eventListener);
        } else {
            eventListenerList = new ArrayList<TweakBlockEventListener>();
            eventListenerList.add(eventListener);
        }
        blockEventPluginMap.put(type, eventListenerList);
    }

    @Deprecated
    public boolean canDoAction(TweakCartEvent.Block event, Player p, Location location) {
        /*
        if (t.getConfig().getBoolean("UseDefaults")) {
            //let zones etc handle this one...
            //fastest way for our configuration

            //there are two options, first off we can handle permissions here
            //second off, we can implement the interface i made (TweakCartPermissionsManager)
            //with zones and perms, but that would make the else clause more suitable


            return true;
        } else {
            //let others handle this one
            //modularity ftw, but little slower
            for (TweakPermissionsHandler perm : permissionsHandlers) {
                switch (event) {
                    case VehicleDispenseEvent:
                        if (!perm.canDispense(p, location)) return false;
                    case VehicleSlabInDispenserEvent:
                        if (!perm.canSlapCollect(p, location)) return false;
                }
            }
            return true;
        }
        */
        return true;
    }

    public void addPermissionsManager(TweakPermissionsHandler perm) {

    }

    /**
     * Singleton method, there should be exactly one PluginManager at all times
     *
     * @return TweakPluginManager instance
     */
    public static TweakPluginManager getInstance() {
        return instance;
    }

}
