package com.obama.jujutsufin.client.render;

import com.obama.jujutsufin.entity.VeilEntity;
import net.mcreator.jujutsucraft.client.model.Modelball;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VeilRender extends MobRenderer<VeilEntity, Modelball<VeilEntity>> {
    public VeilRender(EntityRendererProvider.Context context) {
        super(context, new Modelball<>(context.bakeLayer(Modelball.LAYER_LOCATION)), 0);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull VeilEntity veilEntity) {
        return new ResourceLocation("jujutsucraft:textures/entities/clear.png");
    }

}
