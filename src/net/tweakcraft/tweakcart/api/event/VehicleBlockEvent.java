package net.tweakcraft.tweakcart.api.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class VehicleBlockEvent {
    private Block block;
    private Minecart minecart;

    public VehicleBlockEvent(Minecart c, Block b) {
        minecart = c;
        block = b;
    }

    public Block getBlock() {
        return block;
    }
    
    public Minecart getMinecart(){
        return minecart;
    }
}
