package thesilverecho.avaritia.client.render_layer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import thesilverecho.avaritia.client.shader.ModShaders;

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
		RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
		                                                                    .setShaderState(new ShaderStateShard(() -> ModShaders.cosmicShader))
		                                                                    .setTextureState(new TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false))
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
