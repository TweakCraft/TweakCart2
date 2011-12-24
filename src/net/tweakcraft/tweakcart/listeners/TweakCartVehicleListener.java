package net.tweakcraft.tweakcart.listeners;

import java.util.ArrayList;
import java.util.List;

import net.tweakcraft.tweakcart.api.Direction;
import net.tweakcraft.tweakcart.api.SignLocation;
import net.tweakcraft.tweakcart.plugin.AbstractPlugin;
import net.tweakcraft.tweakcart.util.MathUtil;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;


public class TweakCartVehicleListener extends VehicleListener{
    private static List<AbstractPlugin> vehicleSignPassInterested = new ArrayList<AbstractPlugin>();
    
    
    public static void addInterestInSignPass(AbstractPlugin a){
        vehicleSignPassInterested.add(a);
    }
    
    @Override
    public void onVehicleMove(VehicleMoveEvent event){
        if (MathUtil.isSameBlock(event.getFrom(), event.getTo())) {
            return;
        }
        else{
            Block toBlock = event.getTo().getBlock();
            Direction cartDriveDirection = Direction.getDirection(event.getFrom(), event.getTo());
            if(getSignLocationAround(toBlock, cartDriveDirection).size() != 0){
                //Woei, we hebben bordjes gevonden
            }
        }
    }

    private List<Sign> getSignLocationAround(Block toBlock, Direction cartDriveDirection) {
        //Boven
        List<Block> blockList = new ArrayList<Block>();
        List<Sign> signList = new ArrayList<Sign>();
        blockList.add(toBlock.getRelative(BlockFace.UP));
        
        switch(cartDriveDirection){
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
        
        for(Block b: blockList){
            if(b instanceof Sign){
                Sign s = (Sign) b;
                signList.add(s);
            }
        }
        
        return signList;
    }
}
