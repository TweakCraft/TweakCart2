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

import net.tweakcraft.tweakcart.model.IntMap;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Edoxile
 *         Here comes the black magic
 */
public class InventoryManager {

    public static int[] moveContainerContents(Inventory cart, Inventory chest, IntMap[] maps) {
        if (maps.length == 2) {
            int data[] = moveContainerContents(cart, chest, maps[0]);
            if (data[2] == 64) {
                return data;
            } else {
                return moveContainerContents(chest, cart, maps[1]);
            }
        } else {
            return null;
        }
    }

    //returns state of FROM-container {-1=empty,0=space left,1=full}
    public static int[] moveContainerContents(Inventory iFrom, Inventory iTo, IntMap map) {
        System.out.println("Moving!");
        System.out.println(map);
        ItemStack[] from = iFrom.getContents();
        ItemStack[] to = iTo.getContents();

        //Compile the return data from these four bytes
        int[] returnData = new int[3];

        fromLoop:
        for (int i = 0; i < from.length; i++) {
            ItemStack fStack = from[i];
            if(fStack == null) continue;
            int maxAmountToMove = map.getInt(fStack.getType(), (byte) fStack.getDurability());
            if (fStack == null || maxAmountToMove == 0) {
                returnData[0]++;
                continue;
            }
            for (int j = 0; j < to.length; j++) {
                ItemStack tStack = to[j];
                if (tStack == null){
                    //Als de toStack leeg is, dan zetten we er een fromstack neer
                    //hier gaat iets mis, hier wordt namelijk niets in de intmap aangepast
                    //en gewoon een stack in de nieuwe inventory geduwt, ongeacht
                    //de hoeveelheid die in de intmap staat
                    
                    if(fStack.getAmount() < maxAmountToMove){
                        to[j] = fStack;
                        from[i] = null;
                        returnData[0]++;
                        map.setInt(fStack.getType(), (byte) fStack.getData().getData(), maxAmountToMove - fStack.getAmount());
                        continue fromLoop;
                    }else{
                        to[j] = new ItemStack(fStack.getType(), maxAmountToMove, fStack.getDurability(),  fStack.getData().getData());
                        fStack.setAmount(fStack.getAmount() - maxAmountToMove);
                        map.setInt(fStack.getType(), fStack.getData().getData(), 0);
                        
                    }
                    
                } else if (tStack.getAmount() == 64) {
                    returnData[1]++;
                    continue;
                } else if (fStack.getTypeId() == tStack.getTypeId() && fStack.getDurability() == tStack.getDurability() && tStack.getEnchantments().isEmpty()) {
                    //And now the magic begins
                    //First check if the stackAmount is smaller then the max amount to move
                    if (fStack.getAmount() <= maxAmountToMove) {
                        int total = fStack.getAmount() + tStack.getAmount();
                        if (total > 64) {
                            map.setInt(tStack.getType(), (byte) tStack.getDurability(), map.getInt(tStack.getType(), (byte) tStack.getDurability()) - (64 - tStack.getAmount()));
                            tStack.setAmount(64);
                            fStack.setAmount(total - 64);
                            returnData[1]++;
                        } else {
                            map.setInt(tStack.getType(), (byte) tStack.getDurability(), map.getInt(tStack.getType(), (byte) tStack.getDurability()) - fStack.getAmount());
                            tStack.setAmount(total);
                            from[i] = null;
                            returnData[0]++;
                            continue fromLoop;
                        }
                    } else {
                        //Otherwise, run some other code
                        int total = maxAmountToMove + tStack.getAmount();
                        int stableAmount = fStack.getAmount() - maxAmountToMove;
                        if (total > 64) {
                            map.setInt(tStack.getType(), (byte) tStack.getDurability(), map.getInt(tStack.getType(), (byte) tStack.getDurability()) - (64 - tStack.getAmount()));
                            maxAmountToMove -= 64 - tStack.getAmount();
                            tStack.setAmount(64);
                            fStack.setAmount(total - 64 + stableAmount);
                            returnData[1]++;
                        } else {
                            map.setInt(tStack.getType(), (byte) tStack.getDurability(), map.getInt(tStack.getType(), (byte) tStack.getDurability()) - maxAmountToMove);
                            tStack.setAmount(total);
                            //TODO: I think this part is never run (because stableAmount is always > 0). Check?
                            //if (stableAmount > 0) {
                            from[i].setAmount(stableAmount);
                            //} else {
                            //    from[i] = null;
                            //}
                            continue fromLoop;
                        }
                    }
                } else {
                    returnData[2]++;
                    continue;
                }
                to[j] = tStack;
            }
            from[i] = fStack;
        }
        iFrom.setContents(from);
        iTo.setContents(to);
        return returnData;
    }

    public static ItemStack[] putContents(Inventory iTo, ItemStack... stackFrom) {
        ItemStack[] stackTo = iTo.getContents();
        fromLoop:
        for (int i = 0; i < stackFrom.length; i++) {
            ItemStack fromStack = stackFrom[i];
            if (fromStack == null) {
                continue;
            } else {
                for (int j = 0; j < stackTo.length; j++) {
                    //Voor de inventory To
                    //Dit is de huidige stack
                    ItemStack toStack = stackTo[j];
                    if (toStack == null) {
                        //Als de huidige stack leeg is
                        //Zet dan de nieuwe stack daarneer
                        stackTo[j] = fromStack;
                        //En maak de nieuwe stack leeg
                        stackFrom[i] = null;
                        continue fromLoop;
                    } else if (fromStack.getTypeId() == toStack.getTypeId() && fromStack.getDurability() == toStack.getDurability() && toStack.getEnchantments().isEmpty()) {
                        //als het dezelfde stacks zijn, bereken
                        //dan het totaal
                        int total = fromStack.getAmount() + toStack.getAmount();
                        if (total > 64) {
                            //als het totaal groter is dan 64
                            //zet dan tostack op 64
                            toStack.setAmount(64);
                            //en de fromstack op het totaal minus 64 (de rest dus)
                            fromStack.setAmount(total - 64);
                        } else {
                            toStack.setAmount(total);
                            int remainder = total - 64;
                            if (remainder <= 0) {
                                stackFrom[i] = null;
                                stackTo[j] = toStack;
                                continue fromLoop;
                            } else {
                                fromStack.setAmount(remainder);
                            }
                        }
                    } else {
                        continue;
                    }
                    stackTo[j] = toStack;
                }
            }
            stackFrom[i] = fromStack;
        }
        iTo.setContents(stackTo);
        return stackFrom;
    }

    public static boolean isEmpty(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            if (stack != null && stack.getAmount() != 0) {
                return false;
            }
        }
        return true;
    }
}