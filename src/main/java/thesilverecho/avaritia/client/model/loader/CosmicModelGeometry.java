package thesilverecho.avaritia.client.model.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import thesilverecho.avaritia.client.render_layer.ModRenderLayers;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class CosmicModelGeometry implements IModelGeometry<CosmicModelGeometry>
{
	private final ImmutableSet<Integer> overlayLayers;
	private ImmutableList<Material> textures;

	public CosmicModelGeometry(@Nullable ImmutableList<Material> textures, ImmutableSet<Integer> overlayLayers)
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
		return isOverlay ? ModRenderLayers.getUnsortedTranslucent() : ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get();
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
			final ImmutableList<BakedQuad> quadsForSprite = getQuadsForSprite(isOverlayLayer, i, tas, transform);
			builder.addQuads(rt, quadsForSprite);
		}

		return builder.build();
	}

	private ImmutableList<BakedQuad> getQuadsForSprite(boolean overlayLayer, int i, TextureAtlasSprite tas, Transformation transform)
	{

//		transform.getScale().mul(0.1f);
		transform.getScale().add(32, 32, 32);
		transform.applyOrigin(new Vector3f(1, 3, 1));
		final Matrix4f matrix = transform.getMatrix();
		if (!overlayLayer)
		{
//			matrix.multiply(0.95f);
		} else
		{
//			Add a little offset to try and prevent z-fighting.
			final float v = 0.009f / 2f;

//			matrix.multiplyWithTranslation(0.0005f, 0.0001f, 0.0001f);
//			matrix.translate(new Vector3f(-0.004f, -0.0041f, 0));

//			matrix.setTranslation(-v, -v, -v);
//			matrix.setTranslation(-0.100f, -0.100f, -0.100f);
//			matrix.multiplyWithTranslation(-.200f, -.200f, -.200f);
//			matrix.multiplyWithTranslation(-.1f / 2, -.1f / 2, -.1f / 2);
//			matrix.multiply(1.1f);
//			matrix.multiply(new Matrix4f(new Quaternion(1,1,1,1)));
			//			matrix.multiplyWithTranslation(-0.1f, 0.1f, -0.1f);
//			matrix.multiply(Matrix4f.createScaleMatrix(1.1f,1.1f,1.1f));
//			matrix.add(Matrix4f.createScaleMatrix(1.1f,1.1f,1.1f));
		}
		return ItemLayerModel.getQuadsForSprite(i, tas, new Transformation(matrix)/*transform*/, false);
	}


	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
	{
		this.textures = getTextures(owner);
		return textures;
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
