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
        ItemStack[] from = iFrom.getContents();
        ItemStack[] to = iTo.getContents();

        //Compile the return data from these four bytes
        int[] returnData = new int[3];

        fromLoop:
        for (int i = 0; i < from.length; i++) {
            ItemStack fStack = from[i];
            int maxAmountToMove = map.getInt(fStack.getType(), (byte) fStack.getDurability());
            if (fStack == null || maxAmountToMove == 0) {
                returnData[0]++;
                continue;
            }
            for (int j = 0; j < to.length; j++) {
                ItemStack tStack = to[j];
                if (tStack == null) {
                    to[j] = fStack;
                    from[i] = null;
                    returnData[0]++;
                    continue fromLoop;
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
        return returnData;
    }

    public static ItemStack[] putContents(Inventory iTo, ItemStack... sFrom) {
        ItemStack[] sTo = iTo.getContents();
        fromLoop:
        for (int i = 0; i < sFrom.length; i++) {
            ItemStack fStack = sFrom[i];
            if (fStack == null) {
                continue;
            } else {
                for (int j = 0; j < sTo.length; j++) {
                    ItemStack tStack = sTo[j];
                    if (tStack == null) {
                        sTo[j] = fStack;
                        sFrom[i] = null;
                        continue fromLoop;
                    } else if (fStack.getTypeId() == tStack.getTypeId() && fStack.getDurability() == tStack.getDurability() && tStack.getEnchantments().isEmpty()) {
                        int total = fStack.getAmount() + tStack.getAmount();
                        if (total > 64) {
                            tStack.setAmount(64);
                            fStack.setAmount(total - 64);
                        } else {
                            tStack.setAmount(total);
                            int remainder = total - 64;
                            if (remainder == 0) {
                                sFrom[i] = null;
                                sTo[j] = tStack;
                                continue fromLoop;
                            } else {
                                fStack.setAmount(remainder);
                            }
                        }
                    } else {
                        continue;
                    }
                    sTo[j] = tStack;
                }
            }
            sFrom[i] = fStack;
        }
        return sFrom;
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