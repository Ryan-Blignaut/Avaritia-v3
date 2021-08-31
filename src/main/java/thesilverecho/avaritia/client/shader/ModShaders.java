package thesilverecho.avaritia.client.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import thesilverecho.avaritia.common.Avaritia;

import java.io.IOException;

public class ModShaders
{
	public static TestShader testShader;

	@SubscribeEvent
	public static void registerShaders(RegisterShadersEvent event) throws IOException
	{
		event.registerShader(new TestShader(event.getResourceManager(), new ResourceLocation(Avaritia.MOD_ID, "shaders/fullbright"), DefaultVertexFormat.POSITION_TEX_COLOR), shaderInstance -> testShader = (TestShader) shaderInstance);
	}


	/*private static <T extends ShaderInstance> T registerShader(T shaderClazz, RegisterShadersEvent event)
	{

		final ShaderInstance[] shaderInstance1 = new ShaderInstance[1];
		event.registerShader(testShader, shaderInstance -> shaderInstance1[0] = shaderInstance);
		if (T.getClass().isInstance(shaderInstance1))
		{
		}

		return (T) shaderClazz.getClass().cast(shaderInstance1[0]);
	}*/


}
