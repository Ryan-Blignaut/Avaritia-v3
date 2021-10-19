package thesilverecho.avaritia.client.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterShadersEvent;
import thesilverecho.avaritia.common.Avaritia;

import java.io.IOException;

public class ModShaders
{
	public static CosmicShader cosmicShader;
	public static CosmicShader testShader;

	public static void registerShaders(RegisterShadersEvent event) throws IOException
	{
		event.registerShader(new CosmicShader(event.getResourceManager(), new ResourceLocation(Avaritia.MOD_ID, "cosmic"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> ModShaders.cosmicShader = (CosmicShader) shaderInstance);
		event.registerShader(new CosmicShader(event.getResourceManager(), new ResourceLocation(Avaritia.MOD_ID, "rendertype_end_portal"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> ModShaders.testShader = (CosmicShader) shaderInstance);

	}
}
