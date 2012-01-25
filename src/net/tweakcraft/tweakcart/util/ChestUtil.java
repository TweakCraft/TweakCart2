package net.tweakcraft.tweakcart.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestUtil {
    /**
     * Returns the remainder of items that could not be put in
     * @param inv
     * @param type
     * @param amount
     * @return
     */
    public static int putItemInInventory(Inventory inv, Material type, int amount){
        int slot;
        int stackSize = type.getMaxStackSize();
        while(amount != 0){
            if((slot = inv.first(type)) != -1){
                ItemStack slotStack = inv.getItem(slot);
                int totalAmount = slotStack.getAmount() + amount;
                int roomInSlot = stackSize - slotStack.getAmount();
                if(roomInSlot >= amount){
                    slotStack.setAmount(totalAmount);
                    amount = 0;
                }else{
                    slotStack.setAmount(stackSize);
                    amount -= roomInSlot;
                }
            }else if((slot = inv.firstEmpty()) != -1){
                ItemStack slotStack = new ItemStack(type);
                if(amount < stackSize){
                    slotStack.setAmount(amount);
                    amount = 0;
                }else{
                    slotStack.setAmount(stackSize);
                    amount -= stackSize;
                }
                inv.setItem(slot, slotStack);
            }else{
                return amount;
            }
        }
        return 0;
    }
    
    public static int getAmountInInventory(Inventory inv, Material type){
        ItemStack[] invContent = inv.getContents();
        int amount = 0;
        for(ItemStack stack : invContent){
            if(stack.getType() == type){
                amount += stack.getAmount();
            }
        }
        return amount;
    }
}
