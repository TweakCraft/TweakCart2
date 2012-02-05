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
import net.tweakcraft.tweakcart.api.event.*;
import net.tweakcraft.tweakcart.api.listeners.TweakBlockEventListener;
import net.tweakcraft.tweakcart.api.listeners.TweakSignEventListener;
import net.tweakcraft.tweakcart.util.StringUtil;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TweakPluginManager {
    private static TweakPluginManager instance;
    private Map<TweakCartEvent.Block, List<TweakBlockEventListener>> blockEventPluginMap = new HashMap<TweakCartEvent.Block, List<TweakBlockEventListener>>();
    private Map<Entry<TweakCartEvent.Sign, String>, TweakSignEventListener> signEventPluginMap = new HashMap<Entry<TweakCartEvent.Sign, String>, TweakSignEventListener>();

    private TweakPluginManager() {
    }

    /**
     * Returns true if the event is canceled
     * TODO: is this the logical way?
     *
     * @param type  TweakCartEvent.Block with type of event
     * @param event VehicleBlockEvent with information
     */
    public boolean callCancelableBlockEvent(TweakCartEvent.Block type, VehicleBlockEvent event) {
        List<TweakBlockEventListener> eventListenerList = blockEventPluginMap.get(type);
        if (eventListenerList != null) {
            for (TweakBlockEventListener eventListener : eventListenerList) {
                switch (type) {
                case VehicleDispenseEvent:
                    if (event instanceof TweakVehicleDispenseEvent) {
                        eventListener.onVehicleDispense((TweakVehicleDispenseEvent) event);
                    }else{
                        //uh ooh
                    }
                    break;
                case VehicleCollectEvent:
                    if (event instanceof TweakVehicleCollectEvent) {
                        eventListener.onVehicleCollect((TweakVehicleCollectEvent) event);
                    }else{
                        //uh ooh
                    }
                    break;
               }
            }
        }
        
        return event instanceof CancellableVehicleEvent && ((CancellableVehicleEvent) event).isCancelled();
    }

    public void callEvent(TweakCartEvent.Block type, VehicleBlockEvent event) {
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
                }
            }
        }
    }

    public void callEvent(TweakCartEvent.Sign type, VehicleSignEvent event) {
        System.out.println("Calling event " + type);
        String keyword = StringUtil.stripBrackets(event.getKeyword()).toLowerCase();
        TweakSignEventListener plugin = signEventPluginMap.get(new SimpleEntry<TweakCartEvent.Sign, String>(type, keyword));
        if(plugin != null){
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

    /**
     * Singleton method, there should be exactly one PluginManager at all times
     *
     * @return TweakPluginManager instance
     */
    public static TweakPluginManager getInstance() {
        if (instance == null) {
            instance = new TweakPluginManager();
        }
        return instance;
    }
    
}
