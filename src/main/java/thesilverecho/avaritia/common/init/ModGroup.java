package thesilverecho.avaritia.common.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModGroup extends CreativeModeTab
{
	public static ModGroup AVARITIA = new ModGroup();

	public ModGroup()
	{
		super("avaritia.mod.tab");
	}

	@Override
	public ItemStack makeIcon()
	{
		return new ItemStack(Items.DIAMOND);
	}
}
