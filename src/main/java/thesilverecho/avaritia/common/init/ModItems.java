package thesilverecho.avaritia.common.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.item.InfinitySword;
import thesilverecho.avaritia.common.item.ResourceItem;

import java.util.function.Supplier;

public class ModItems
{

	public static final RegistryObject<Item> EXTREME_CRAFTING_TABLE_BLOCK = register("extreme_crafting_table", () -> new BlockItem(ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK.get(), new Item.Properties().tab(ModGroup.AVARITIA)));
	public static final RegistryObject<Item> INFINITY_SWORD = register("infinity_sword", InfinitySword::new);
	public static final RegistryObject<Item> NEUTRON_INGOT = register("neutron_ingot", ResourceItem::new);
	public static final RegistryObject<Item> NEUTRON_NUGGET = register("neutron_nugget", ResourceItem::new);
	public static final RegistryObject<Item> NEUTRON_PILE = register("neutron_pile", ResourceItem::new);

	public static final RegistryObject<Item> EXTREME_INGOT = register("extreme_ingot", () -> new ResourceItem().setRarity(Avaritia.COSMIC));

	public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
	{
		return ModRegistry.ITEMS.register(name, item);
	}

	public static void register()
	{
	}
}
