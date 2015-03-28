package net.tweakcraft.tweakcart.api.event;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockRedstoneEvent;

/**
 * Created by nick on 26/03/2015.
 */
public class TweakVehicleBlockRedstoneEvent implements TweakEvent {
    private BlockRedstoneEvent blockRedstoneEvent;

    public TweakVehicleBlockRedstoneEvent(BlockRedstoneEvent blockRedstoneEvent) {
        this.blockRedstoneEvent = blockRedstoneEvent;
    }

    public int getOldCurrent(){
        return blockRedstoneEvent.getOldCurrent();
    }

    public int getNewCurrent(){
        return blockRedstoneEvent.getNewCurrent();
    }

    public void setNewCurrent(int current) {
        blockRedstoneEvent.setNewCurrent(current);
    }

    public Block getBlock(){
        return blockRedstoneEvent.getBlock();
    }
}
