package com.obama.jujutsufin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.obama.jujutsufin.entity.PigeonViola;
import net.mcreator.jujutsucraft.client.model.Modelball;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PigeonViolaRender extends MobRenderer<PigeonViola, Modelball<PigeonViola>> {

    public PigeonViolaRender(EntityRendererProvider.Context context) {
        super(context, new Modelball<>(context.bakeLayer(Modelball.LAYER_LOCATION)), 0.0F);
    }

    @Override
    public void render(@NotNull PigeonViola pigeonViola, float v, float v1, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.scale(3, 3, 3);
        poseStack.translate(0, -1.38, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        VertexConsumer vertexConsumerEye = multiBufferSource.getBuffer(RenderType.eyes(getTextureLocation(pigeonViola)));
        this.model.renderToBuffer(poseStack, vertexConsumerEye, i, OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(false)), 1f, 1F, 1f,  1f);
        poseStack.popPose();
    }

    private static final Map<Integer, String> textures = Map.of(
            0, "jujutsucraft:textures/entities/red.png",
            1, "jujutsucraft:textures/entities/blue.png",
            2, "jujutsucraft:textures/entities/purple.png",
            3, "jujutsufin:textures/entities/gold.png",
            4, "jujutsufin:textures/entities/green.png"
    );

    public @NotNull ResourceLocation getTextureLocation(@NotNull PigeonViola pigeonViola) {
        return new ResourceLocation(textures.getOrDefault(pigeonViola.getEntityData().get(PigeonViola.colorType),"jujutsucraft:textures/entities/red.png"));
    }
}
