package thesilverecho.avaritia.client;

import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import thesilverecho.avaritia.client.model.loader.CosmicModelGeometry;
import thesilverecho.avaritia.client.model.loader.ExtremeModelGeometry;
import thesilverecho.avaritia.client.shader.ModShaders;
import thesilverecho.avaritia.common.Avaritia;

import java.nio.FloatBuffer;
import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Avaritia.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup
{

	public static final FloatBuffer COSMIC_UVS = FloatBuffer.allocate(40);
	private static final ArrayList<TextureAtlasSprite> COSMIC_LOCATIONS = new ArrayList<>();

	public static void init(final FMLClientSetupEvent event)
	{
//		DeferredWorkQueue.runLater(() ->
//		{
//			ScreenManager.register(ModContainers.EXTREME_CRAFTING_TABLE_CONTAINER.get(), ExtremeCraftingTableScreen::new);
//		});
	}


/*
	@SubscribeEvent
	public static void onRenderTick(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			if (COSMIC_LOCATIONS.isEmpty())
				for (int i = 0; i < 10; i++)
					COSMIC_LOCATIONS.add(Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(Avaritia.MOD_ID, "shader/cosmic_" + i)));

			final Uniform vs = ModShaders.cosmicShader.getUniform("CosmicUVs");
//			if (vs != null)
			{
				final FloatBuffer cosmicUVs = vs.getFloatBuffer();
				COSMIC_LOCATIONS.forEach(textureAtlasSprite ->
				{
					cosmicUVs.put(textureAtlasSprite.getU0());
					cosmicUVs.put(textureAtlasSprite.getV0());
					cosmicUVs.put(textureAtlasSprite.getU1());
					cosmicUVs.put(textureAtlasSprite.getV1());
				});
				cosmicUVs.flip();
			}
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
	public static void registerCustomModelLoaders(ModelEvent.RegisterGeometryLoaders event)
	{
//		event.register("extreme",ExtremeModelGeometry.Loader.INSTANCE);
//		event.register(new ResourceLocation(Avaritia.MOD_ID, "extreme"));
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Avaritia.MOD_ID, "extreme"), ExtremeModelGeometry.Loader.INSTANCE);
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Avaritia.MOD_ID, "cosmic"), CosmicModelGeometry.Loader.INSTANCE);
	}

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event)
	{
		if (!event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS))
			return;
		for (int i = 0; i < 10; i++)
			event.addSprite(new ResourceLocation(Avaritia.MOD_ID, "shader/cosmic_" + i));
	}

}
