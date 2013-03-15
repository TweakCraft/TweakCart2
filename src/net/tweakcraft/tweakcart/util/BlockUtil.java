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

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class BlockUtil {

    public static Set<Sign> getSignLocationAround(Block toBlock, Direction cartDriveDirection) {
        Set<Sign> signList = new HashSet<Sign>();

        if(isSign(toBlock.getRelative(BlockFace.UP)))
            signList.add((Sign)toBlock.getRelative(BlockFace.UP).getState());

        switch (cartDriveDirection) {
            case NORTH:
            case SOUTH:
                if(isSign(toBlock.getRelative(BlockFace.SOUTH)))
                    signList.add((Sign)toBlock.getRelative(BlockFace.SOUTH).getState());
                if(isSign(toBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN)))
                    signList.add((Sign)toBlock.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).getState());
                if(isSign(toBlock.getRelative(BlockFace.NORTH)))
                    signList.add((Sign)toBlock.getRelative(BlockFace.NORTH).getState());
                if(isSign(toBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN)))
                    signList.add((Sign)toBlock.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN).getState());
                break;
            case EAST:
            case WEST:
                if(isSign(toBlock.getRelative(BlockFace.WEST)))
                    signList.add((Sign)toBlock.getRelative(BlockFace.WEST).getState());
                if(isSign(toBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN)))
                    signList.add((Sign)toBlock.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN).getState());
                if(isSign(toBlock.getRelative(BlockFace.EAST)))
                    signList.add((Sign)toBlock.getRelative(BlockFace.EAST).getState());
                if(isSign(toBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN)))
                    signList.add((Sign)toBlock.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN).getState());
                break;
        }

        return signList;
    }

    public static boolean isRailBlock(Block b) {
        return b.getTypeId() == Material.POWERED_RAIL.getId() || b.getTypeId() == Material.DETECTOR_RAIL.getId() || b.getTypeId() == Material.RAILS.getId();
    }

    public static boolean isSign(Block b) {
        return b.getTypeId() == Material.SIGN_POST.getId() || b.getTypeId() == Material.WALL_SIGN.getId();
    }
}
