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


import org.bukkit.Material;
import org.bukkit.block.Block;

public abstract class VehicleCollectEvent implements TweakEvent {
    private Material type;
    private Block block;
    public VehicleCollectEvent(Material type, Block block){
        this.type = type;
        this.block = block;
    }
    public Block getBlock() {
        return block;
    }
    public Material getType() {
        return type;
    }
}
