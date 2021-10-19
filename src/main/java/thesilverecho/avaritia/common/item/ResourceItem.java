package thesilverecho.avaritia.common.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.IItemRenderProperties;
import thesilverecho.avaritia.client.render.ExtremeItemRender;
import thesilverecho.avaritia.common.init.ModGroup;

import java.util.function.Consumer;

public class ResourceItem extends Item
{
	private Rarity rarity;

	public ResourceItem(boolean pulse, boolean noise, int color, int size, Rarity rarity)
	{
		super(new Item.Properties().tab(ModGroup.AVARITIA).rarity(rarity));
	}

	public ResourceItem()
	{
		this(false, false, 0xFF000000, 8, Rarity.UNCOMMON);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer)
	{
		consumer.accept(new IItemRenderProperties()
		{
			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer()
			{
				return ExtremeItemRender.SUPPLIER;
			}
		});
	}

	public ResourceItem setRarity(Rarity rarity)
	{
		this.rarity = rarity;
		return this;
	}
}
