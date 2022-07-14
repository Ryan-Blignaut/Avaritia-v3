package thesilverecho.avaritia.client.render_layer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import thesilverecho.avaritia.client.shader.ModShaders;
import thesilverecho.avaritia.common.Avaritia;

import java.util.function.Supplier;

public enum ModRenderTypes
{
	COSMIC_RENDER_TYPE(() -> InternalModTypes.COSMIC_RENDER_TYPE);

	private final Supplier<RenderType> typeSupplier;

	ModRenderTypes(Supplier<RenderType> typeSupplier)
	{
		this.typeSupplier = typeSupplier;
	}

	public RenderType get()
	{
		return typeSupplier.get();
	}

	private static class InternalModTypes extends RenderType
	{
		private static final ResourceLocation BLOCK_ITEM_LOC = InventoryMenu.BLOCK_ATLAS;

		public static final RenderType COSMIC_RENDER_TYPE = create(Avaritia.MOD_ID + ":cosmic",
				DefaultVertexFormat.NEW_ENTITY,
				VertexFormat.Mode.QUADS,
				256,
				true,
				false,
				RenderType.CompositeState.builder()
//				                         .setShaderState(RenderStateShard.RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER)
                                         .setShaderState(new ShaderStateShard(() -> ModShaders.cosmicShader))
                                         .setTextureState(MultiTextureStateShard.builder().add(BLOCK_ITEM_LOC, false, false).build())
                                         .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                                         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                                         .setCullState(NO_CULL)
                                         .setDepthTestState(LEQUAL_DEPTH_TEST)
                                         .setLayeringState(RenderStateShard.POLYGON_OFFSET_LAYERING)
                                         .setLightmapState(LIGHTMAP)
                                         .createCompositeState(false));

		public InternalModTypes(String name, VertexFormat vertexFormat, VertexFormat.Mode mode, int expectedSize, boolean b, boolean b1, Runnable preTask, Runnable postTask)
		{
			super(name, vertexFormat, mode, expectedSize, b, b1, preTask, postTask);
			throw new IllegalStateException("This class must not be instantiated");
		}
	}

}
