package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class VehicleBlockEvent {
    private Block block;
    private Direction direction;
    private Minecart minecart;

    public VehicleBlockEvent(Minecart c, Direction d, Block b) {
        minecart = c;
        direction = d;
        block = b;
    }

    public Block getBlock() {
        return block;
    }

    public Minecart getMinecart() {
        return minecart;
    }

    public Direction getDirection() {
        return direction;
    }
}
