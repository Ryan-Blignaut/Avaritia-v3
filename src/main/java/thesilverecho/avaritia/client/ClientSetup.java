package thesilverecho.avaritia.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import thesilverecho.avaritia.client.model.loader.CosmicModelGeometry;
import thesilverecho.avaritia.common.Avaritia;

@Mod.EventBusSubscriber(modid = Avaritia.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup
{

	public static void init(final FMLClientSetupEvent event)
	{
//		DeferredWorkQueue.runLater(() ->
//		{
//			ScreenManager.register(ModContainers.EXTREME_CRAFTING_TABLE_CONTAINER.get(), ExtremeCraftingTableScreen::new);
//		});
	}


	@SubscribeEvent
	public static void registerCustomModelLoaders(ModelRegistryEvent event)
	{
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Avaritia.MOD_ID, "super"), CosmicModelGeometry.Loader.INSTANCE);
	}

}
