package net.tweakcraft.tweakcart;

import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.TweakCartSignEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleBlockCollisionEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleDetectEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleMoveEvent;
import net.tweakcraft.tweakcart.plugin.AbstractBlockPlugin;
import net.tweakcraft.tweakcart.plugin.AbstractSignPlugin;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class TweakPluginManager {
    private static Logger log = Logger.getLogger("Minecraft");
    private static TweakPluginManager instance;
    private Map<TweakCartEvent, List<AbstractBlockPlugin>> eventPluginMap = new HashMap<TweakCartEvent, List<AbstractBlockPlugin>>();
    private Map<Entry<TweakCartSignEvent, String>, AbstractSignPlugin> signEventPluginMap = new HashMap<Entry<TweakCartSignEvent, String>, AbstractSignPlugin>();

    private TweakPluginManager() {
    }

    ;

    public void callEvent(TweakCartEvent ev, Object arg) {
        List<AbstractBlockPlugin> pluginList = eventPluginMap.get(ev);
        if (pluginList != null) {
            for (AbstractBlockPlugin plugin : pluginList) {
                callEvent(plugin, ev, arg);
            }
        }
    }

    public void callSignEvent(TweakCartSignEvent ev, String keyword, Object arg) {
        AbstractSignPlugin plugin = signEventPluginMap.get(new SimpleEntry<TweakCartSignEvent, String>(ev, keyword));
        callSignEvent(plugin, ev, arg);
    }

    public void addEvent(TweakCartEvent ev, AbstractBlockPlugin av) {
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

    public void callEvent(AbstractBlockPlugin plugin, TweakCartEvent ev, Object data) {
        switch (ev) {
            case VehicleBlockChangeEvent:
                if (data instanceof TweakVehicleMoveEvent) {
                    plugin.onVehicleBlockChange((TweakVehicleMoveEvent) data);
                } else {
                    log.severe(String.format("[TweakCart] Could not pass VehicleBlockChangeEvent to %s, failed to cast object", plugin.getPluginName()));
                }
                break;
            case VehicleBlockCollisionEvent:
                if (data instanceof TweakVehicleBlockCollisionEvent) {
                    plugin.onVehicleBlockCollision((TweakVehicleBlockCollisionEvent) data);
                } else {
                    log.severe(String.format("[TweakCart] Could not pass TweakVehicleBlockCollisionEvent to %s, failed to cast object", plugin.getPluginName()));
                }
                break;
            case VehicleDetectEvent:
                if (data instanceof TweakVehicleDetectEvent) {
                    plugin.onVehicleDetect((TweakVehicleDetectEvent) data);
                } else {
                    log.severe(String.format("[TweakCart] Could not pass TweakVehicleDetectEvent to %s, failed to cast object", plugin.getPluginName()));
                }
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
    public static TweakPluginManager getInstance() {
        if (instance == null) {
            instance = new TweakPluginManager();
        }
        return instance;
    }
}
