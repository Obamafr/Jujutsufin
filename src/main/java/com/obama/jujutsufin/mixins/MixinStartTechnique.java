package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.StartCursedTechniqueProcedure;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StartCursedTechniqueProcedure.class)
public class MixinStartTechnique {
    @Inject(method = "execute", at = @At("TAIL"), remap = false)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        int used = (int) entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerTechniqueUsedNumber;
        if (entity instanceof ServerPlayer serverPlayer) {
            if (used >= 6000) {
                Advancement veil = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsufin:veil"));
                if (veil != null) {
                    AdvancementProgress veilProgress = serverPlayer.getAdvancements().getOrStartProgress(veil);
                    if (!veilProgress.isDone()) {
                        veilProgress.getRemainingCriteria().forEach(c -> serverPlayer.getAdvancements().award(veil, c));
                    }
                }
            }
            if (used >= 10000) {
                Advancement hwb = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsufin:hwb"));
                if (hwb != null) {
                    AdvancementProgress veilProgress = serverPlayer.getAdvancements().getOrStartProgress(hwb);
                    if (!veilProgress.isDone()) {
                        veilProgress.getRemainingCriteria().forEach(c -> serverPlayer.getAdvancements().award(hwb, c));
                    }
                }
            }
        }
    }
}
