package thesilverecho.avaritia.client.shader;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.RegisterShadersEvent;
import org.lwjgl.stb.STBImageWrite;
import thesilverecho.avaritia.common.Avaritia;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.stb.STBImageWrite.stbi_write_png;
import static org.lwjgl.system.MemoryUtil.memAlloc;
import static org.lwjgl.system.MemoryUtil.memFree;

public class ModShaders
{
	public static ShaderInstance cosmicShader;
	private static final ArrayList<TextureAtlasSprite> COSMIC_LOCATIONS = new ArrayList<>();

	public static void registerShaders(RegisterShadersEvent event) throws IOException
	{
		event.registerShader(new CustomUniformBindShader(event.getResourceManager(), new ResourceLocation(Avaritia.MOD_ID, "cosmic_render1"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> ModShaders.cosmicShader = shaderInstance);
	}


	public static class CustomUniformBindShader extends ShaderInstance
	{

		public CustomUniformBindShader(ResourceProvider pResourceProvider, ResourceLocation shaderLocation, VertexFormat pVertexFormat) throws IOException
		{
			super(pResourceProvider, shaderLocation, pVertexFormat);
		}

		boolean t = false;

		@Override
		public void apply()
		{
			super.apply();

			float yaw = (float) ((Minecraft.getInstance().player.getYRot()/*x*/ * 2 * Math.PI) / 360.0);
			float pitch = -(float) ((Minecraft.getInstance().player.getXRot() /*y*/ * 2 * Math.PI) / 360.0);
			this.safeGetUniform("Yaw").set(yaw);
			this.safeGetUniform("Pitch").set(pitch);

			final Uniform vs = ModShaders.cosmicShader.getUniform("CosmicUVs");
//			if (vs != null)
			{
				if (COSMIC_LOCATIONS.isEmpty())
					for (int i = 0; i < 10; i++)
						COSMIC_LOCATIONS.add(Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(Avaritia.MOD_ID, "shader/cosmic_" + i)));
				float[] cosmicUVs = new float[COSMIC_LOCATIONS.size() * 4];
				for (int i = 0; i < COSMIC_LOCATIONS.size(); i++)
				{
					TextureAtlasSprite textureAtlasSprite = COSMIC_LOCATIONS.get(i);
					cosmicUVs[i * 4] = (textureAtlasSprite.getU0());
					cosmicUVs[i * 4 + 1] = (textureAtlasSprite.getV0());
					cosmicUVs[i * 4 + 2] = (textureAtlasSprite.getU1());
					cosmicUVs[i * 4 + 3] = (textureAtlasSprite.getV1());

					if (!t)
					{
						System.out.println("U  0 " + cosmicUVs[i]);
						System.out.println("V  0 " + cosmicUVs[i + 1]);
						System.out.println("U  1 " + cosmicUVs[i + 2]);
						System.out.println("V  1 " + cosmicUVs[i + 3]);
					}
				}

//				System.out.println(cosmicUVs[2]);
				t = true;
//				System.out.println(cosmicUVs[1]);
//				cosmicUVs.flip();
//				vs.upload();
//				System.out.println(cosmicUVs[0]);
				this.safeGetUniform("CosmicUVs").set(cosmicUVs);

				this.safeGetUniform("ExternalScale").set(1f);

				this.safeGetUniform("Time").set(Minecraft.getInstance().player.level.dayTime() % Integer.MAX_VALUE);
//				float yaw = (float) ((Minecraft.getInstance().player.getYRot()/*x*/ * 2 * Math.PI) / 360.0);
//				float pitch = -(float) ((Minecraft.getInstance().player.getXRot() /*y*/ * 2 * Math.PI) / 360.0);
//				this.getUniform("Yaw").set(yaw);
//				this.getUniform("Pitch").set(pitch);

		/*		if (!t)
				{
					t = true;
					Minecraft.getInstance().getTextureManager().getTexture(InventoryMenu.BLOCK_ATLAS).bind();
					ByteBuffer image = memAlloc(1024 * 1024 * 4);
					glReadPixels(0, 0, 1024, 1024, GL_RGBA, GL_UNSIGNED_BYTE, image);
					STBImageWrite.stbi_write_png("test.png", 1024, 1024, 4, image, 512 * 4);
					memFree(image);
				}*/


			}
		}
	}

}
