package net.tweakcraft.tweakcart.event;

import org.bukkit.block.Block;

public class TweakVehicleBlockDetectEvent extends VehicleBlockEvent {

    public TweakVehicleBlockDetectEvent(Block b) {
        super(b);
    }
}
