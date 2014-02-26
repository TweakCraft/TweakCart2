package net.tweakcraft.tweakcart.api.event;

import org.bukkit.block.Sign;

/**
 * Created by edoxile on 2/26/14.
 */
public class TweakSignRedstoneEvent extends VehicleSignEvent {
    final int power;

    public TweakSignRedstoneEvent(final Sign s, final String k, final int p){
        super(null, null, s, k);
        power = p;
    }

    public int getPower(){
        return power;
    }
}
