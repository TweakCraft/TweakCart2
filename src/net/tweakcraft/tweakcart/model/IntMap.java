package net.tweakcraft.tweakcart.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.bukkit.Material;

public class IntMap {

    private int[] mapData;
    public static final int[] materialIndex = new int[Material.values().length];
    public static final Material[] materialList = Material.values();
    public static final HashMap<Material, HashMap<Integer, Integer>> dataValueMap = new HashMap<Material, HashMap<Integer, Integer>>();
    public static final int mapSize;
    public static final int materialSize = materialList.length;

    static {
        /*
         * dit moet dus nog worden geautomatiseerd
         */
        HashMap<Material, Integer> metaDataList = new HashMap<Material, Integer>();
        metaDataList.put(Material.WOOD, 4);
        metaDataList.put(Material.SAPLING, 4);
        metaDataList.put(Material.LOG, 4);
        metaDataList.put(Material.LEAVES, 15);
        metaDataList.put(Material.SANDSTONE, 3);
        metaDataList.put(Material.GRASS, 3);
        metaDataList.put(Material.WOOL, 16);
        metaDataList.put(Material.DOUBLE_STEP, 6);
        metaDataList.put(Material.STEP, 6);
        //metaDataList.put(Material.WOOD_STAIRS, 4); No longer necessairy
        metaDataList.put(Material.MONSTER_EGG, 3);
        metaDataList.put(Material.SMOOTH_BRICK, 4);
        metaDataList.put(Material.WOOD_DOUBLE_STEP, 4);
        metaDataList.put(Material.WOOD_STEP, 4);
        metaDataList.put(Material.COAL, 2);
        metaDataList.put(Material.INK_SACK, 16);
        Set<Material> materials = metaDataList.keySet();
        //Material[] matList = materials.toArray(new Material[materials.size()]);
        for (Iterator<Material> it = materials.iterator(); it.hasNext();) {
            Material mat = it.next();
            dataValueMap.put(mat, new HashMap<Integer, Integer>());
            for (int meta = 0; meta < metaDataList.get(mat); meta++) {
                dataValueMap.get(mat).put(meta, meta);
            }
        }

        int[] potionArray = new int[]{0, 16, 32, 64, 8192, 8193, 8194, 8195, 8196, 8197, 8198, 8200, 8201, 8202, 8204, 8206, 8225, 8226, 8227, 8228, 8229, 8232, 8233, 8234, 8236, 8257, 8258, 8259, 8260, 8261, 8262, 8264, 8265, 8266, 8268, 8270, 16385, 16386, 16387, 16388, 16389, 16392, 16393, 16394, 16396, 16417, 16418, 16419, 16420, 16421, 16424, 16425, 16426, 16428, 16390, 16398, 16449, 16450, 16451, 16452, 16453, 16454, 16456, 16457, 16458, 16460, 16462};
        dataValueMap.put(Material.getMaterial(373), new HashMap<Integer, Integer>());
        for (int meta = 0; meta < potionArray.length; meta++) {
            dataValueMap.get(Material.getMaterial(373)).put(potionArray[meta], meta);
        }

        int[] eggArray = new int[]{50, 51, 52, 54, 55, 56, 57, 58, 59, 60, 61, 62, 90, 91, 92, 93, 94, 95, 96, 98, 120};
        dataValueMap.put(Material.getMaterial(383), new HashMap<Integer, Integer>());
        for (int meta = 0; meta < eggArray.length; meta++) {
            dataValueMap.get(Material.getMaterial(383)).put(eggArray[meta], meta);
        }

        int offsetX = 0;
        Material m;
        for (int x = 0; x < materialIndex.length; x++) {
            materialIndex[x] = offsetX;
            if (dataValueMap.containsKey(materialList[x])) {
                offsetX += dataValueMap.get(materialList[x]).size();
            } else {
                offsetX++;
            }
        }
        mapSize = offsetX;
    }

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

    public static int getIntIndex(int id, int data) {
        return getIntIndex(Material.getMaterial(id), data);
    }

    public static int getIntIndex(Material m, int data) {
        if (m == null) {
            return -1;
        }
        if (data < 0) {
            data = 0;
        }
        if (hasDataValue(m) && dataValueMap.get(m).containsKey(data)) {
            data = dataValueMap.get(m).get(data);
        } else {
            data = 0;
        }
        return ((materialIndex[m.ordinal()] + data) < mapSize) ? materialIndex[m.ordinal()] + data : -1;
    }

