package thesilverecho.avaritia.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import thesilverecho.avaritia.common.Avaritia;
import thesilverecho.avaritia.common.init.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider
{
	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, Avaritia.MOD_ID, exFileHelper);
	}


	@Override
	protected void registerStatesAndModels()
	{
		simpleBlock(ModBlocks.EXTREME_CRAFTING_TABLE_BLOCK.get());
	}
}