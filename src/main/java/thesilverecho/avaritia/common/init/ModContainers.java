package thesilverecho.avaritia.common.init;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fmllegacy.RegistryObject;
import thesilverecho.avaritia.common.container.ExtremeCraftingTableContainer;

public class ModContainers
{
	public static final RegistryObject<MenuType<ExtremeCraftingTableContainer>> EXTREME_CRAFTING_TABLE_CONTAINER = register("extreme_crafting_table", ExtremeCraftingTableContainer::new);


	public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, MenuType.MenuSupplier<T> supplier)
	{
		return ModRegistry.CONTAINERS.register(name, () -> new MenuType<>(supplier));
	}

	static void register()
	{
	}


}
