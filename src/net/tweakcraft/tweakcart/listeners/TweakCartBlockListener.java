/**
 * TODO: Is this really necessary?
 */

package net.tweakcraft.tweakcart.listeners;

import net.tweakcraft.tweakcart.TweakPluginManager;
import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleDispenseEvent;
import net.tweakcraft.tweakcart.model.Direction;
import net.tweakcraft.tweakcart.util.VehicleUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class TweakCartBlockListener extends BlockListener {
    private TweakPluginManager manager = TweakPluginManager.getInstance();

    //TODO: need hooks for this?
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
    }

    //TODO: implement this
    public void onBlockDispense(BlockDispenseEvent event) {
        Material type = event.getItem().getType();
        Dispenser disp = (Dispenser) event.getBlock();
        switch (type) {
            case MINECART:
            case STORAGE_MINECART:
            case POWERED_MINECART:
                Block b = event.getBlock();
                Direction d = Direction.getDirection(new Location(b.getWorld(), 0, 0, 0), event.getVelocity().toLocation(b.getWorld()));
                //Tjongejonge was het echt nodig om ook een locaal event te maken :p
                if (!manager.callCancelableEvent(TweakCartEvent.Block.VehicleDispenseEvent, new TweakVehicleDispenseEvent(null, d, b, type))){
                    VehicleUtil.spawnCartFromDispenser(disp, type);
                } //haakjes voor de duidelijkheid
                break;
        }
    }
}
