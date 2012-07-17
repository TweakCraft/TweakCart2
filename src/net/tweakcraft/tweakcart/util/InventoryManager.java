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

        if (maps == null) return null;
        if (cart == null) {
            System.out.println("Cart was null!");
            return null;
        }
        if (chest == null) {
            System.out.println("Chest was null!");
            return null;
        }

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

    //returns state of containers[from {empty slots}][to {full slots}][non matching stacks?]
    public static int[] moveContainerContents(Inventory iFrom, Inventory iTo, IntMap map) {
        ItemStack[] from = iFrom.getContents();
        ItemStack[] to = iTo.getContents();

        //Compile the return data from these three bytes
        int[] returnData = new int[3];

        for (int fromIndex = 0; fromIndex < from.length; fromIndex++) {
            ItemStack fStack = from[fromIndex];
            if (fStack == null) continue;
            int maxAmountToMove = map.getInt(fStack.getType(), fStack.getDurability());
            if (fStack == null || maxAmountToMove == 0) {
                returnData[0]++;
                continue;
            }
            for (int toIndex = 0; toIndex < to.length; toIndex++) {
                ItemStack tStack = to[toIndex];
				/* Extra check op maxAmountToMove */
				if(maxAmountToMove == 0)
				{
					break;
				}
                if (tStack == null) {
                    if (fStack.getAmount() <= maxAmountToMove) {
                        to[toIndex] = fStack;
                        fStack = null;
                        returnData[0]++;
                        map.setInt(
                            to[toIndex].getType(),
                            to[toIndex].getDurability(),
                            maxAmountToMove - to[toIndex].getAmount()
                        );
                        break;
                    } else {
                        //TODO: check if durability & data is the same in this case
                        to[toIndex] = new ItemStack(
                                fStack.getType(),
                                maxAmountToMove,
                                fStack.getDurability()
                        );
                        fStack.setAmount(fStack.getAmount() - maxAmountToMove);
						map.setInt(
                            fStack.getType(),
                            fStack.getDurability(),
                            0
                        );
                        break;
                    }
                } else if (tStack.getAmount() == 64) {
                    returnData[1]++;
                    continue;
                } else if (fStack.getTypeId() == tStack.getTypeId() && fStack.getDurability() == tStack.getDurability() && tStack.getEnchantments().equals(fStack.getEnchantments())) {
                    //And now the magic begins
                    //First check if the stackAmount is smaller then the max amount to move
                    if (fStack.getAmount() <= maxAmountToMove) {
                        int total = fStack.getAmount() + tStack.getAmount();
                        if (total > 64) {
                            map.setInt(
                                    tStack.getType(),
                                    tStack.getDurability(),
                                    map.getInt(tStack.getType(), tStack.getDurability()) - (64 - tStack.getAmount())
                            );
                            tStack.setAmount(64);
                            fStack.setAmount(total - 64);
                            returnData[1]++;
                        } else {
                            map.setInt(
                                    tStack.getType(),
                                    tStack.getDurability(),
                                    map.getInt(tStack.getType(), tStack.getDurability()) - fStack.getAmount()
                            );
                            tStack.setAmount(total);
                            fStack = null;
                            returnData[0]++;
                            break;
                        }
                    } else {
                        //Otherwise, run some other code
                        int total = maxAmountToMove + tStack.getAmount();
                        int stableAmount = fStack.getAmount() - maxAmountToMove;
                        if (total > 64) {
                            map.setInt(
                                    tStack.getType(),
                                    tStack.getDurability(),
                                    map.getInt(tStack.getType(), tStack.getDurability()) - (64 - tStack.getAmount())
                            );
                            maxAmountToMove -= 64 - tStack.getAmount();
                            tStack.setAmount(64);
                            fStack.setAmount(total - 64 + stableAmount);
                            returnData[1]++;
                        } else {
                            map.setInt(
                                    tStack.getType(),
                                    tStack.getDurability(),
                                    map.getInt(tStack.getType(), tStack.getDurability()) - maxAmountToMove
                            );
                            tStack.setAmount(total);
                            fStack.setAmount(stableAmount);
                            break;
                        }
                    }
                } else {
                    returnData[2]++;
                    continue;
                }
                to[toIndex] = tStack;
            }
            from[fromIndex] = fStack;
        }
        iFrom.setContents(from);
        iTo.setContents(to);
        return returnData;
    }

    public static ItemStack[] putContents(Inventory iTo, ItemStack... stackFrom) {
        ItemStack[] stackTo = iTo.getContents();
        fromLoop:
        for (int fromIndex = 0; fromIndex < stackFrom.length; fromIndex++) {
            ItemStack fromStack = stackFrom[fromIndex];
            if (fromStack == null) {
                continue;
            } else {
                for (int toIndex = 0; toIndex < stackTo.length; toIndex++) {
                    ItemStack toStack = stackTo[toIndex];
                    if (toStack == null) {
                        stackTo[toIndex] = fromStack;
                        stackFrom[fromIndex] = null;
                        continue fromLoop;
                    } else if (fromStack.getTypeId() == toStack.getTypeId() && fromStack.getDurability() == toStack.getDurability() && toStack.getEnchantments().isEmpty()) {
                        int total = fromStack.getAmount() + toStack.getAmount();
                        if (total > 64) {
                            toStack.setAmount(64);
                            fromStack.setAmount(total - 64);
                        } else {
                            toStack.setAmount(total);
                            int remainder = total - 64;
                            if (remainder <= 0) {
                                stackFrom[fromIndex] = null;
                                stackTo[toIndex] = toStack;
                                continue fromLoop;
                            } else {
                                fromStack.setAmount(remainder);
                            }
                        }
                    } else {
                        continue;
                    }
                    stackTo[toIndex] = toStack;
                }
            }
            stackFrom[fromIndex] = fromStack;
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