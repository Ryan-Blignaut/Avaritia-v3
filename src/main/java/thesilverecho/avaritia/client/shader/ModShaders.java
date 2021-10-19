package thesilverecho.avaritia.client.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterShadersEvent;
import thesilverecho.avaritia.common.Avaritia;

import java.io.IOException;

public class ModShaders
{
	public static ShaderInstance cosmicShader;

	public static void registerShaders(RegisterShadersEvent event) throws IOException
	{
		event.registerShader(new ShaderInstance(event.getResourceManager(), new ResourceLocation(Avaritia.MOD_ID, "cosmic_render"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> ModShaders.cosmicShader = shaderInstance);
	}
}
