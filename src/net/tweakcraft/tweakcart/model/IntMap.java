package net.tweakcraft.tweakcart.model;

import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.Material;

public class IntMap
{
	private int[] mapData;
	private final static int[] materialIndex = new int[Material.values().length];
	public final static HashMap<Integer, Byte> dataValueMap = new HashMap<Integer, Byte>();
	public static final int mapSize;
	public static final int materialSize = Material.values().length;

	static
	{
		/*
		 * dit moet dus nog worden geautomatiseerd
		 */
		HashMap<Integer, Byte> tmpMap = new HashMap<Integer, Byte>();
		dataValueMap.put(5, (byte) 4);
		dataValueMap.put(6, (byte) 4);
		dataValueMap.put(17, (byte) 4);
		dataValueMap.put(18, (byte) 4); // 3-4 different blocks?
		dataValueMap.put(24, (byte) 3);
		dataValueMap.put(31, (byte) 3);
		dataValueMap.put(35, (byte) 16);
		dataValueMap.put(43, (byte) 6); // double slabs
		dataValueMap.put(44, (byte) 6);
		dataValueMap.put(53, (byte) 4); // wooden stairs
		dataValueMap.put(97, (byte) 3); // monster egg block
		dataValueMap.put(98, (byte) 4);
		dataValueMap.put(125, (byte) 4); // wooden slabs
		dataValueMap.put(126, (byte) 4); // wooden double slabs
		dataValueMap.put(263, (byte) 2);
		dataValueMap.put(351, (byte) 16);
		dataValueMap.put(373, (byte) 13); // potions
		dataValueMap.put(383, (byte) 21); // spawn eggs

		Material[] materials = Material.values();
		int offsetX = 0;
		for (int x = 0; x < materialIndex.length; x++)
		{
			materialIndex[x] = offsetX;
			if (dataValueMap.containsKey(x))
			{
				offsetX += dataValueMap.get(x);
			}
			else
			{
				offsetX++;
			}
		}
		mapSize = offsetX;
	}

	public IntMap()
	{
		mapData = new int[mapSize];
	}

	private IntMap(int[] data)
	{
		if (data.length != (mapSize))
		{
			mapData = new int[mapSize];
		}
		else
		{
			mapData = data;
		}
	}

	public static boolean isAllowedMaterial(int id, byte data)
	{
		int intLocation = IntMap.getIntIndex(id, data);
		return intLocation != -1;
	}

	public int getInt(int id, byte data)
	{
		int intLocation = IntMap.getIntIndex(id, data);

		if (intLocation == -1 || intLocation >= mapSize)
		{
			return 0;
		}

		return mapData[intLocation];
	}

	public int getInt(Material m, byte data)
	{
		int intLocation = IntMap.getIntIndex(m.getId(), data);

		if (intLocation == -1)
		{
			return 0;
		}

		return mapData[intLocation];
	}

	public boolean setInt(Material m, byte data, int value)
	{
		return setInt(m.getId(), data, value);
	}

	public boolean setInt(int id, byte data, int value)
	{
		//System.out.println("SETINT: " + id + " : " + data + " : " + value);
		if (hasDataValue(id) && data == (byte) -1)
		{
			//System.out.println("range");
			setDataRange(id, (byte) 0, (byte) 15, value);
		}
		else
		{
			int intLocation = IntMap.getIntIndex(id, data);
			//System.out.println("loc : " + intLocation);
			if (intLocation == -1)
			{
				return false;
			}
			//System.out.println("mapdata old : " + mapData[intLocation]);
			mapData[intLocation] = value;
			//System.out.println("mapdata new : " + mapData[intLocation]);
		}
		return true;
	}

	public static int getIntIndex(int id, byte data)
	{
		return ((materialIndex[id] + data) < mapSize) ? materialIndex[id] + data : -1;
	}

	private boolean hasDataValue(int id)
	{
		return dataValueMap.containsKey(id);
	}

	/**
	 * Combine two IntMaps, with otherMap having higher priority than this.
	 * Please do not use this function, as it is slow.
	 *
	 * @param otherMap Map to combine with.
	 */
	@Deprecated
	public void combine(IntMap otherMap)
	{
		for (int index = 0; index < mapData.length; index++)
		{
			if (otherMap.mapData[index] != 0)
			{
				mapData[index] = otherMap.mapData[index];
			}
		}
	}

	/**
	 * Sets a range of the IntMap prevents multiple calls to IntMap and back
	 */
	public boolean setRange(int startId, byte startdata, int endId, byte enddata, int value)
	{
		if (startdata < -1 || enddata < -1 || startId > endId
			|| (startdata > 0 && !hasDataValue(startId)) || (enddata > 0 && !hasDataValue(endId))
			|| !isAllowedMaterial(startId, startdata) || !isAllowedMaterial(endId, enddata))
		{
			return false;
		}
		if (startId < endId)
		{
			if (startdata >= 0 && enddata >= 0)
			{
				setDataRange(startId, startdata, (byte) 15, value);
				startId++;
				setDataRange(endId, (byte) 0, enddata, value);
				endId--;
			}
			else if (startdata == -1 && enddata >= 0)
			{
				setDataRange(endId, (byte) 0, enddata, value);
				endId--;
			}
			else if (startdata >= 0 && enddata == -1)
			{
				setDataRange(startId, startdata, (byte) 15, value);
				startId++;
			}
			while (startId <= endId)
			{
				if (hasDataValue(startId))
				{
					setDataRange(startId, (byte) 0, (byte) 15, value);
				}
				else
				{
					setInt(startId, (byte) 0, value);
				}
				do
				{
					startId++;
				}
				while (!isAllowedMaterial(startId, (byte) 0));
			}
			return true;
		}
		else if (startId == endId)
		{
			if (startdata < enddata && hasDataValue(startId))
			{
				setDataRange(startId, startdata, enddata, value);
				return true;
			}
			return false;
		}
		else
		{
			return false;
		}
	}

	private boolean setDataRange(int id, byte start, byte end, int amount)
	{
		if (!hasDataValue(id))
		{
			return false;
		}
		for (byte data = start; data <= end; data++)
		{
			int key = getIntIndex(id, data);
			if (key == -1)
			{
				break;
			}
			mapData[key] = amount;
		}
		return true;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof IntMap)
		{
			IntMap otherMap = (IntMap) other;
			for (int index = 0; index < mapData.length; index++)
			{
				if (mapData[index] != otherMap.mapData[index])
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(mapData);
	}

	@Override
	public IntMap clone() throws CloneNotSupportedException
	{
		super.clone();
		return new IntMap(mapData.clone());
	}

	@Override
	public String toString()
	{
		String str = "{\n";
		for (int i = 0; i < mapData.length; i++)
		{
			if (mapData[i] != 0)
			{
				str += "    [" + i + "] -> " + mapData[i] + "\n";
			}
		}
		str += "}";
		return str;
	}

	public void fillAll()
	{
		fillAll(false);
	}

	public void fillAll(boolean negative)
	{
		int value = negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for (int i = 0; i < mapData.length; i++)
		{
			mapData[i] = value;
		}

	}
}