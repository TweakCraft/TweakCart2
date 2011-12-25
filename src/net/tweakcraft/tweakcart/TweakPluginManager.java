package net.tweakcraft.tweakcart;

import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.TweakCartSignEvent;
import net.tweakcraft.tweakcart.event.*;
import net.tweakcraft.tweakcart.plugin.AbstractBlockPlugin;
import net.tweakcraft.tweakcart.plugin.AbstractSignPlugin;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TweakPluginManager {
    private static TweakPluginManager instance;
    private Map<TweakCartEvent, List<AbstractBlockPlugin>> eventPluginMap = new HashMap<TweakCartEvent, List<AbstractBlockPlugin>>();
    private Map<Entry<TweakCartSignEvent, String>, AbstractSignPlugin> signEventPluginMap = new HashMap<Entry<TweakCartSignEvent, String>, AbstractSignPlugin>();

    private TweakPluginManager() {
    }

    public void callBlockEvent(TweakCartEvent type, VehicleBlockEvent event) {
        List<AbstractBlockPlugin> pluginList = eventPluginMap.get(type);
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

    public void callSignEvent(TweakCartSignEvent type, String keyword, VehicleSignEvent event) {
        AbstractSignPlugin plugin = signEventPluginMap.get(new SimpleEntry<TweakCartSignEvent, String>(type, keyword));
        //TODO: cast values of event (need to update AbstractSignPlugin first)
        switch (type) {
            case VehiclePassesSignEvent:
                plugin.onSignPass(event);
            case VehicleCollidesWithSignEvent:
                plugin.onSignCollision(event);
        }
    }

    public void registerBlockEvents(AbstractBlockPlugin plugin, TweakCartEvent... events) {
        for (TweakCartEvent event : events) {
            addBlockEvent(event, plugin);
        }
    }

    public void addBlockEvent(TweakCartEvent ev, AbstractBlockPlugin av) {
        if (eventPluginMap.get(ev) != null) {
            List<AbstractBlockPlugin> pluginList = (List<AbstractBlockPlugin>) eventPluginMap.get(ev);
            pluginList.add(av);
        } else {
            List<AbstractBlockPlugin> pluginList = new ArrayList<AbstractBlockPlugin>();
            pluginList.add(av);
            eventPluginMap.put(ev, pluginList);
        }
    }

    public void addSignEvent(TweakCartSignEvent ev, String keyword, AbstractSignPlugin av) {
        signEventPluginMap.put(new SimpleEntry<TweakCartSignEvent, String>(ev, keyword), av);
    }

    /**
     * Singleton method, there should be exactly one pluginmanager at all time
     *
     * @return
     */
    public static TweakPluginManager getInstance() {
        if (instance == null) {
            instance = new TweakPluginManager();
        }
        return instance;
    }
}
