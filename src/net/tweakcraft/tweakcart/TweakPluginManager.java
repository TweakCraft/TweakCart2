package net.tweakcraft.tweakcart;

import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.event.*;
import net.tweakcraft.tweakcart.api.plugin.AbstractBlockPlugin;
import net.tweakcraft.tweakcart.api.plugin.AbstractSignPlugin;
import net.tweakcraft.tweakcart.util.StringUtil;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TweakPluginManager {
    private static TweakPluginManager instance;
    private Map<TweakCartEvent.Block, List<AbstractBlockPlugin>> blockEventPluginMap = new HashMap<TweakCartEvent.Block, List<AbstractBlockPlugin>>();
    private Map<Entry<TweakCartEvent.Sign, String>, AbstractSignPlugin> signEventPluginMap = new HashMap<Entry<TweakCartEvent.Sign, String>, AbstractSignPlugin>();

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
        List<AbstractBlockPlugin> pluginList = blockEventPluginMap.get(type);
        if (pluginList != null) {
            for (AbstractBlockPlugin plugin : pluginList) {
                switch (type) {
                case VehicleDispenseEvent:
                    if (event instanceof TweakVehicleDispenseEvent) {
                        plugin.onVehicleDispense((TweakVehicleDispenseEvent) event);
                    }else{
                        //uh ooh
                    }
                    break;
                case VehicleCollectEvent:
                    if (event instanceof TweakVehicleCollectEvent) {
                        plugin.onVehicleCollect((TweakVehicleCollectEvent) event);
                    }else{
                        //uh ooh
                    }
                    break;
               }
            }
        }
        
        return event instanceof CancellableVehicleEvent && ((CancelableVehicleEvent) event).isCanceled();
    }

    public void callEvent(TweakCartEvent.Block type, VehicleBlockEvent event) {
        List<AbstractBlockPlugin> pluginList = blockEventPluginMap.get(type);
        if (pluginList != null) {
            for (AbstractBlockPlugin plugin : pluginList) {
                switch (type) {
                case VehicleBlockChangeEvent:
                    if (event instanceof TweakVehicleBlockChangeEvent) {
                        plugin.onVehicleBlockChange((TweakVehicleBlockChangeEvent) event);
                    } else {
                        //Something went wrong, debug info(?)
                    }
                    break;
                case VehicleBlockCollisionEvent:
                    if (event instanceof TweakVehicleBlockCollisionEvent) {
                        plugin.onVehicleBlockCollision((TweakVehicleBlockCollisionEvent) event);
                    } else {
                        //Something went wrong, debug info(?)
                    }
                    break;
                case VehicleBlockDetectEvent:
                    if (event instanceof TweakVehicleBlockDetectEvent) {
                        plugin.onVehicleDetect((TweakVehicleBlockDetectEvent) event);
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
        AbstractSignPlugin plugin = signEventPluginMap.get(new SimpleEntry<TweakCartEvent.Sign, String>(type, keyword));
        if(plugin != null){
            //TODO: cast values of event (need to update AbstractSignPlugin first)
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

    public void registerEvent(AbstractBlockPlugin plugin, TweakCartEvent.Block... events) {
        for (TweakCartEvent.Block event : events) {
            addBlockEvent(event, plugin);
        }
    }

    public void registerEvent(AbstractSignPlugin plugin, TweakCartEvent.Sign type, String... keywords) {
        for (String keyword : keywords) {
            signEventPluginMap.put(new SimpleEntry<TweakCartEvent.Sign, String>(type, keyword), plugin);
        }
    }

    private void addBlockEvent(TweakCartEvent.Block type, AbstractBlockPlugin plugin) {
        List<AbstractBlockPlugin> pluginList = blockEventPluginMap.get(type);
        if (pluginList != null) {
            pluginList.add(plugin);
        } else {
            pluginList = new ArrayList<AbstractBlockPlugin>();
            pluginList.add(plugin);
        }
        blockEventPluginMap.put(type, pluginList);
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
