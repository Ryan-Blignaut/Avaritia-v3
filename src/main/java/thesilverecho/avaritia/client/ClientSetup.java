package thesilverecho.avaritia.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import thesilverecho.avaritia.client.model.loader.CosmicModelGeometry;
import thesilverecho.avaritia.client.model.loader.InnerModelGeometry;
import thesilverecho.avaritia.client.model.loader.LayeredModelTest;
import thesilverecho.avaritia.client.shader.ModShaders;
import thesilverecho.avaritia.common.Avaritia;

import java.util.HashMap;

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


	/*@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			if (COSMIC_LOCATIONS.isEmpty())
				for (int i = 0; i < 10; i++)
					COSMIC_LOCATIONS.add(Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(Avaritia.MOD_ID, "shader/cosmic_" + i)));


			COSMIC_LOCATIONS.forEach(textureAtlasSprite ->
			{
				COSMIC_UVS.put(textureAtlasSprite.getU0());
				COSMIC_UVS.put(textureAtlasSprite.getV0());
				COSMIC_UVS.put(textureAtlasSprite.getU1());
				COSMIC_UVS.put(textureAtlasSprite.getV1());
			});
			COSMIC_UVS.flip();
		}
	}
*/
	@SubscribeEvent
	public static void registerShaders(RegisterShadersEvent event)
	{
		try
		{
			ModShaders.registerShaders(event);
		} catch (Exception e)
		{
			Avaritia.LOGGER.error("Error loading avaritia shaders", e);
		}
	}

	@SubscribeEvent
	public static void registerCustomModelLoaders(ModelRegistryEvent event)
	{
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Avaritia.MOD_ID, "super"), CosmicModelGeometry.Loader.INSTANCE);
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Avaritia.MOD_ID, "inner-model"), InnerModelGeometry.Loader.INSTANCE);
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Avaritia.MOD_ID, "layered"), LayeredModelTest.Loader.INSTANCE);
	}

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event)
	{
		if (!event.getMap().location().equals(InventoryMenu.BLOCK_ATLAS))
			return;
		LOCS.forEach((s, resourceLocation) -> event.addSprite(resourceLocation));

		/*for (int i = 0; i < 10; i++)
		{
			event.addSprite(new ResourceLocation(Avaritia.MOD_ID, "shader/cosmic_" + i));
		}*/
	}

//	public static final FloatBuffer COSMIC_UVS = BufferUtils.createFloatBuffer(4 * 10);
//	public static final ArrayList<TextureAtlasSprite> COSMIC_LOCATIONS = new ArrayList<>(10);

	private final static HashMap<String, ResourceLocation> LOCS = new HashMap<>();

	public static void registerSprite(ResourceLocation name, String location)
	{
		LOCS.put(location, name);
	}
}
