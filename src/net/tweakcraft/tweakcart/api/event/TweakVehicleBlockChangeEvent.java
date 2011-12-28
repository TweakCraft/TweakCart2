package net.tweakcraft.tweakcart.api.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class TweakVehicleBlockChangeEvent extends VehicleBlockEvent {
    public TweakVehicleBlockChangeEvent(Minecart c, Block b) {
        super(c, b);
    }
}
