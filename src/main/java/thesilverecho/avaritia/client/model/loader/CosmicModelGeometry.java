package thesilverecho.avaritia.client.model.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.TRSRTransformer;
import thesilverecho.avaritia.client.render_layer.ModRenderTypes;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class CosmicModelGeometry implements IModelGeometry<CosmicModelGeometry>
{
	private final ImmutableSet<Integer> overlayLayers;
	private ImmutableList<Material> textures;

	public CosmicModelGeometry(ImmutableList<Material> textures, ImmutableSet<Integer> overlayLayers)
	{
		this.textures = textures;
		this.overlayLayers = overlayLayers;
	}

	private static ImmutableList<Material> getTextures(IModelConfiguration model)
	{
		ImmutableList.Builder<Material> builder = ImmutableList.builder();
		for (int i = 0; model.isTexturePresent("layer" + i); i++)
			builder.add(model.resolveTexture("layer" + i));
		return builder.build();
	}

	public static RenderType getLayerRenderType(boolean isOverlay)
	{

		return isOverlay ? ModRenderTypes.COSMIC_RENDER_TYPE.get() : ForgeRenderTypes.ITEM_LAYERED_TRANSLUCENT.get();
	}


	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation)
	{
		final ImmutableMap<ItemTransforms.TransformType, Transformation> transformMap = PerspectiveMapWrapper.getTransforms(new CompositeModelState(owner.getCombinedTransform(), modelTransform));
		final TextureAtlasSprite particle = spriteGetter.apply(owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : textures.get(0));
		final ItemMultiLayerBakedModel.Builder builder = ItemMultiLayerBakedModel.builder(owner, particle, overrides, transformMap);

		final Transformation transform = modelTransform.getRotation();
		for (int i = 0; i < textures.size(); i++)
		{
			TextureAtlasSprite tas = spriteGetter.apply(textures.get(i));
			final boolean isOverlayLayer = overlayLayers.contains(i);
			final RenderType rt = getLayerRenderType(isOverlayLayer);
			final ImmutableList<BakedQuad> quadsForSprite = getQuadsForSprite(i, tas, transform);
			builder.addQuads(rt, quadsForSprite);
		}

		return builder.build();
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
	{
		this.textures = getTextures(owner);
		return textures;
	}

	/*
	 * Big thanks to tetra for this code.
	 * */
	public static ImmutableList<BakedQuad> getQuadsForSprite(int tintIndex, TextureAtlasSprite sprite, Transformation transform)
	{


		return ItemLayerModel.getQuadsForSprite(tintIndex, sprite, transform);

//		TODO: fix the topology as all parts of the mesh are now internally connected.
		/*ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		int uMax = sprite.getWidth();
		int vMax = sprite.getHeight();

		for (int v = 0; v < vMax; v++)
		{
			builder.add(buildSideQuad(transform, Direction.UP, tintIndex, sprite, 0, v, uMax));
			builder.add(buildSideQuad(transform, Direction.DOWN, tintIndex, sprite, 0, v + 1, uMax));
		}

		for (int u = 0; u < uMax; u++)
		{
			builder.add(buildSideQuad(transform, Direction.EAST, tintIndex, sprite, u + 1, 0, vMax));
			builder.add(buildSideQuad(transform, Direction.WEST, tintIndex, sprite, u, 0, vMax));
		}

		// front
		builder.add(buildQuad(transform, Direction.NORTH, sprite, tintIndex,
				0, 0, 7.5f / 16f, sprite.getU0(), sprite.getV1(),
				0, 1, 7.5f / 16f, sprite.getU0(), sprite.getV0(),
				1, 1, 7.5f / 16f, sprite.getU1(), sprite.getV0(),
				1, 0, 7.5f / 16f, sprite.getU1(), sprite.getV1()
		));
		// back
		builder.add(buildQuad(transform, Direction.SOUTH, sprite, tintIndex,
				0, 0, 8.5f / 16f, sprite.getU0(), sprite.getV1(),
				1, 0, 8.5f / 16f, sprite.getU1(), sprite.getV1(),
				1, 1, 8.5f / 16f, sprite.getU1(), sprite.getV0(),
				0, 1, 8.5f / 16f, sprite.getU0(), sprite.getV0()
		));

		return builder.build();*/
	}

	private static BakedQuad buildSideQuad(Transformation transform, Direction side, int tintIndex, TextureAtlasSprite sprite, int u, int v, int size)
	{
		final float eps = 1e-2f;

		int width = sprite.getWidth();
		int height = sprite.getHeight();

		float x0 = (float) u / width;
		float y0 = (float) v / height;
		float x1 = x0;
		float y1 = y0;
		float z0 = 7.5f / 16f;
		float z1 = 8.5f / 16f;

		switch (side)
		{
			case WEST:
				z0 = 8.5f / 16f;
				z1 = 7.5f / 16f;
			case EAST:
				y1 = (float) (v + size) / height;
				break;
			case DOWN:
				z0 = 8.5f / 16f;
				z1 = 7.5f / 16f;
			case UP:
				x1 = (float) (u + size) / width;
				break;
			default:
				throw new IllegalArgumentException("can't handle z-oriented side");
		}

		float dx = side.getNormal().getX() * eps / width;
		float dy = side.getNormal().getY() * eps / height;

		float u0 = 16f * (x0 - dx);
		float u1 = 16f * (x1 - dx);
		float v0 = 16f * (1f - y0 - dy);
		float v1 = 16f * (1f - y1 - dy);

		return buildQuad(
				transform, remap(side), sprite, tintIndex,
				x0, y0, z0, sprite.getU(u0), sprite.getV(v0),
				x1, y1, z0, sprite.getU(u1), sprite.getV(v1),
				x1, y1, z1, sprite.getU(u1), sprite.getV(v1),
				x0, y0, z1, sprite.getU(u0), sprite.getV(v0)
		);
	}

	private static Direction remap(Direction side)
	{
		// getOpposite is related to the swapping of V direction
		return side.getAxis() == Direction.Axis.Y ? side.getOpposite() : side;
	}


	private static BakedQuad buildQuad(Transformation transform, Direction side, TextureAtlasSprite sprite, int tint,
			float x0, float y0, float z0, float u0, float v0,
			float x1, float y1, float z1, float u1, float v1,
			float x2, float y2, float z2, float u2, float v2,
			float x3, float y3, float z3, float u3, float v3)
	{
		BakedQuadBuilder builder = new BakedQuadBuilder(sprite);

		builder.setQuadTint(tint);
		builder.setQuadOrientation(side);
		builder.setApplyDiffuseLighting(false);

		boolean hasTransform = !transform.isIdentity();
		IVertexConsumer consumer = hasTransform ? new TRSRTransformer(builder, transform) : builder;

		putVertex(consumer, side, x0, y0, z0, u0, v0);
		putVertex(consumer, side, x1, y1, z1, u1, v1);
		putVertex(consumer, side, x2, y2, z2, u2, v2);
		putVertex(consumer, side, x3, y3, z3, u3, v3);

		return builder.build();
	}

	/**
	 * This method is pretty much the same as ItemLayerModel.putVertex(). The only change is the full bright code was removed.
	 */
	private static void putVertex(IVertexConsumer consumer, Direction side, float x, float y, float z, float u, float v)
	{
		VertexFormat format = consumer.getVertexFormat();
		for (int e = 0; e < format.getElements().size(); e++)
		{
			switch (format.getElements().get(e).getUsage())
			{
				case POSITION:
					consumer.put(e, x, y, z, 1f);
					break;
				case COLOR:
					consumer.put(e, 1f, 1f, 1f, 1f);
					break;
				case NORMAL:
					float offX = (float) side.getStepX();
					float offY = (float) side.getStepY();
					float offZ = (float) side.getStepZ();
					consumer.put(e, offX, offY, offZ, 0f);
					break;
				case UV:
					if (format.getElements().get(e).getIndex() == 0)
					{
						consumer.put(e, u, v, 0f, 1f);
						break;
					}
					// else fallthrough to default
				default:
					consumer.put(e);
					break;
			}
		}
	}


	public static class Loader implements IModelLoader<CosmicModelGeometry>
	{
		public static final Loader INSTANCE = new CosmicModelGeometry.Loader();

		@Override
		public void onResourceManagerReload(ResourceManager resourceManager)
		{
			// nothing to do
		}

		@Override
		public CosmicModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
		{
			ImmutableSet.Builder<Integer> overlayLayers = ImmutableSet.builder();
			if (modelContents.has("overlay_layers"))
			{
				JsonArray arr = GsonHelper.getAsJsonArray(modelContents, "overlay_layers");
				for (int i = 0; i < arr.size(); i++)
					overlayLayers.add(arr.get(i).getAsInt());
			}
			return new CosmicModelGeometry(null, overlayLayers.build());
		}
	}

}
