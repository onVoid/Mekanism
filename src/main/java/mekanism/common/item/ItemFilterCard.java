package mekanism.common.item;

import java.util.List;

import mekanism.api.EnumColor;
import mekanism.api.IFilterAccess;
import mekanism.common.util.LangUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFilterCard extends ItemMekanism
{
	public ItemFilterCard()
	{
		super();
		
		setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List<String> list, boolean flag)
	{
		super.addInformation(itemstack, entityplayer, list, flag);
		
		list.add(EnumColor.GREY + LangUtils.localize("gui.data") + ": " + EnumColor.INDIGO + LangUtils.localize(getDataType(itemstack)));
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileEntity tileEntity = world.getTileEntity(pos);
			
			if(tileEntity instanceof IFilterAccess)
			{
				if(player.isSneaking())
				{
					NBTTagCompound data = ((IFilterAccess)tileEntity).getFilterData(new NBTTagCompound());
					
					if(data != null)
					{
						data.setString("dataType", ((IFilterAccess)tileEntity).getDataType());
						setData(stack, data);
						player.addChatMessage(new ChatComponentText(EnumColor.DARK_BLUE + "[Mekanism] " + EnumColor.GREY + LangUtils.localize("tooltip.filterCard.got").replaceAll("%s", EnumColor.INDIGO + LangUtils.localize(data.getString("dataType")) + EnumColor.GREY)));
					}
					
					return true;
				}
				else if(getData(stack) != null)
				{
					if(((IFilterAccess)tileEntity).getDataType().equals(getDataType(stack)))
					{
						((IFilterAccess)tileEntity).setFilterData(getData(stack));
						player.addChatMessage(new ChatComponentText(EnumColor.DARK_BLUE + "[Mekanism] " + EnumColor.DARK_GREEN + LangUtils.localize("tooltip.filterCard.set").replaceAll("%s", EnumColor.INDIGO + LangUtils.localize(getDataType(stack)) + EnumColor.DARK_GREEN)));
						setData(stack, null);
					}
					else {
						player.addChatMessage(new ChatComponentText(EnumColor.DARK_BLUE + "[Mekanism] " + EnumColor.RED + LangUtils.localize("tooltip.filterCard.unequal") + "."));
					}
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void setData(ItemStack itemstack, NBTTagCompound data)
	{
		if(itemstack.getTagCompound() == null)
		{
			itemstack.setTagCompound(new NBTTagCompound());
		}

		if(data != null)
		{
			itemstack.getTagCompound().setTag("data", data);
		}
		else {
			itemstack.getTagCompound().removeTag("data");
		}
	}

	public NBTTagCompound getData(ItemStack itemstack)
	{
		if(itemstack.getTagCompound() == null)
		{
			return null;
		}
		
		NBTTagCompound data = itemstack.getTagCompound().getCompoundTag("data");
		
		if(data.hasNoTags())
		{
			return null;
		}
		else {
			return itemstack.getTagCompound().getCompoundTag("data");
		}
	}

	public String getDataType(ItemStack itemstack)
	{
		NBTTagCompound data = getData(itemstack);
		
		if(data != null)
		{
			return data.getString("dataType");
		}
		
		return "gui.none";
	}
}
