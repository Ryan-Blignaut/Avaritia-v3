package thesilverecho.avaritia.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import thesilverecho.avaritia.common.Avaritia;

@Mod.EventBusSubscriber(modid = Avaritia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		if (event.includeServer())
		{
//			generator.addProvider(new RecipeGenerator(generator));
//			generator.addProvider(new LootTableGenerator(generator));
		}
		if (event.includeClient())
		{
			generator.addProvider(new ModBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new ModItemModelGenerator(generator, event.getExistingFileHelper()));
			generator.addProvider(new ExtremeItemProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new ModLanguageGenerator(generator));
		}
	}
}