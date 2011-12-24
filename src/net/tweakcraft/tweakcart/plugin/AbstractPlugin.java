package net.tweakcraft.tweakcart.plugin;

import net.tweakcraft.tweakcart.api.CartModel;
import net.tweakcraft.tweakcart.api.TweakCartEvent;

public abstract class AbstractPlugin {
    public abstract void registerEvents(TweakCartEvent... events);
    
    public abstract void onEnable();
    
    public void registerParserOnKeyword(AbstractParser p, String keyword){
        return;
    }

    
    public void onVehicleBlockChange(){
        //Called when a vehicle enters a new block
    }
    
    public void onVehicleBlockCollision(){
        //Called when a vehicle collides with a block
        //Is a default bukkit feature
    }
    
    public void onVehicleDispense(){
        //Called when tweakcart desides to dispense a cart
    }
    
    public void onVehicleDetect(CartModel cart){
        //Called when a cart is detected
        //Is a default bukkit feature
    }
}
