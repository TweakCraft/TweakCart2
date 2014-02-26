package net.tweakcraft.tweakcart.api.event;

import org.bukkit.block.Sign;

/**
 * Created by edoxile on 2/26/14.
 */
public class TweakSignRedstoneEvent extends VehicleSignEvent {
    public TweakSignRedstoneEvent(final Sign s, final String k){
        super(null, null, s, k);
    }
}
