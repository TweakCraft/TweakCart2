package net.tweakcraft.tweakcart.listeners;

import net.tweakcraft.tweakcart.TweakPluginManager;
import net.tweakcraft.tweakcart.util.VehicleUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class TweakCartPlayerListener extends PlayerListener{
    private TweakPluginManager manager = TweakPluginManager.getInstance();
    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled() || event.getAction() != Action.LEFT_CLICK_BLOCK){
            System.out.println("noes?");
            return;
        }
        if(event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Dispenser && VehicleUtil.isMinecart(event.getItem().getType())){
            Material type = event.getItem().getType();
            Block dispBlock = event.getClickedBlock();
            
            //TODO: call cancellable event
            Dispenser disp = (Dispenser) dispBlock.getState();
            ItemStack inHand = event.getItem();
            if(disp.getInventory().addItem(new ItemStack(type, 1)).size() == 0){
                if(inHand.getAmount() == 1){
                    inHand = null;
                }else{
                    inHand.setAmount(inHand.getAmount()-1);
                }
            
                //TODO: dit netter maken, carts moeten stacken, plus dat het gebruik moet maken van een Inventory framework achtig iets
            
                if(inHand != null){
                    event.getPlayer().getInventory().setItemInHand(inHand);
                }else{
                    System.out.println("oioi");
                    int slot = event.getPlayer().getInventory().getHeldItemSlot();
                    System.out.println("slot is " + slot);
                    event.getPlayer().getInventory().clear(slot);
                }
            }
        }
    }
}
