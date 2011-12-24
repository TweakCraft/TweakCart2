package net.tweakcraft.tweakcart.pluginregister;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.TweakCartSignEvent;
import net.tweakcraft.tweakcart.plugin.AbstractPlugin;
import net.tweakcraft.tweakcart.plugin.AbstractSignPlugin;

public class PluginManager {
    private Map<TweakCartEvent, List<AbstractPlugin>> eventPluginMap = new HashMap<TweakCartEvent,List<AbstractPlugin>>();
    private Map<Entry<TweakCartSignEvent, String>, AbstractSignPlugin> signEventPluginMap = new HashMap<Entry<TweakCartSignEvent, String>, AbstractSignPlugin>();
    public void callEvent(TweakCartEvent ev, Object arg){
        List<AbstractPlugin> pluginList = eventPluginMap.get(ev);
        if(pluginList != null){
            for(AbstractPlugin plugin : pluginList){
                plugin.callEvent(ev, arg);
            }
        }
    }
    
    public void callSignEvent(TweakCartSignEvent ev, String keyword, Object arg){
        AbstractSignPlugin plugin = signEventPluginMap.get(new SimpleEntry<TweakCartSignEvent, String>(ev,keyword));
        plugin.callSignEvent(ev, arg);
    }
    
    public void addEvent(TweakCartEvent ev, AbstractPlugin av){
        if(eventPluginMap.get(ev) != null){
            List<AbstractPlugin> pluginList = (List<AbstractPlugin>)eventPluginMap.get(ev);
            pluginList.add(av);
        }else{
            List<AbstractPlugin> pluginList = new ArrayList<AbstractPlugin>();
            pluginList.add(av);
            eventPluginMap.put(ev, pluginList);
        }
    }
    
    public void addSignEvent(TweakCartSignEvent ev, String keyword, AbstractSignPlugin av){
        signEventPluginMap.put(new SimpleEntry<TweakCartSignEvent, String>(ev, keyword), av);
    }
}
