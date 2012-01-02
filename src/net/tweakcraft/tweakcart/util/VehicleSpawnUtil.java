package net.tweakcraft.tweakcart.util;

import net.tweakcraft.tweakcart.model.Direction;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.Material;


public class VehicleSpawnUtil{
    
	//TODO: should a cart be spawnable on something different then a track?
	//Long live operator overloading
	
    public static boolean spawnCartWithVelocity(Block b, Material type, Direction dir, double velocity){
    	if(isMinecart(type)){
    		Minecart cart;
    		switch(type){
    		case MINECART:
    			cart = b.getWorld().spawn(b.getLocation(), Minecart.class);
                break;
    		case STORAGE_MINECART:
    			cart = b.getWorld().spawn(b.getLocation(), StorageMinecart.class);
                break;
    		case POWERED_MINECART:
    			cart = b.getWorld().spawn(b.getLocation(), PoweredMinecart.class);
                break;
            default:
            	return false;
    		}
    		
    		cart.setVelocity(dir.mod(velocity == Double.MAX_VALUE ? cart.getMaxSpeed() : velocity));
    	}
    	else{
    		return false;
    	}
    	return true;
    }
    
    /**
     * Spawns a cart on a block, with a given direction
     * wont check for rails, just spawns the cart
     * @param b
     * @param type
     * @param dir
     */
    public static boolean spawnCart(Block b, Material type, Direction dir){
    	return spawnCartWithVelocity(b, type, dir, Double.MAX_VALUE);
    }
    
    public static boolean spawnCartFromDispenser(Dispenser d, Material type, double velocity){
        Block track = null;
        Direction dir = null;
    	switch (d.getData().getData()) {
        case 0x2:
            track = ((Block) d).getRelative(BlockFace.EAST);
            dir = Direction.EAST;
            break;
        case 0x3:
            track = ((Block) d).getRelative(BlockFace.WEST);
            dir = Direction.WEST;
            break;
        case 0x4:
            track = ((Block) d).getRelative(BlockFace.NORTH);
            dir = Direction.NORTH;
            break;
        case 0x5:
            track = ((Block) d).getRelative(BlockFace.SOUTH);
            dir = Direction.SOUTH;
            break;
    	}
    	return spawnCartWithVelocity(track, type, dir, velocity);
    }
    
    public static boolean spawnCartFromDispenser(Dispenser d, Material type){
    	return spawnCartFromDispenser(d, type, Double.MAX_VALUE);
    }
    
    public static boolean isMinecart(Material type){
    	return type == Material.MINECART || type == Material.POWERED_MINECART || type == Material.STORAGE_MINECART;
    }
    
    
}
