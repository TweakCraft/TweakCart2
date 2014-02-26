/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.tweakcraft.tweakcart.api.event;

import org.bukkit.entity.Minecart;

/**
 *
 * @author lennart
 */
public class TweakVehicleDestroyEvent implements TweakEvent{
    private final Minecart cart;
    
    public TweakVehicleDestroyEvent(final Minecart cart){
        this.cart = cart;
    }
    
    public Minecart getMinecart(){
        return this.cart;
    }
}
