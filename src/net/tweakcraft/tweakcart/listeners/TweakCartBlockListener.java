/**
 * TODO: Is this really necessary?
 */

package net.tweakcraft.tweakcart.listeners;

import net.tweakcraft.tweakcart.TweakPluginManager;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class TweakCartBlockListener extends BlockListener {
    private TweakPluginManager manager = TweakPluginManager.getInstance();

    //TODO: need hooks for this?
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
    }

    //TODO: implement this
    public void onBlockDispense(BlockDispenseEvent event){

    }
}
