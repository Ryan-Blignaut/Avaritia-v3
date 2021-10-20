package thesilverecho.avaritia.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fmllegacy.RegistryObject;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModItems;
import thesilverecho.avaritia.datagen.builder.ExtremeItemTemplate;

import java.util.Objects;

public class ExtremeItemProvider extends ModelProvider<ItemModelBuilder>
{

	public ExtremeItemProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
	{
		super(generator, Avaritia.MOD_ID, ITEM_FOLDER, ExtremeItemTemplate::new, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		getBuilder(ModItems.EXTREME_INGOT).setSize(10).pulse().setBackgroundTexture(new ResourceLocation(Avaritia.MOD_ID, "halo/halo128"));
		getBuilder(ModItems.NEUTRON_INGOT).setSize(8).setColour(0x99FFFFFF).setBackgroundTexture(new ResourceLocation(Avaritia.MOD_ID, "halo/halo_noise"));
		getBuilder(ModItems.NEUTRON_NUGGET).setSize(8).setColour(0x99FFFFFF).setBackgroundTexture(new ResourceLocation(Avaritia.MOD_ID, "halo/halo_noise"));
		getBuilder(ModItems.NEUTRON_PILE).setSize(8).setColour(0x99FFFFFF).setBackgroundTexture(new ResourceLocation(Avaritia.MOD_ID, "halo/halo_noise"));
	}

	private ExtremeItemTemplate getBuilder(RegistryObject<Item> itemRegistryObject)
	{
		final String path = Objects.requireNonNull(itemRegistryObject.get().getRegistryName()).getPath();
		return ((ExtremeItemTemplate) getBuilder(path)).setParent(new ResourceLocation(Avaritia.MOD_ID, "item/" + path + "_original"));
	}

	@Override
	public String getName()
	{
		return "Extreme item providers";
	}
}
