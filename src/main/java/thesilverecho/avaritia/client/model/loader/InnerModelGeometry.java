package thesilverecho.avaritia.client.model.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import thesilverecho.avaritia.client.model.InnerBakedModel;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class InnerModelGeometry implements IModelGeometry<InnerModelGeometry>
{
	private final boolean pulse;
	private final Material backgroundMaterial;
	private final int colour, size;
	private ResourceLocation parentLocation;
	private UnbakedModel innerModel;

	public InnerModelGeometry(ResourceLocation parentLocation, boolean pulse, Material backgroundMaterial, int colour, int size)
	{
		this.parentLocation = parentLocation;
		this.backgroundMaterial = backgroundMaterial;
		this.pulse = pulse;
		this.colour = colour;
		this.size = size;
	}

	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation)
	{
		final TextureAtlasSprite backgroundTexture = spriteGetter.apply(backgroundMaterial);
		final BakedModel innerBakedModel = this.innerModel.bake(bakery, spriteGetter, modelTransform, parentLocation);
		return new InnerBakedModel(innerBakedModel, pulse, backgroundTexture, colour, size);
	}

	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, UnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors)
	{
		this.innerModel = modelGetter.apply(this.parentLocation);
		if (this.innerModel == null)
		{
			System.out.println("NO parent found");
			this.parentLocation = ModelBakery.MISSING_MODEL_LOCATION;
			this.innerModel = modelGetter.apply(this.parentLocation);
		}
		if (!(this.innerModel instanceof BlockModel))
			throw new IllegalStateException("BlockModel parent has to be a block model.");

		final Collection<Material> materials = innerModel.getMaterials(modelGetter, missingTextureErrors);
		if (this.backgroundMaterial != null) materials.add(this.backgroundMaterial);
		return materials;
	}


	public static class Loader implements IModelLoader<InnerModelGeometry>
	{
		public static final Loader INSTANCE = new InnerModelGeometry.Loader();

		@Override
		public void onResourceManagerReload(ResourceManager resourceManager)
		{
			// nothing to do
		}

		@Override
		public InnerModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
		{
			final String parent = modelContents.get("parent").getAsString();
			final ResourceLocation parentLocation = new ResourceLocation(parent);

			final boolean pulse = modelContents.has("pulse") && modelContents.get("pulse").getAsBoolean();
			Material back = null;
			int colour = 0xFF000000;
			int size = 8;
			if (modelContents.has("background"))
			{
				final JsonObject background = modelContents.getAsJsonObject("background");
				if (background.has("texture"))
				{
					final Optional<Material> texture = parseTextureLocationOrReference(background.get("texture").getAsString()).left();
					if (texture.isPresent())
						back = texture.get();
				}
				if (background.has("colour")) colour = background.get("colour").getAsInt();
				if (background.has("size")) size = background.get("size").getAsInt();
			}
			return new InnerModelGeometry(parentLocation, pulse, back, colour, size);
		}

		private static Either<Material, String> parseTextureLocationOrReference(String pName)
		{
			ResourceLocation resourcelocation = ResourceLocation.tryParse(pName);
			if (resourcelocation == null)
				throw new JsonParseException(pName + " is not valid resource location");
			else
				return Either.left(new Material(InventoryMenu.BLOCK_ATLAS, resourcelocation));
		}
	}

}
