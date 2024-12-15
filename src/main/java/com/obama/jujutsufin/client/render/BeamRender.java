package com.obama.jujutsufin.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.obama.jujutsufin.entity.BeamProjectile;
import net.mcreator.jujutsucraft.client.model.Modelball;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class BeamRender extends EntityRenderer<BeamProjectile> {

    private static final Map<Float, String> textures = Map.of(
            2f, "jujutsufin:textures/entities/orange.png",
            8f, "jujutsufin:textures/entities/orange.png"
    );
    private final Modelball<BeamProjectile> model;

    public BeamRender(EntityRendererProvider.Context context) {
        super(context);
        model = new Modelball<>(context.bakeLayer(Modelball.LAYER_LOCATION));
    }

    @Override
    public void render(@NotNull BeamProjectile beamProjectile, float v, float v1, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        float scale = beamProjectile.getEntityData().get(BeamProjectile.scale) * 4;
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0, -1.38, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        RenderType rendertype = RenderType.eyes(getTextureLocation(beamProjectile));
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(rendertype);
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(false)), 1f, 1F, 1f,  1f);
        poseStack.popPose();
        super.render(beamProjectile, v, v1, poseStack, multiBufferSource, i);
    }



    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BeamProjectile beamProjectile) {
        return new ResourceLocation(textures.getOrDefault(beamProjectile.getEntityData().get(BeamProjectile.scale), "jujutsucraft:textures/entities/red.png"));
    }
}
