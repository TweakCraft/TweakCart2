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

package net.tweakcraft.tweakcart.permissions;

import net.tweakcraft.tweakcart.api.event.TweakPlayerCollectEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleCollectEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehicleDispenseEvent;

@Deprecated
public interface TweakPermissionsHandler {

    public boolean canVehicleCollect(TweakVehicleCollectEvent event);

    public boolean canDispense(TweakVehicleDispenseEvent event);

    public boolean canSlapCollect(TweakPlayerCollectEvent event);

    public String getName();
}
