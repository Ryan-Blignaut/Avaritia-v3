package thesilverecho.avaritia.client.shader;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;

public class TestShader extends ShaderInstance
{
	public TestShader(ResourceProvider resourceProvider, ResourceLocation shaderLocation, VertexFormat vertexFormat) throws IOException
	{
		super(resourceProvider, shaderLocation, vertexFormat);
	}
}
