package thesilverecho.avaritia.client.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;

public class ExtremeBakedModel extends InnerBakedModel
{
	private final boolean pulse;
	private final TextureAtlasSprite backgroundTexture;
	private final int backgroundColour, size;

	private final int lightBeamColour, lightBeamCount;

	public ExtremeBakedModel(BakedModel innerModel, boolean pulse, TextureAtlasSprite backgroundTexture, int backgroundColour, int size, int lightBeamColour, int lightBeamCount)
	{
		super(innerModel);
		this.pulse = pulse;
		this.backgroundTexture = backgroundTexture;
		this.backgroundColour = backgroundColour;
		this.size = size;
		this.lightBeamColour = lightBeamColour;
		this.lightBeamCount = lightBeamCount;
	}

	public boolean shouldPulse()
	{
		return pulse;
	}

	public TextureAtlasSprite getBackgroundTexture()
	{
		return backgroundTexture;
	}

	public int getBackgroundColour()
	{
		return backgroundColour;
	}

	public int getSize()
	{
		return size;
	}

	public int getLightBeamColour()
	{
		return lightBeamColour;
	}

	public int getLightBeamCount()
	{
		return lightBeamCount;
	}
}
