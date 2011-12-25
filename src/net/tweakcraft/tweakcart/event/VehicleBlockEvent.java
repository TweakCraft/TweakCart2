package net.tweakcraft.tweakcart.event;

import org.bukkit.block.Block;

public class VehicleBlockEvent {
    private Block block;

    public VehicleBlockEvent(Block b) {
        block = b;
    }

    public Block getBlock() {
        return block;
    }
}
