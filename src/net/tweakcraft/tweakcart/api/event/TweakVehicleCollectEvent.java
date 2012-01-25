package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class TweakVehicleCollectEvent extends VehicleBlockEvent implements CancellableVehicleEvent{
    private boolean isCancelled = false;
    private Material vehicletype;
    
    public TweakVehicleCollectEvent(Minecart c, Direction dir, Block b) {
        super(c,  dir, b);
        // TODO Auto-generated constructor stub
    }
    
    public Material getVehicleType(){
        return vehicletype;
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
