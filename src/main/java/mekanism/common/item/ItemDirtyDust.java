package mekanism.common.item;

import mekanism.common.Mekanism;
import mekanism.common.Resource;

import net.minecraft.item.ItemStack;

public class ItemDirtyDust extends ItemMekanism
{
	public ItemDirtyDust()
	{
		super();
		setHasSubtypes(true);
		setCreativeTab(Mekanism.tabMekanism);
	}

/*
	@Override
	public void registerIcons(IIconRegister register)
	{
		for(int i = 0; i < Resource.values().length; i++)
		{
			icons[i] = register.registerIcon("mekanism:Dirty" + Resource.values()[i].getName() + "Dust");
		}
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List itemList)
	{
		for(int counter = 0; counter < Resource.values().length; counter++)
		{
			itemList.add(new ItemStack(item, 1, counter));
		}
	}
*/

	@Override
	public String getUnlocalizedName(ItemStack item)
	{
		if(item.getItemDamage() <= Resource.values().length-1)
		{
			return "item.dirty" + Resource.values()[item.getItemDamage()].getName() + "Dust";
		}
		
		return "Invalid";
	}
}
