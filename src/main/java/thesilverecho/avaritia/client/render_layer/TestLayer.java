package thesilverecho.avaritia.client.render_layer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;
import thesilverecho.avaritia.client.shader.ModShaders;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class TestLayer extends RenderType
{
	private final RenderType original;

	public TestLayer(RenderType original)
	{
		super("avaritia" + original.toString(), original.format(), original.mode(), original.bufferSize(), true, original.affectsCrumbling(), () ->
		{
			original.setupRenderState();
			RenderSystem.setShader(() -> ModShaders.testShader);
		}, () ->
		{
			RenderSystem.setShader(() -> null);
			original.clearRenderState();
		});
		this.original = original;
	}

	public Optional<RenderType> test()
	{
		return this.original.outline();
	}

	public boolean equals(@Nullable Object other)
	{
		return other instanceof TestLayer && this.original.equals(((TestLayer) other).original);
	}

	public int hashCode()
	{
		return Objects.hash(this.original);
	}
}
