package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class TweakVehicleBlockCollisionEvent extends VehicleBlockEvent {
    public TweakVehicleBlockCollisionEvent(Minecart c, Direction d, Block b) {
        super(c, d, b);
    }
}
