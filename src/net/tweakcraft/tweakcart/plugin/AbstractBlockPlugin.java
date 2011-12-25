package net.tweakcraft.tweakcart.plugin;

import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleBlockChangeEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleBlockCollisionEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleBlockDetectEvent;
import net.tweakcraft.tweakcart.event.TweakVehicleDispenseEvent;

public abstract class AbstractBlockPlugin {
    public void registerEvents(TweakCartEvent... events) {

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
