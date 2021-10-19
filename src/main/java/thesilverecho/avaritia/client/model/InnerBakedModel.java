package thesilverecho.avaritia.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Random;

public record InnerBakedModel(BakedModel innerModel, boolean pulse, TextureAtlasSprite backgroundTexture,
                              int colour,
                              int size) implements BakedModel
{
	@Override
	public List<BakedQuad> getQuads(final BlockState state, final Direction side, final Random rand)
	{
		return innerModel.getQuads(state, side, rand);
	}

	@Override
	public boolean useAmbientOcclusion()
	{
		return innerModel.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d()
	{
		return innerModel.isGui3d();
	}

	@Override
	public boolean usesBlockLight()
	{
		return innerModel.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer()
	{
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleIcon()
	{
		return innerModel.getParticleIcon();
	}

	@Override
	public ItemOverrides getOverrides()
	{
		return innerModel.getOverrides();
	}

	public BakedModel getInnerModel()
	{
		return innerModel;
	}


}
