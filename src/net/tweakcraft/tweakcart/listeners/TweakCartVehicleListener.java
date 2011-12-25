package net.tweakcraft.tweakcart.listeners;

import net.tweakcraft.tweakcart.TweakPluginManager;
import net.tweakcraft.tweakcart.api.TweakCartSignEvent;
import net.tweakcraft.tweakcart.model.Direction;
import net.tweakcraft.tweakcart.util.MathUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class TweakCartVehicleListener extends VehicleListener {
    private TweakPluginManager manager = TweakPluginManager.getInstance();


    @Override
    public void onVehicleMove(VehicleMoveEvent event) {
        if (MathUtil.isSameBlock(event.getFrom(), event.getTo())) {
            return;
        } else {
            Block toBlock = event.getTo().getBlock();
            Direction cartDriveDirection = Direction.getDirection(event.getFrom(), event.getTo());

            //we hebben niets te doen met blocks die geen rails zijn
            if (!isRailBlock(toBlock)) return;

            List<Sign> signBlockList;
            if ((signBlockList = getSignLocationAround(toBlock, cartDriveDirection)).size() != 0) {
                //Woei, we hebben bordjes gevonden
                for (Sign sign : signBlockList) {
                    String keyword = sign.getLine(0);
                    //TODO: fix the null
                    manager.callSignEvent(TweakCartSignEvent.VehiclePassesSignEvent, keyword, null);
                }
            }
        }
    }

    @Override
    public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {
        Block collideBlock = event.getBlock();
        if (collideBlock.getType() == Material.SIGN || collideBlock.getType() == Material.SIGN_POST) {
            Sign signBlock = (Sign) collideBlock;
            String keyword = signBlock.getLine(0);
            //TODO: fix the null
            manager.callSignEvent(TweakCartSignEvent.VehicleCollidesWithSignEvent, keyword, null);
        }
    }

    ;

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
