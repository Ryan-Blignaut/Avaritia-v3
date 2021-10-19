package thesilverecho.avaritia.common.item;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModGroup;

public class InfinityBow extends BowItem
{
	public InfinityBow()
	{
		super(new Item.Properties().stacksTo(1).tab(ModGroup.AVARITIA).rarity(Avaritia.COSMIC));
	}

	@Override
	public int getUseDuration(ItemStack pStack)
	{
		return 1200;
	}


}
