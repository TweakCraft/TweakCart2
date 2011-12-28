package net.tweakcraft.tweakcart.api.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class TweakVehicleDispenseEvent extends VehicleBlockEvent {
    public TweakVehicleDispenseEvent(Minecart c, Block b) {
        super(c, b);
    }
}
