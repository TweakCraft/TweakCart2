package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;

public class TweakVehicleDispenseEvent extends VehicleBlockEvent implements CancelableVehicleEvent{
    Material vehicleType;
    public boolean isCanceled;
    
    public TweakVehicleDispenseEvent(Minecart c, Direction d, Block b, Material vehicle) {
        super(c, d, b);
        vehicleType = vehicle;
    }
    
    public Material getVehicleType(){
        return vehicleType;
    }

    @Override
    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }
}
