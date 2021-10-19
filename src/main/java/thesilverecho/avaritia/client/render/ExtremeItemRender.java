package thesilverecho.avaritia.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import thesilverecho.avaritia.client.model.InnerBakedModel;

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
		if (!(iBakedModel instanceof final InnerBakedModel innerBakedModel))
			return;

		BakedModel innerModel = innerBakedModel.getInnerModel();
		pPoseStack.translate(.5f, .5f, .5f);

		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

		if (pTransformType == ItemTransforms.TransformType.GUI)
		{
			final TextureAtlasSprite sprite = innerBakedModel.backgroundTexture();
			if (sprite != null)
			{
				final float spread = innerBakedModel.size() / 16f;
				final float min = -0.5f - spread;
				final float max = 0.5f + spread;

				final float minU = sprite.getU0();
				final float maxU = sprite.getU1();
				final float minV = sprite.getV0();
				final float maxV = sprite.getV1();


				final int haloColour = innerBakedModel.colour();
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
		if (innerBakedModel.pulse())
		{
			float scale = (float) (new Random().nextFloat() * 0.15 + 0.95);
			double trans = (1 - scale) / 2;
			pPoseStack.translate(trans, trans, 0);
			pPoseStack.scale(scale, scale, 1.0001f);
		}
		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		Minecraft.getInstance().getItemRenderer().render(pStack, pTransformType, false, pPoseStack, pBuffer, pPackedLight, pOverlay, innerModel);
		pPoseStack.popPose();
	}
}
