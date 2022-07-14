package thesilverecho.avaritia.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModItems;

import java.util.Objects;

public class ModItemModelGenerator extends ItemModelProvider
{

	public ModItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, Avaritia.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		singleTexture(ModItems.EXTREME_INGOT, new ResourceLocation(Avaritia.MOD_ID, "item/extreme_ingot"));
		singleTexture(ModItems.NEUTRON_INGOT, new ResourceLocation(Avaritia.MOD_ID, "item/neutron_ingot"));
		singleTexture(ModItems.NEUTRON_NUGGET, new ResourceLocation(Avaritia.MOD_ID, "item/neutron_nugget"));
		singleTexture(ModItems.NEUTRON_PILE, new ResourceLocation(Avaritia.MOD_ID, "item/neutron_pile"));
		withExistingParent("extreme_crafting_table_original", modLoc("block/extreme_crafting_table"));
	}

	private void singleTexture(RegistryObject<Item> itemRegistryObject, ResourceLocation location)
	{
		singleTexture(Objects.requireNonNull(itemRegistryObject.get().getRegistryName()).getPath() + "_original", new ResourceLocation("item/generated"), "layer0", location);

	}


	private ItemModelBuilder getBuilder(RegistryObject<Item> itemRegistryObject)
	{
		final String path = Objects.requireNonNull(itemRegistryObject.get().getRegistryName()).getPath();
		return getBuilder(path);
	}

}
