package thesilverecho.avaritia.common.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import thesilverecho.avaritia.common.item.InfinitySword;

import java.util.function.Supplier;

public class ModItems
{

	public static final RegistryObject<Item> EXTREME_CRAFTING_TABLE_BLOCK = register("extreme_crafting_table_block", () -> new BlockItem(ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> INFINITY_SWORD = register("infinity_sword", InfinitySword::new);

	public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
	{
		return ModRegistry.ITEMS.register(name, item);
	}

	public static void register()
	{
	}
}
