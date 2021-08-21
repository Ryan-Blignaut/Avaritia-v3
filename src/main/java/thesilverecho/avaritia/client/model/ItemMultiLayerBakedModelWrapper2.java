package thesilverecho.avaritia.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.client.model.BakedItemModel;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.ItemMultiLayerBakedModel;

import java.util.Collection;
import java.util.List;

public class ItemMultiLayerBakedModelWrapper2 extends ItemMultiLayerBakedModel
{
	private static boolean isInInv;

	public ItemMultiLayerBakedModelWrapper2(boolean smoothLighting, boolean shadedInGui, boolean sideLit, TextureAtlasSprite particle, ItemOverrides overrides, ImmutableMap<ItemTransforms.TransformType, Transformation> cameraTransforms, ImmutableList<Pair<BakedModel, RenderType>> layerModels)
	{
		super(smoothLighting, shadedInGui, sideLit, particle, overrides, cameraTransforms, layerModels);
	}


	public static CustomBuilder builder1(IModelConfiguration owner, TextureAtlasSprite particle, ItemOverrides overrides, ImmutableMap<ItemTransforms.TransformType, Transformation> cameraTransforms)
	{
		return new CustomBuilder(owner, particle, overrides, cameraTransforms);
	}

	@Override
	public BakedModel handlePerspective(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack)
	{
		/*CosmicShader.inventory*/
		isInInv = cameraTransformType == ItemTransforms.TransformType.GUI;
		return super.handlePerspective(cameraTransformType, poseStack);
	}

	public static class CustomBuilder
	{
		private final ImmutableList.Builder<Pair<BakedModel, RenderType>> builder = ImmutableList.builder();
		private final List<BakedQuad> quads = Lists.newArrayList();
		private final ItemOverrides overrides;
		private final ImmutableMap<ItemTransforms.TransformType, Transformation> cameraTransforms;
		private final IModelConfiguration owner;
		private final TextureAtlasSprite particle;
		private RenderType lastRt = null;

		private CustomBuilder(IModelConfiguration owner, TextureAtlasSprite particle, ItemOverrides overrides,
				ImmutableMap<ItemTransforms.TransformType, Transformation> cameraTransforms)
		{
			this.owner = owner;
			this.particle = particle;
			this.overrides = overrides;
			this.cameraTransforms = cameraTransforms;
		}

		private void addLayer(ImmutableList.Builder<Pair<BakedModel, RenderType>> builder, List<BakedQuad> quads, RenderType rt)
		{
			BakedModel model = new BakedItemModel(ImmutableList.copyOf(quads), particle, ImmutableMap.of(), ItemOverrides.EMPTY, true, owner.isSideLit());
			builder.add(Pair.of(model, rt));
		}

		private void flushQuads(RenderType rt)
		{
			if (rt != lastRt)
			{
				if (quads.size() > 0)
				{
					addLayer(builder, quads, lastRt);
					quads.clear();
				}
				lastRt = rt;
			}
		}

		public CustomBuilder addQuads(RenderType rt, Collection<BakedQuad> quadsToAdd)
		{
			flushQuads(rt);
			quads.addAll(quadsToAdd);
			return this;
		}

		public BakedModel build()
		{
			if (quads.size() > 0)
				addLayer(builder, quads, lastRt);
			return new ItemMultiLayerBakedModelWrapper2(owner.useSmoothLighting(), owner.isShadedInGui(), owner.isSideLit(),
					particle, overrides, cameraTransforms, builder.build());
		}
	}


}

