package thesilverecho.avaritia.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL45;
import thesilverecho.avaritia.client.model.ExtremeBakedModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class ExtremeItemRender extends BlockEntityWithoutLevelRenderer
{
	public static BlockEntityWithoutLevelRenderer SUPPLIER = new ExtremeItemRender();
	protected static Map<ItemStack, BakedModel> itemModelCache = new HashMap<>();

	public ExtremeItemRender()
	{
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	@Override
	public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pOverlay)
	{
		pPoseStack.pushPose();
		final BakedModel iBakedModel = itemModelCache.computeIfAbsent(pStack, stack1 -> Minecraft.getInstance().getItemRenderer().getModel(pStack, Minecraft.getInstance().level, null, 0));
		if (!(iBakedModel instanceof final ExtremeBakedModel extremeBakedModel))
			return;

		BakedModel innerModel = extremeBakedModel.getInnerModel();
		pPoseStack.translate(.5f, .5f, .5f);

		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		if (pTransformType == ItemTransforms.TransformType.GUI)
		{
			final TextureAtlasSprite sprite = extremeBakedModel.getBackgroundTexture();
			if (sprite != null)
			{
				final float spread = extremeBakedModel.getSize() / 16f;
				final float min = -0.5f - spread;
				final float max = 0.5f + spread;

				final float minU = sprite.getU0();
				final float maxU = sprite.getU1();
				final float minV = sprite.getV0();
				final float maxV = sprite.getV1();


				final int haloColour = extremeBakedModel.getBackgroundColour();
				final float alpha = (float) (haloColour >> 24 & 255) / 255.0F;
				final float red = (float) (haloColour >> 16 & 255) / 255.0F;
				final float green = (float) (haloColour >> 8 & 255) / 255.0F;
				final float blue = (float) (haloColour & 255) / 255.0F;

				final Matrix4f matrix4f = pPoseStack.last().pose();
				RenderSystem.setShaderColor(red, green, blue, alpha);
				RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
				BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
				bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
				bufferbuilder.vertex(matrix4f, max, max, 0).color(red, blue, green, alpha).uv(maxU, minV).endVertex();
				bufferbuilder.vertex(matrix4f, min, max, 0).color(red, blue, green, alpha).uv(minU, minV).endVertex();
				bufferbuilder.vertex(matrix4f, min, min, 0).color(red, blue, green, alpha).uv(minU, maxV).endVertex();
				bufferbuilder.vertex(matrix4f, max, min, 0).color(red, blue, green, alpha).uv(maxU, maxV).endVertex();
				bufferbuilder.end();
				BufferUploader.end(bufferbuilder);
			}
		}
//		if (extremeBakedModel.shouldPulse())
//		{
//			float scale = (float) (new Random().nextFloat() * 0.15 + 0.95);
//			double trans = (1 - scale) / 2;
//			pPoseStack.translate(trans, trans, 0);
//			pPoseStack.scale(scale, scale, 1.0001f);
//		}

		if (extremeBakedModel.getLightBeamCount() > 0)
		{
//			float f5 = (float) (System.currentTimeMillis() / 50 / 400) / 200.0F;
//			float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
			final long l = System.currentTimeMillis() / 50;
			final float time = l / 400f;

			Random random = new Random(432L);
			VertexConsumer vb = pBuffer.getBuffer(RenderType.endPortal());

			pPoseStack.pushPose();
//			pPoseStack.translate(0.0D, -1.0D, -2.0D);

			for (int i = 0; (float) i < 1; i++)
			{
				pPoseStack.pushPose();
//				pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));

//				pPoseStack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
//				pPoseStack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
//				pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
//				pPoseStack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
//				pPoseStack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
//				pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + time * 360.0F));

				Matrix4f matrix4f = pPoseStack.last().pose();

				float alpha = 25;
				final int pAlpha = 25;


				float red = 100;
				float green = 50;
				float blue = 10;
//				vb.vertex(matrix4f, 0F, 0F, 0F).color(red, green, blue, alpha).endVertex();
//				vb.vertex(matrix4f, 1F, 0F, 0F).color(red, green, blue, alpha).endVertex();
//				vb.vertex(matrix4f, 1F, 1F, 0F).color(red, green, blue, alpha).endVertex();
//				vb.vertex(matrix4f, 0F, 1F, 0F).color(red, green, blue, alpha).endVertex();

				float num_segments = 120;
				float r = 1;


				float angle = 0.0f;
			/*	while (angle < 2 * 3.1415926f)
				{
					float x = (float) (r * Math.cos(angle));
					float y = (float) (r * Math.sin(angle));
					vb.vertex(matrix4f, x, y, 1F).color(red, green, blue, alpha).endVertex();
					vb.vertex(matrix4f, x, y, 0F).color(red, green, blue, alpha).endVertex();
					angle = angle + 0.1f;

					x = (float) (r * Math.cos(angle));
					y = (float) (r * Math.sin(angle));
					vb.vertex(matrix4f, x, y, 0F).color(red, green, blue, alpha).endVertex();
					vb.vertex(matrix4f, x, y, 1F).color(red, green, blue, alpha).endVertex();
//					angle = angle + 0.1f;
				}
				vb.vertex(matrix4f, r, 0.0f, 1).color(red, green, blue, alpha).endVertex();
				vb.vertex(matrix4f, r, 0.0f, 0F).color(red, green, blue, alpha).endVertex();*/


			/*	final int slices = 10;
				for (int j = 0; j < slices; j++)
				{
					float dtheta = (float) (2.0f * Math.PI / slices);
					float theta = j * dtheta;
					for (i = 0; i <= 10; i++)
					{
						float rho = (float) (i * Math.PI / 10);
						float x = (float) (Math.cos(theta) * Math.sin(rho));
						float y = (float) (Math.sin(theta) * Math.sin(rho));
						float z = (float) Math.cos(rho);
						vb.vertex(matrix4f, x * r, y * r, z * r).color(red, green, blue, alpha).endVertex();
					}
				}*/

//				draw(vb, matrix4f, 0.5f, 100, 100);
				int nSegments = 4;
				int nSlices = 4;
				for (int slice = 0; slice < nSlices; slice++)
				{
					float lat0 = (float) (Math.PI * (((slice - 1f) / nSlices) - 0.5));
					float z0 = (float) Math.sin(lat0);
					float zr0 = (float) Math.cos(lat0);

					float lat1 = (float) (Math.PI * (((float) slice / nSlices) - 0.5f));
					float z1 = (float) Math.sin(lat1);
					float zr1 = (float) Math.cos(lat1);

					for (float segment = 0.0f; segment < nSegments; segment += 1.0)
					{
						float long0 = (float) (2 * Math.PI * ((segment - 1) / nSegments));
						float x0 = (float) Math.cos(long0);
						float y0 = (float) Math.sin(long0);

						float long1 = (float) (2 * Math.PI * (segment / nSegments));
						float x1 = (float) Math.cos(long1);
						float y1 = (float) Math.sin(long1);


						vb.vertex(matrix4f, x0 * zr0, y0 * zr0, z0).color(red, green, blue, alpha).endVertex();
						vb.vertex(matrix4f, x1 * zr0, y1 * zr0, z0).color(red, green, blue, alpha).endVertex();
						vb.vertex(matrix4f, x1 * zr1, y1 * zr1, z1).color(red, green, blue, alpha).endVertex();
						vb.vertex(matrix4f, x0 * zr1, y0 * zr1, z1).color(red, green, blue, alpha).endVertex();
					}
				}

			/*	for (int segment = 0; segment < num_segments; segment++)
				{
					float theta = 2.0f * 3.1415926f * segment / num_segments;//get the current angle
					float x = (float) (r * Math.cos(theta));
					float y = (float) (r * Math.sin(theta));

					vb.vertex(matrix4f, x, y, 0F).color(red, green, blue, alpha).endVertex();
					vb.vertex(matrix4f, x, y, 1F).color(red, green, blue, alpha).endVertex();

					theta = 2.0f * 3.1415926f * (segment + 1) / (num_segments);//get the current angle
					x = (float) (r * Math.cos(theta));//calculate the x component
					y = (float) (r * Math.sin(theta));//calculate the y component


					vb.vertex(matrix4f, x, y, 1F).color(red, green, blue, alpha).endVertex();
					vb.vertex(matrix4f, x, y, 0F).color(red, green, blue, alpha).endVertex();
				}
*/
				pPoseStack.popPose();

			}
			pPoseStack.popPose();

		}

		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		Minecraft.getInstance().getItemRenderer().render(pStack, pTransformType, false, pPoseStack, pBuffer, pPackedLight, pOverlay, innerModel);
		pPoseStack.popPose();
	}


	public void draw(VertexConsumer consumer, Matrix4f matrix4f, float radius, int slices, int stacks)
	{
		float rho, drho, theta, dtheta;
		float x, y, z;
		int i, j, imin, imax;
		float nsign;
		float red = 100;
		float green = 50;
		float blue = 10;
		float alpha = 0.2f;

		nsign = 1.0f;

		drho = (float) (Math.PI / stacks);
		dtheta = (float) (2.0f * Math.PI / slices);

		imin = 1;
		imax = stacks - 1;

		for (i = imin; i < imax; i++)
		{
			rho = i * drho;
			for (j = 0; j <= slices; j++)
			{
				theta = (j == slices) ? 0.0f : j * dtheta;
				x = (float) (-Math.sin(theta) * Math.sin(rho));
				y = (float) (Math.cos(theta) * Math.sin(rho));
				z = (float) (nsign * Math.cos(rho));
				consumer.vertex(matrix4f, x * radius, y * radius, z * radius).color(red, green, blue, alpha).endVertex();
				x = (float) (-Math.sin(theta) * Math.sin(rho + drho));
				y = (float) (Math.cos(theta) * Math.sin(rho + drho));
				z = (float) (nsign * Math.cos(rho + drho));
				consumer.vertex(matrix4f, x * radius, y * radius, z * radius).color(red, green, blue, alpha).endVertex();
			}
		}

	}


}
