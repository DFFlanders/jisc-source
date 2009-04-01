package com.dytech.edge.common.valuebean;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * Copyright of The Learning Edge International.
 * Not for redistribution.
 */
public class ItemKey implements Serializable
{
	private String uuid;
	private int version;
	private String itemdef;

	public ItemKey()
	{
		super();
	}

	public ItemKey(String uuid, int version)
	{
		setUuid(uuid);
		setVersion(version);
	}

	public ItemKey(String uuid, int version, String itemdef)
	{
		this(uuid, version);
		setItemdef(itemdef);
	}

	/**
	 * @param value <quot>item_uuid:itemdef:item_version</quot>.
	 */
	public ItemKey(String value)
	{
		setFromString(value);
	}

	/**
	 * @param value <quot>item_uuid:itemdef:item_version</quot>.
	 */
	public void setFromString(String value)
	{
		StringTokenizer stok = new StringTokenizer(value, ":");
		uuid = stok.nextToken();
		itemdef = stok.nextToken();
		version = Integer.parseInt(stok.nextToken());
	}

	@Override
	public boolean equals(Object obj)
	{
		ItemKey key = null;
		if( obj instanceof ItemKey )
		{
			key = (ItemKey) obj;
		}
		else if( obj instanceof String )
		{
			try
			{
				key = new ItemKey((String) obj);
			}
			catch( Exception ex )
			{
				// We will just ignore this.
			}
		}

		if( key == null )
		{
			return false;
		}
		else
		{
			// Calls the more specific method
			return equals(key);
		}
	}

	public boolean equals(ItemKey rhs)
	{
		if( rhs.uuid.equals(uuid) )
		{
			if( rhs.itemdef.equals(itemdef) )
			{
				if( rhs.version == version )
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return uuid + ':' + itemdef + ':' + version;
	}

	public String getKey()
	{
		//return id + "/" + version;
		return uuid + "/" + version;
	}

	@Override
	public int hashCode()
	{
		return (uuid + itemdef + version).toString().hashCode();
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		if( uuid == null )
		{
			throw new IllegalArgumentException("UUID must not be null");
		}
		this.uuid = uuid;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		if( version < 0 )
		{
			throw new IllegalArgumentException("Version must be greater than or equals to zero");
		}
		this.version = version;
	}

	public String getItemdef()
	{
		return itemdef;
	}

	public void setItemdef(String itemdef)
	{
		if( itemdef == null )
		{
			throw new IllegalArgumentException("Itemdef must not be null");
		}
		this.itemdef = itemdef;
	}
}
