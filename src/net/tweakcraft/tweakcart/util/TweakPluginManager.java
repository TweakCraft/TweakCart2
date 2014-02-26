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

package net.tweakcraft.tweakcart.util;

import java.util.*;

import net.tweakcraft.tweakcart.TweakCart;
import net.tweakcraft.tweakcart.api.event.*;
import net.tweakcraft.tweakcart.api.event.listeners.TweakBlockEventListener;
import net.tweakcraft.tweakcart.api.event.listeners.TweakSignEventListener;
import net.tweakcraft.tweakcart.api.model.TweakCartEvent;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.logging.Level;

public class TweakPluginManager {
    private static TweakPluginManager instance = new TweakPluginManager();
    private Map<TweakCartEvent.Block, List<TweakBlockEventListener>> blockEventListenerMap = new HashMap<TweakCartEvent.Block, List<TweakBlockEventListener>>();
    private Map<Entry<TweakCartEvent.Sign, String>, TweakSignEventListener> signEventListenerMap = new HashMap<Entry<TweakCartEvent.Sign, String>, TweakSignEventListener>();
    private Map<TweakCartEvent.Sign, List<TweakSignEventListener>> anySignEventListenerMap = new HashMap<TweakCartEvent.Sign, List<TweakSignEventListener>>();

    public void callEvent(TweakCartEvent.Block type, TweakEvent event) {
        List<TweakBlockEventListener> eventListenerList = blockEventListenerMap.get(type);
        if (eventListenerList != null) {
            for (TweakBlockEventListener eventListener : eventListenerList) {
                switch (type) {
                    case VehicleBlockChangeEvent:
                        if (event instanceof TweakVehicleBlockChangeEvent) {
                            eventListener.onVehicleBlockChange((TweakVehicleBlockChangeEvent) event);
                        } else {
                            TweakCart.log("Event was thrown but event and type do not correspond " + TweakCartEvent.Block.VehicleBlockChangeEvent, Level.WARNING);
                        }
                        break;
                    case VehicleBlockCollisionEvent:
                        if (event instanceof TweakVehicleBlockCollisionEvent) {
                            eventListener.onVehicleBlockCollision((TweakVehicleBlockCollisionEvent) event);
                        } else {
                            TweakCart.log("Event was thrown but event and type do not correspond " + TweakCartEvent.Block.VehicleBlockCollisionEvent, Level.WARNING);
                        }
                        break;
                    case VehicleBlockDetectEvent:
                        if (event instanceof TweakVehicleBlockDetectEvent) {
                            eventListener.onVehicleDetect((TweakVehicleBlockDetectEvent) event);
                        } else {
                            TweakCart.log("Event was thrown but event and type do not correspond " + TweakCartEvent.Block.VehicleBlockDetectEvent, Level.WARNING);
                        }
                        break;
                    case VehicleDestroyEvent:
                        if (event instanceof TweakVehicleDestroyEvent) {
                            eventListener.onVehicleDestroy((TweakVehicleDestroyEvent) event);
                        } else {
                            TweakCart.log("Event was thrown but event and type do not correspond " + TweakCartEvent.Block.VehicleBlockDetectEvent, Level.WARNING);
                        }
                    default:
                        break;
                }
            }
        }
    }

    public void callEvent(TweakCartEvent.Sign type, VehicleSignEvent event) {
        String keyword = StringUtil.stripBrackets(event.getKeyword()).toLowerCase();
        List<TweakSignEventListener> eventListeners = anySignEventListenerMap.get(type);
        if (eventListeners == null) {
            eventListeners = new ArrayList<TweakSignEventListener>();
        }
        TweakSignEventListener eventListener = signEventListenerMap.get(new SimpleEntry<TweakCartEvent.Sign, String>(type, keyword));
        if (eventListener != null) {
            eventListeners.add(eventListener);
        }
        switch (type) {
            case VehiclePassesSignEvent:
                if (event instanceof TweakVehiclePassesSignEvent) {
                    for (TweakSignEventListener listener : eventListeners) {
                        try {
                            listener.onSignPass((TweakVehiclePassesSignEvent) event);
                        } catch (Exception ex) {
                            TweakCart.log("VehiclePassesSignEvent could not be passed to " + listener.getClass().getName(), Level.WARNING);
                            TweakCart.log("Sign at " + event.getSign().getBlock().getLocation());
                            System.out.println(Arrays.toString(event.getSign().getLines()));
                            ex.printStackTrace();
                        }
                    }
                } else {
                    TweakCart.log("Event was thrown but event and type do not correspond " + TweakCartEvent.Sign.VehiclePassesSignEvent, Level.WARNING);
                }
                break;
            case VehicleCollidesWithSignEvent:
                if (event instanceof TweakVehicleCollidesWithSignEvent) {
                    for (TweakSignEventListener listener : eventListeners) {
                        listener.onSignCollision((TweakVehicleCollidesWithSignEvent) event);
                    }
                } else {
                    TweakCart.log("Event was thrown but event and type do not correspond " + TweakCartEvent.Sign.VehicleCollidesWithSignEvent, Level.WARNING);
                }
                break;
            case RedstoneEvent:
                if (event instanceof TweakSignRedstoneEvent) {
                    for (TweakSignEventListener listener : eventListeners) {
                        listener.onSignRedstone((TweakSignRedstoneEvent) event);
                    }
                } else {
                    TweakCart.log("Event was thrown but event and type do not correspond " + TweakCartEvent.Sign.RedstoneEvent, Level.WARNING);
                }
        }
    }

    public void registerEvent(TweakBlockEventListener eventListener, TweakCartEvent.Block... types) {
        for (TweakCartEvent.Block type : types) {
            addBlockEvent(type, eventListener);
        }
    }

    public void registerEvent(TweakSignEventListener eventListener, TweakCartEvent.Sign type, String... keywords) {
        for (String keyword : keywords) {
            signEventListenerMap.put(new SimpleEntry<TweakCartEvent.Sign, String>(type, keyword), eventListener);
        }
    }

    public void registerEvent(TweakSignEventListener eventListener, TweakCartEvent.Sign type) {
        List<TweakSignEventListener> list = anySignEventListenerMap.get(type);
        if (list == null) {
            list = new ArrayList<TweakSignEventListener>();
        }
        list.add(eventListener);
        anySignEventListenerMap.put(type, list);
    }

    private void addBlockEvent(TweakCartEvent.Block type, TweakBlockEventListener eventListener) {
        List<TweakBlockEventListener> eventListenerList = blockEventListenerMap.get(type);
        if (eventListenerList != null) {
            eventListenerList.add(eventListener);
        } else {
            eventListenerList = new ArrayList<TweakBlockEventListener>();
            eventListenerList.add(eventListener);
        }
        blockEventListenerMap.put(type, eventListenerList);
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
