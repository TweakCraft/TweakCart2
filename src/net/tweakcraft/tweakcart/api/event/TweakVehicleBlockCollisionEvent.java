package net.tweakcraft.tweakcart.api.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class TweakVehicleBlockCollisionEvent extends VehicleBlockEvent {
    public TweakVehicleBlockCollisionEvent(Minecart c, Block b) {
        super(c, b);
    }
}
