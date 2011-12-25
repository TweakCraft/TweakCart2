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

public class PluginManager {
    private static PluginManager instance;
    private Map<TweakCartEvent, List<AbstractBlockPlugin>> eventPluginMap = new HashMap<TweakCartEvent, List<AbstractBlockPlugin>>();
    private Map<Entry<TweakCartSignEvent, String>, AbstractSignPlugin> signEventPluginMap = new HashMap<Entry<TweakCartSignEvent, String>, AbstractSignPlugin>();

    private PluginManager() {
    }

    public void callBlockEvent(TweakCartEvent type, VehicleBlockEvent event) {
        List<AbstractBlockPlugin> pluginList = eventPluginMap.get(type);
        if (pluginList != null) {
            for (AbstractBlockPlugin plugin : pluginList) {
                callBlockEvent(plugin, type, event);
            }
        }
    }

    public void callSignEvent(TweakCartSignEvent type, String keyword, VehicleSignEvent event) {
        AbstractSignPlugin plugin = signEventPluginMap.get(new SimpleEntry<TweakCartSignEvent, String>(type, keyword));
        callSignEvent(plugin, type, event);
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

    public void callBlockEvent(AbstractBlockPlugin plugin, TweakCartEvent ev, VehicleBlockEvent vehicleBlockEvent) {
        switch (ev) {
            case VehicleBlockChangeEvent:
                plugin.onVehicleBlockChange((TweakVehicleBlockChangeEvent) vehicleBlockEvent);
                break;
            case VehicleBlockCollisionEvent:
                plugin.onVehicleBlockCollision((TweakVehicleBlockCollisionEvent) vehicleBlockEvent);
                break;
            case VehicleBlockDetectEvent:
                plugin.onVehicleDetect((TweakVehicleBlockDetectEvent) vehicleBlockEvent);
                break;
        }

    }

    public void callSignEvent(AbstractSignPlugin plugin, TweakCartSignEvent ev, Object arg) {
        switch (ev) {
            case VehiclePassesSignEvent:
                plugin.onSignPass(arg);
            case VehicleCollidesWithSignEvent:
                plugin.onSignCollision(arg);
        }
    }

    /**
     * Singleton method, there should be exactly one pluginmanager at all time
     *
     * @return
     */
    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }
}
