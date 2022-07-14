package thesilverecho.avaritia.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import thesilverecho.avaritia.common.block.ExtremeCraftingTableBlock;

import java.util.function.Supplier;

public class ModBlocks
{
	public static final RegistryObject<Block> EXTREME_CRAFTING_TABLE_BLOCK = register("extreme_crafting_table", ExtremeCraftingTableBlock::new);

	public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block)
	{
//		ModRegistry.ITEMS.register(name, () -> new Item(new Item.Properties().group(ModGroup.AVARITIA)));
		return ModRegistry.BLOCKS.register(name, block);
	}

	public static void register()
	{

	}
}
