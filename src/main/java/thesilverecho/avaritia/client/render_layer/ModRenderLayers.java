package thesilverecho.avaritia.client.render_layer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import thesilverecho.avaritia.client.shader.CosmicShader;
import thesilverecho.avaritia.client.shader.ModShaders;
import thesilverecho.avaritia.common.Avaritia;

import java.util.function.Function;

public class ModRenderLayers extends RenderType
{

	public static RenderType getUnsortedTranslucent()
	{
		return ITEM_SHADER.apply(TextureAtlas.LOCATION_BLOCKS);
	}

	private static final Function<ResourceLocation, RenderType> ITEM_SHADER = Util.memoize(ModRenderLayers::layeredShader);

	private static RenderType layeredShader(ResourceLocation locationIn)
	{
		RenderType.CompositeState compositeState = CompositeState.builder()
		                                                         .setShaderState(new ShaderStateShard(() ->
		                                                         {

			                                                         final CosmicShader cosmicShader = ModShaders.testShader;
			                                                         final Minecraft mc = Minecraft.getInstance();
			                                                         float yaw = 0;
			                                                         float pitch = 0;
			                                                         float scale = 1.0f;
			                                                         if (!false)
			                                                         {
				                                                         assert mc.player != null;
				                                                         yaw = (float) ((mc.player.yRotO * 2 * Math.PI) / 360.0);
				                                                         pitch = -(float) ((mc.player.xRotO * 2 * Math.PI) / 360.0);
			                                                         } else
				                                                         scale = 25.0f;

//			                                                         cosmicShader.safeGetUniform("pitch").set(pitch);
//			                                                         cosmicShader.safeGetUniform("yaw").set(yaw);
//			                                                         cosmicShader.safeGetUniform("externalScale").set(scale);
//			                                                                    cosmicShader.safeGetUniform("CosmicUVs").set(ClientSetup.COSMIC_UVS.array());
//			                                                                    int uvs = ARBShaderObjects.glGetUniformLocationARB(cosmicShader.getId(), "CosmicUVs");
//			                                                                    ARBShaderObjects.glUniformMatrix2fvARB(uvs, false, ClientSetup.COSMIC_UVS);

			                                                         return cosmicShader;
		                                                         }))
		                                                         .setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(TextureAtlas.LOCATION_BLOCKS, false, false).add(new ResourceLocation(Avaritia.MOD_ID, "textures/shader/cosmic_0"), false, false).build())
		                                                         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
		                                                         .setCullState(NO_CULL)
		                                                         .setLightmapState(LIGHTMAP)
		                                                         .createCompositeState(true);
		return create("test", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, compositeState);
	}

	public static final RenderType COSMIC_RENDER_LAYER = RenderType.create("cosmic",
			DefaultVertexFormat.POSITION_TEX,
			VertexFormat.Mode.QUADS,
			256, false, false,
			RenderType.CompositeState
					.builder()
					.setShaderState(new RenderStateShard.ShaderStateShard(() -> ModShaders.cosmicShader))
					.setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false))
					.setWriteMaskState(COLOR_WRITE)
					.setCullState(NO_CULL)
					.setDepthTestState(EQUAL_DEPTH_TEST)
					.createCompositeState(true));

	public ModRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.Mode mode, int expectedSize, boolean b, boolean b1, Runnable preTask, Runnable postTask, RenderType original)
	{
		super(name, vertexFormat, mode, expectedSize, b, b1, preTask, postTask);
	}


}
