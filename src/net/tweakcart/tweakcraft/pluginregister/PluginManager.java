package net.tweakcart.tweakcraft.pluginregister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tweakcart.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.plugin.AbstractPlugin;

public class PluginManager {
    private Map<TweakCartEvent, List<AbstractPlugin>> eventPluginMap = new HashMap<TweakCartEvent,List<AbstractPlugin>>();
    public void callEvent(TweakCartEvent ev, Object arg){
        
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
}
