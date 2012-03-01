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

package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;

public class VehicleSignEvent implements TweakEvent {
    private final Minecart minecart;
    private final Direction direction;
    private final Sign sign;
    private final String keyword;

    public VehicleSignEvent(final Minecart c, final Direction d, final Sign s, final String kw) {
        minecart = c;
        direction = d;
        sign = s;
        keyword = kw;
    }

    public Sign getSign() {
        return sign;
    }

    public Minecart getMinecart() {
        return minecart;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getKeyword() {
        return keyword;
    }
}
