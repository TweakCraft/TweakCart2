package net.tweakcraft.tweakcart.event;

import org.bukkit.block.Block;

public class TweakVehicleBlockCollisionEvent extends VehicleBlockEvent {

    public TweakVehicleBlockCollisionEvent(Block b) {
        super(b);
    }
}
