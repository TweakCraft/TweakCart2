package net.tweakcraft.tweakcart.api.event;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;

public abstract class VehicleCollectEvent {
    private Material type;
    private Block block;
    public VehicleCollectEvent(Material type, Block block){
        this.type = type;
        this.block = block;
    }
    public Block getBlock() {
        return block;
    }
    public Material getType() {
        return type;
    }
}
