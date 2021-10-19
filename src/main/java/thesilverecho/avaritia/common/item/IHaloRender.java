package thesilverecho.avaritia.common.item;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface IHaloRender
{
	TextureAtlasSprite getHaloTexture();

	int getHaloColour();

	int getHaloSize();

	boolean shouldDrawPulse();

}
