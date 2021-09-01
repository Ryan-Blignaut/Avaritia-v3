package thesilverecho.avaritia.client.render_layer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;
import thesilverecho.avaritia.client.shader.ModShaders;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class CosmicRenderLayer extends RenderType
{
	private final RenderType delegate;

	public CosmicRenderLayer(RenderType delegate)
	{
		super("avaritia_cosmic" + delegate.toString(), delegate.format(), delegate.mode(), delegate.bufferSize(), true, delegate.isOutline(), () ->
		{
			delegate.setupRenderState();
			RenderSystem.setShader(() -> ModShaders.cosmicShader);
		}, () ->
		{
			RenderSystem.setShader(() -> null);
			delegate.clearRenderState();
		});
		this.delegate = delegate;
	}

	@Override
	public Optional<RenderType> outline()
	{
		return this.delegate.outline();
	}

	@Override
	public boolean equals(@Nullable Object other)
	{
		return other instanceof CosmicRenderLayer && this.delegate.equals(((CosmicRenderLayer) other).delegate);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.delegate);
	}

}
