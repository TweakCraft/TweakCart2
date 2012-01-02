package net.tweakcraft.tweakcart.listeners;

import net.tweakcraft.tweakcart.TweakPluginManager;
import net.tweakcraft.tweakcart.api.TweakCartEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleBlockCollisionEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleCollidesWithSignEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehiclePassesSignEvent;
import net.tweakcraft.tweakcart.model.Direction;
import net.tweakcraft.tweakcart.util.MathUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class TweakCartVehicleListener extends VehicleListener {
    private TweakPluginManager manager = TweakPluginManager.getInstance();


    @Override
    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Minecart minecart = (Minecart) event.getVehicle();
            if (MathUtil.isSameBlock(event.getFrom(), event.getTo())) {
                return;
            } else {
                Block toBlock = event.getTo().getBlock();
                Direction cartDriveDirection = Direction.getDirection(event.getFrom(), event.getTo());

                //we hebben niets te doen met blocks die geen rails zijn
                //OH ZEKER WEL! Signs bijvoorbeeld worden niet door blockCollision gevonden. Hier zou ik dus de 'collision checks' daarvoor doen.
                if (!isRailBlock(toBlock)) {
                    switch (toBlock.getType()) {
                        case WALL_SIGN:
                        case SIGN_POST:
                            Sign signBlock = (Sign) toBlock;
                            String keyword = signBlock.getLine(0);
                            manager.callEvent(TweakCartEvent.Sign.VehicleCollidesWithSignEvent, keyword, new TweakVehicleCollidesWithSignEvent(minecart, cartDriveDirection, signBlock));
                            break;
                    }
                } else {
                    List<Sign> signBlockList;
                    if ((signBlockList = getSignLocationAround(toBlock, cartDriveDirection)).size() != 0) {
                        //Woei, we hebben bordjes gevonden
                        for (Sign sign : signBlockList) {
                            String keyword = sign.getLine(0);
                            //TODO: fix the null
                            manager.callEvent(TweakCartEvent.Sign.VehiclePassesSignEvent, keyword, new TweakVehiclePassesSignEvent(minecart, cartDriveDirection, sign));
                        }
                    }
                }
            }
        }
    }

    @Override
    //Made a general rule for all blocks. Makes various functions for block collision possible.
    public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            manager.callEvent(TweakCartEvent.Block.VehicleBlockCollisionEvent, new TweakVehicleBlockCollisionEvent((Minecart) event.getVehicle(), Direction.SELF, event.getBlock()));
        }
    }

    /**
     * Adds Signs to a list, signs are searched for in the following pattern
     * _X_
     * XTX
     * XBX
     * Where X is a location to search for a sign, B is the block where the track was placed on,
     * and T is the track itself.
     *
     * @param toBlock
     * @param cartDriveDirection
     * @return a list of all found signs
     */
    private List<Sign> getSignLocationAround(Block toBlock, Direction cartDriveDirection) {
        List<Block> blockList = new ArrayList<Block>();
        List<Sign> signList = new ArrayList<Sign>();
        blockList.add(toBlock.getRelative(BlockFace.UP));

        switch (cartDriveDirection) {
            case NORTH:
            case SOUTH:
                blockList.add(toBlock.getRelative(BlockFace.WEST));
                blockList.add(toBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN));
                blockList.add(toBlock.getRelative(BlockFace.EAST));
                blockList.add(toBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN));
                break;
            case EAST:
            case WEST:
                blockList.add(toBlock.getRelative(BlockFace.NORTH));
                blockList.add(toBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN));
                blockList.add(toBlock.getRelative(BlockFace.SOUTH));
                blockList.add(toBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN));
                break;
        }

        for (Block b : blockList) {
            if (b instanceof Sign) {
                Sign s = (Sign) b;
                signList.add(s);
            }
        }

        return signList;
    }

    private boolean isRailBlock(Block b) {
        return b.getType() == Material.POWERED_RAIL || b.getType() == Material.DETECTOR_RAIL || b.getType() == Material.RAILS;
    }

}
