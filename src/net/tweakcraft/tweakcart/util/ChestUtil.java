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
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChestUtil {
    public static int getAmountInChest(Chest c, Material type) {
        Inventory i = c.getInventory();
        ItemStack[] s = i.getContents();
        int amount = 0;
        for (ItemStack stack : s) {
            if (stack.getType() == type) {
                amount += stack.getAmount();
            }
        }
        return amount;
    }

    public static List<Chest> getChestsAroundBlock(Block block, int sw) {
        int nsw = -sw;
        List<Chest> chestList = new ArrayList<Chest>();
        for (int dx = nsw; dx <= sw; dx++) {
            for (int dy = nsw; dy <= sw; dy++) {
                for (int dz = nsw; dz <= sw; dz++) {
                    if (block.getRelative(dx, dy, dz).getTypeId() == Material.CHEST.getId()) {
                        chestList.add((Chest) block.getRelative(dx, dy, dz).getState());
                        chestList = getChestsAdjacent(chestList, block, dx, dy, dz);
                    }
                }
            }
        }

        return chestList;
    }

    private static List<Chest> getChestsAdjacent(List<Chest> chestList, Block block, int x, int y, int z) {

        if ((x == 1 || x == -1) && (z == 1 || z == -1)) {
            if (block.getRelative(x + x, y, z).getTypeId() == Material.CHEST.getId()) {
                chestList.add((Chest) block.getRelative(x + x, y, z).getState());
            } else if (block.getRelative(x, y, z + z).getTypeId() == Material.CHEST.getId()) {
                chestList.add((Chest) block.getRelative(x, y, z + z).getState());
            }
        } else if (x == 1 || x == -1) {
            if (block.getRelative(x + x, y, z).getTypeId() == Material.CHEST.getId()) {
                chestList.add((Chest) block.getRelative(x + x, y, z).getState());
            }
        } else if (z == 1 || z == -1) {
            if (block.getRelative(x, y, z + z).getTypeId() == Material.CHEST.getId()) {
                chestList.add((Chest) block.getRelative(x, y, z + z).getState());
            }
        }

        return chestList;
    }
}
