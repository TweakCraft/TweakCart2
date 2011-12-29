package net.tweakcraft.tweakcart.api.plugin;

import net.tweakcraft.tweakcart.TweakCart;
import net.tweakcraft.tweakcart.api.event.TweakVehicleBlockChangeEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleBlockCollisionEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleBlockDetectEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleDispenseEvent;

public abstract class AbstractBlockPlugin {
    protected TweakCart plugin;
    
    public AbstractBlockPlugin(TweakCart p){
        plugin = p;
    }

    /**
     * Called when this plugin is found and turned on
     */
    public abstract void onEnable();

    public abstract String getPluginName();

    public void onVehicleBlockChange(TweakVehicleBlockChangeEvent event) {
        //Called when a vehicle enters a new block
    }

    public void onVehicleBlockCollision(TweakVehicleBlockCollisionEvent event) {
        //Called when a vehicle collides with a block
        //Is a default bukkit feature
    }

    public void onVehicleDispense(TweakVehicleDispenseEvent event) {
        //Called when tweakcart desides to dispense a cart
    }

    public void onVehicleDetect(TweakVehicleBlockDetectEvent event) {
        //Called when a cart is detected
        //Is a default bukkit feature
    }


}
