package net.tweakcraft.tweakcart.plugin;

import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleBlockCollisionEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleDetectEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleMoveEvent;

public abstract class AbstractBlockPlugin {
    public abstract void registerEvents(TweakCartEvent... events);
    
    /**
     * Called when this plugin is found and turned on
     */
    public abstract void onEnable();
    
    public abstract String getPluginName();
    
    public void registerParserOnKeyword(AbstractParser p, String keyword){
        return;
    }

    
    public void onVehicleBlockChange(TweakVehicleMoveEvent data){
        //Called when a vehicle enters a new block
    }
    
    public void onVehicleBlockCollision(TweakVehicleBlockCollisionEvent data){
        //Called when a vehicle collides with a block
        //Is a default bukkit feature
    }
    
    public void onVehicleDispense(){
        //Called when tweakcart desides to dispense a cart
    }
    
    public void onVehicleDetect(TweakVehicleDetectEvent data){
        //Called when a cart is detected
        //Is a default bukkit feature
    }


}
