package net.tweakcraft.tweakcart.api.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class TweakVehicleBlockDetectEvent extends VehicleBlockEvent {
    public TweakVehicleBlockDetectEvent(Minecart c, Block b) {
        super(c, b);
    }
}
