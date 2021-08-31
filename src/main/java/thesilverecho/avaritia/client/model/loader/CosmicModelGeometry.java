package thesilverecho.avaritia.client.model.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.RenderType;
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
import thesilverecho.avaritia.client.render_layer.TestLayer;

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
		return isOverlay ? /*new ShaderWrappedRenderLayer(ForgeRenderTypes.ITEM_UNSORTED_UNLIT_TRANSLUCENT.get()*//*, CosmicShader.COSMIC_SHADER*//*)*/new TestLayer(ForgeRenderTypes.ITEM_UNSORTED_UNLIT_TRANSLUCENT.get()) : ForgeRenderTypes.ITEM_UNSORTED_UNLIT_TRANSLUCENT.get();
	}


	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation)
	{
		final ImmutableMap<ItemTransforms.TransformType, Transformation> transformMap = PerspectiveMapWrapper.getTransforms(new CompositeModelState(owner.getCombinedTransform(), modelTransform));
		final Transformation transform = modelTransform.getRotation();
		final TextureAtlasSprite particle = spriteGetter.apply(owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : textures.get(0));

		ItemMultiLayerBakedModel.Builder builder = ItemMultiLayerBakedModel.builder(owner, particle, overrides, transformMap);

		for (int i = 0; i < textures.size(); i++)
		{
			TextureAtlasSprite tas = spriteGetter.apply(textures.get(i));

			RenderType rt = getLayerRenderType(overlayLayers.contains(i));
			builder.addQuads(rt, ItemLayerModel.getQuadsForSprite(i, tas, transform, true));
		}

		return builder.build();
	}

	/*@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation)
	{
		final ImmutableMap<ItemTransforms.TransformType, Transformation> transforms = PerspectiveMapWrapper.getTransforms(new CompositeModelState(owner.getCombinedTransform(), modelTransform));
		final Transformation transform = modelTransform.getRotation();
		final TextureAtlasSprite particle = spriteGetter.apply(owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : textures.get(0));
		final ItemMultiLayerBakedModelWrapper2.CustomBuilder builder = ItemMultiLayerBakedModelWrapper2.builder1(owner, particle, overrides, transforms);
//		ItemMultiLayerBakedModelWrapper.Builder builder = ItemMultiLayerBakedModelWrapper.builder(owner, particle, overrides, transformMap);
		for (int i = 0; i < textures.size(); i++)
		{
			TextureAtlasSprite tas = spriteGetter.apply(textures.get(i));
			RenderType rt = getLayerRenderType(overlayLayers.contains(i));
			builder.addQuads(rt, ItemLayerModel.getQuadsForSprite(i, tas, transform, false));
		}

		return builder.build();
	}*/

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
