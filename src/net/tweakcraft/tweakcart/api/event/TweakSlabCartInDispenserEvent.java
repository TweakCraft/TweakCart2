package net.tweakcraft.tweakcart.api.event;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class TweakSlabCartInDispenserEvent extends VehicleCollectEvent implements CancellableVehicleEvent{
    private boolean isCancelled = false;
    
    public TweakSlabCartInDispenserEvent(Material type, Block block) {
        super(type, block);
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }




}