    private static boolean hasDataValue(int id) {
        return hasDataValue(Material.getMaterial(id));
    }

    private static boolean hasDataValue(Material m) {
        return dataValueMap.containsKey(m);
    }

    private static int getMaxDataValue(int id) {
        return getMaxDataValue(Material.getMaterial(id));
    }

    private static int getMaxDataValue(Material m) {
        return dataValueMap.get(m).size();
    }

    public static boolean isAllowedMaterial(Material m, int data) {
        if (m == null) {
            /* TODO: dit moet misschien anders? */
            return false;
        }
        return IntMap.getIntIndex(m, data) != -1;
    }

    public static boolean isAllowedMaterial(int id, int data) {
        if (Material.getMaterial(id) == null) {
            /* TODO: dit moet misschien anders? */
            return false;
        }
        return IntMap.isAllowedMaterial(Material.getMaterial(id), data);
    }

    public int getInt(int id, int data) {
        return getInt(Material.getMaterial(id), data);
    }

    public int getInt(Material m, int data) {
        if (m == null) {
            return 0;
        }
        int intLocation = IntMap.getIntIndex(m, data);

        if (intLocation == -1) {
            return 0;
        }

        return mapData[intLocation];
    }

    public boolean setInt(int id, int data, int value) {
        return setInt(Material.getMaterial(id), data, value);
    }

    public boolean setInt(Material m, int data, int value) {
        if (m == null) {
            return false;
        }
        if (hasDataValue(m) && data == -1) {
            setRange(getIntIndex(m, 0), getIntIndex(m, 0) + getMaxDataValue(m), value);
        } else {
            int intLocation = IntMap.getIntIndex(m, data);
            if (intLocation == -1) {
                return false;
            }
            mapData[intLocation] = value;
        }
        return true;
    }

    /**
     * Sets a range of the IntMap prevents multiple calls to IntMap and back
     */
    public boolean setRange(int startId, int startdata, int endId, int enddata, int value) {
        return setRange(Material.getMaterial(startId), startdata, Material.getMaterial(endId), enddata, value);
    }

    public boolean setRange(Material startM, int startData, Material endM, int endData, int value) {
        if (startM == null || endM == null) {
            return false;
        }
        int startId = startM.getId();
        int endId = endM.getId();
        if (startData < -1 || endData < -1 || startId > endId
                || (startData > 0 && !hasDataValue(startM)) || (endData > 0 && !hasDataValue(endM))
                || !isAllowedMaterial(startM, startData) || !isAllowedMaterial(endId, endData)) {
            return false;
        }
        if (startId < endId) {
            int rangeStart = IntMap.getIntIndex(startM, startData);
            int rangeEnd = IntMap.getIntIndex(endM, endData);
            /* if endData is -1 and the endMaterial has subdata we have to add this */
            if (endData == -1 && hasDataValue(endM)) {
                rangeEnd += getMaxDataValue(endM);
            }
            for (int r = rangeStart; r <= rangeEnd; r++) {
                mapData[r] = value;
            }
            return true;
        } else if (startId == endId) {
            if (startData < endData && hasDataValue(startM)) {
                return setRange(getIntIndex(startM, startData), getIntIndex(startM, endData), value);
            }
            return false;
        } else {
            return false;
        }
    }

    private boolean setRange(int start, int end, int amount) {
        for (int k = start; k <= end; k++) {
            mapData[k] = amount;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof IntMap) {
            IntMap otherMap = (IntMap) other;
            for (int index = 0; index < mapData.length; index++) {
                if (mapData[index] != otherMap.mapData[index]) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Combine two IntMaps, with otherMap having higher priority than this.
     * Please do not use this function, as it is slow.
     *
     * @param otherMap Map to combine with.
     */
    @Deprecated
    public void combine(IntMap otherMap) {
        for (int index = 0; index < mapData.length; index++) {
            if (otherMap.mapData[index] != 0) {
                mapData[index] = otherMap.mapData[index];
            }
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
                str += "    [" + i + "] -> " + mapData[i] + "\n";
            }
        }
        str += "}";
        return str;
    }

    public void fillAll() {
        fillAll(false);
    }

    public void fillAll(boolean negative) {
        int value = negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < mapData.length; i++) {
            mapData[i] = value;
        }

    }
}
