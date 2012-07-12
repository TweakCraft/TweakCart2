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

import net.tweakcraft.tweakcart.TweakCart;
import org.bukkit.Material;

import java.util.Arrays;

/**
 * @author Edoxile
 */
public class IntMap {
    public static final int materialSize = Material.values().length;
    public static final int mapSize = materialSize + 74;
    private int[] mapData;

    public IntMap() {
        mapData = new int[mapSize];
    }

    private IntMap(int[] data) {
        if (data.length != (mapSize)) {
            mapData = new int[mapSize];
        } else {
            mapData = data;
        }
    }

    public void clear(){
        mapData = new int[mapSize];
    }

    public static boolean isAllowedMaterial(int id, byte data) {
        int intLocation = getIntIndex(id, data);
        return intLocation != -1;
    }

    public int getInt(int id, byte data) {
        int intLocation = getIntIndex(id, data);

        if (intLocation == -1 || intLocation >= mapSize) {
            return 0;
        }

        return mapData[intLocation];
    }

    public int getInt(Material m, byte data) {
        int intLocation = getIntIndex(m, data);

        if (intLocation == -1) {
            return 0;
        }

        return mapData[intLocation];
    }

    public boolean setInt(Material m, byte data, int value) {
        return setInt(m.getId(), data, value);
    }

    public boolean setInt(int id, byte data, int value) {
        if (hasDataValue(id) && data == (byte) -1) {
            setDataRange(id, (byte) 0, (byte) 15, value);
        } else {
            int intLocation = getIntIndex(id, data);
            if (intLocation == -1) {
                return false;
            }
            mapData[intLocation] = value;
        }
        return true;
    }

    public static int getIntIndex(int id, byte data) {
        return getIntIndex(Material.getMaterial(id), data);
    }

    private static int getIntIndex(Material m, byte data) {
        if (m == null) {
            return -1;
        }
        switch (data) {
            case 0:
                return m.ordinal();
            default:
                //TODO: reorder list
                switch (m) {
                    case SAPLING:
                        if (data < 5)
                            return materialSize + (int) data;
                        else
                            return -1;
                    case LOG:
                        if (data < 5)
                            return materialSize + (int) data + 4;
                        else
                            return -1;
                    case LEAVES:
                        if (data < 5)
                            return materialSize + (int) data + 8;
                        else
                            return -1;
                    case WOOL:
                        if (data < 16)
                            return materialSize + (int) data + 25;
                        else
                            return -1;
                    case INK_SACK:
                        if (data < 16)
                            return materialSize + (int) data + 40;
                        else
                            return -1;
                    case COAL:
                        if (data < 2)
                            return materialSize + (int) data + 55;
                        else
                            return -1;
                    case STEP:
                        if (data < 7)
                            return materialSize + (int) data + 56;
                        else
                            return -1;
                    case LONG_GRASS:
                        if (data < 3)
                            return materialSize + (int) data + 62;
                        else
                            return -1;
                    case WOOD:
                        if (data < 4)
                            return materialSize + (int) data + 65;
                        else
                            return -1;
                    case SMOOTH_BRICK:
                        if (data < 4)
                            return materialSize + (int) data + 68;
                        else
                            return -1;
                    case SANDSTONE:
                        if (data < 3)
                            return materialSize + (int) data + 70;
                    default:
                        return m.ordinal();
                }
        }


    }

    private boolean hasDataValue(int id) {
        switch (id) {
            case 5:
            case 6:
            case 17:
            case 18:
            case 24:
            case 31:
            case 35:
            case 44:
            case 98:
            case 263:
            case 351:
                return true;
            default:
                return false;
        }
    }

    @Deprecated
    public void combine(IntMap otherMap) {
        for (int index = 0; index < mapData.length; index++) {
            if (otherMap.mapData[index] != 0)
                mapData[index] = otherMap.mapData[index];
        }
    }

    public boolean setRange(int startId, byte startData, int endId, byte endData, int value) {
        // System.out.println("setRange("+startId+","+startData+","+endId+","+endData+","+value+");");
        if (startData < -1 || endData < -1 || startId > endId
                || (startData > 0 && !hasDataValue(startId)) || (endData > 0 && !hasDataValue(endId))
                || !isAllowedMaterial(startId, startData) || !isAllowedMaterial(endId, endData)) {
            TweakCart.log("Requirements: {start [" + startId + "," + startData + "]" + "; end [" + endId + "," + endData + "]}");
            return false;
        }
        if (startId < endId) {
            if (startData >= 0 && endData >= 0) {
                setDataRange(startId, startData, (byte) 15, value);
                // startId++;
                setDataRange(endId, (byte) 0, endData, value);
                // endId--;
            } else if (startData == -1 && endData >= 0) {
                setDataRange(endId, (byte) 0, endData, value);
                // endId--;
            } else if (startData >= 0 && endData == -1) {
                setDataRange(startId, startData, (byte) 15, value);
                // startId++;
            }
            while (startId <= endId) {
                if (hasDataValue(startId)) {
                    setDataRange(startId, (byte) 0, (byte) 15, value);
                } else {
                    setInt(startId, (byte) 0, value);
                }
                do {
                    startId++;
                } while (!isAllowedMaterial(startId, (byte) 0));
            }
            return true;
        } else if (startId == endId) {
            if (startData < endData && hasDataValue(startId)) {
                setDataRange(startId, startData, endData, value);
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    private boolean setDataRange(int id, byte start, byte end, int amount) {
        if (!hasDataValue(id)) {
            return false;
        }
        for (byte data = start; data <= end; data++) {
            int key = getIntIndex(id, data);
            if (key == -1) {
                break;
            }
            mapData[key] = amount;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof IntMap) {
            IntMap otherMap = (IntMap) other;
            for (int index = 0; index < mapData.length; index++) {
                if (mapData[index] != otherMap.mapData[index])
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mapData);
    }

    @Override
    public IntMap clone() throws CloneNotSupportedException {
        super.clone();
        return new IntMap(mapData.clone());
    }

    @Override
    public String toString() {
        String str = "{\n";
        for (int i = 0; i < mapData.length; i++) {
            if (mapData[i] != 0) {
                str += " [" + i + "] -> " + mapData[i] + "\n";
            }
        }
        str += "}";
        return str;
    }

    public void fillAll() {
        for (int i = 0; i < mapData.length; i++) {
            mapData[i] = Integer.MAX_VALUE;
        }
    }
}