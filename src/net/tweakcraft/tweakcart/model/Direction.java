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

package net.tweakcraft.tweakcart.model;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public enum Direction {
    NORTH(-1, 0, 0),
    EAST(0, 0, -1),
    SOUTH(1, 0, 0),
    WEST(0, 0, 1),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    NORTH_EAST(NORTH, EAST),
    NORTH_WEST(NORTH, WEST),
    SOUTH_EAST(SOUTH, EAST),
    SOUTH_WEST(SOUTH, WEST),
    SELF(0, 0, 0);

    private int modY;
    private int modZ;
    private int modX;

    private Direction(final int modX, final int modY, final int modZ) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }

    private Direction(final Direction dir1, final Direction dir2) {
        this.modX = dir1.getModX() + dir2.getModX();
        this.modY = dir1.getModY() + dir2.getModY();
        this.modZ = dir1.getModZ() + dir2.getModZ();
    }

    public int getModZ() {
        return modZ;
    }

    public int getModX() {
        return modX;
    }

    public int getModY() {
        return modY;
    }
    

    public static Direction getDirection(Location from, Location to) {
        if (from.getBlockX() == to.getBlockX() + 1) {
            return Direction.NORTH;
        } else if (from.getBlockX() + 1 == to.getBlockX()) {
            return Direction.SOUTH;
        } else if (from.getBlockZ() == to.getBlockZ() + 1) {
            return Direction.EAST;
        } else if (from.getBlockZ() + 1 == to.getBlockZ()) {
            return Direction.WEST;
        }
        return Direction.SELF;
    }

    public Vector mod(double mod) {
        //TODO: shameless copy paste from old codebase, is this correct?
        //Yes this is correct
        return new Vector(modX * mod, modY * mod, modZ * mod);
    }
}
