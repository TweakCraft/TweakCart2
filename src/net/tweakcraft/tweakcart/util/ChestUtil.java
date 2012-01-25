/*
 * Copyright (c) 2012.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
