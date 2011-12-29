package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class TweakVehicleBlockDetectEvent extends VehicleBlockEvent {
    public TweakVehicleBlockDetectEvent(Minecart c, Direction d, Block b) {
        super(c, d, b);
    }
}
