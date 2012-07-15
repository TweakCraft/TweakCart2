package net.tweakcraft.tweakcart.model;

import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.Material;

public class IntMap
{
	private int[] mapData;
	public static final int[] materialIndex = new int[Material.values().length];
	public static final Material[] materialList = Material.values();
	public final static HashMap<Integer, Byte> dataValueMap = new HashMap<Integer, Byte>();
	public static final int mapSize;
	public static final int materialSize = materialList.length;

	static
	{
		/*
		 * dit moet dus nog worden geautomatiseerd
		 */
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
		
		//dataValueMap.put(373, (byte) 13); // potions // id's verlefpt?
		//dataValueMap.put(383, (byte) 21); // spawn eggs // id's verlefpt?

		
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

	private IntMap( int[] data )
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

	public static int getIntIndex( int id, byte data )
	{
		return getIntIndex(Material.getMaterial(id), data);
	}
	public static int getIntIndex( Material m, byte data )
	{
		if(m == null)
		{
			return -1;
		}
		if(data < 0)
		{
			data = 0;
		}
		return ((materialIndex[m.ordinal()] + data) < mapSize) ? materialIndex[m.ordinal()] + data : -1;
	}


	private boolean hasDataValue( int id )
	{
		return dataValueMap.containsKey(id);
	}
	private boolean hasDataValue( Material m )
	{
		return dataValueMap.containsKey(m.getId());
	}
	private int getMaxDataValue( int id )
	{
		return dataValueMap.get(id);
	}
	private int getMaxDataValue( Material m )
	{
		return dataValueMap.get(m.getId());
	}	
	public static boolean isAllowedMaterial( Material m, byte data )
	{
		if(m == null)
		{
			/* TODO: dit moet misschien anders? */
			return false;
		}
		return IntMap.getIntIndex(m, data) != -1;
	}
	public static boolean isAllowedMaterial( int id, byte data )
	{
		if(Material.getMaterial(id) == null)
		{
			/* TODO: dit moet misschien anders? */
			return false;
		}		
		return IntMap.isAllowedMaterial(Material.getMaterial(id), data);
	}

	public int getInt( int id, byte data )
	{
		return getInt(Material.getMaterial(id), data);
	}
	public int getInt( Material m, byte data )
	{
		if (m == null)
		{
			return 0;
		}
		int intLocation = IntMap.getIntIndex(m, data);

		if (intLocation == -1)
		{
			return 0;
		}

		return mapData[intLocation];
	}

	public boolean setInt( int id, byte data, int value )
	{
		return setInt(Material.getMaterial(id), data, value);
	}
	public boolean setInt( Material m, byte data, int value )
	{
		if (m == null)
		{
			return false;
		}
		if (hasDataValue(m) && data == (byte) -1)
		{
			setDataRange(m, (byte) 0, (byte) getMaxDataValue(m), value);
		}
		else
		{
			int intLocation = IntMap.getIntIndex(m, data);
			if (intLocation == -1)
			{
				return false;
			}
			mapData[intLocation] = value;
		}
		return true;
	}
	/**
	 * Sets a range of the IntMap prevents multiple calls to IntMap and back
	 */
	public boolean setRange( int startId, byte startdata, int endId, byte enddata, int value )
	{
		return setRange(Material.getMaterial(startId), startdata, Material.getMaterial(endId), enddata, value);
	}
	public boolean setRange( Material startM, byte startData, Material endM, byte endData, int value )
	{
		if(startM == null || endM == null)
		{
			return false;
		}
		int startId = startM.getId();
		int endId = endM.getId();
		if (startData < -1 || endData < -1 || startId > endId
				|| (startData > 0 && !hasDataValue(startM)) || (endData > 0 && !hasDataValue(endM))
				|| !isAllowedMaterial(startM, startData) || !isAllowedMaterial(endId, endData))
		{
			return false;
		}
		if (startId < endId)
		{
			int rangeStart = IntMap.getIntIndex(startM, startData);
			int rangeEnd = IntMap.getIntIndex(endM, endData);
			/* if endData is -1 and the endMaterial has subdata we have to add this */
			if(endData == -1 && hasDataValue(endM))
			{
				rangeEnd += IntMap.dataValueMap.get(endId);
			}
			for(int r = rangeStart; r <= rangeEnd; r++)
			{
				mapData[r] = value;
			}
			return true;
		}
		else if (startId == endId)
		{
			if (startData < endData && hasDataValue(startM))
			{
				setDataRange(startM, startData, endData, value);
				return true;
			}
			return false;
		}
		else
		{
			return false;
		}
	}

	private boolean setDataRange( Material m, byte start, byte end, int amount )
	{
		if (!hasDataValue(m))
		{
			return false;
		}
		for (byte data = start; data <= end; data++)
		{
			int key = getIntIndex(m, data);
			if (key == -1)
			{
				break;
			}
			mapData[key] = amount;
		}
		return true;
	}

	@Override
	public boolean equals( Object other )
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
	/**
	 * Combine two IntMaps, with otherMap having higher priority than this.
	 * Please do not use this function, as it is slow.
	 *
	 * @param otherMap Map to combine with.
	 */
	@Deprecated
	public void combine( IntMap otherMap )
	{
		for (int index = 0; index < mapData.length; index++)
		{
			if (otherMap.mapData[index] != 0)
			{
				mapData[index] = otherMap.mapData[index];
			}
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

	public void fillAll( boolean negative )
	{
		int value = negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for (int i = 0; i < mapData.length; i++)
		{
			mapData[i] = value;
		}

	}
}