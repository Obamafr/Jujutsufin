package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.procedures.MasterSkillsProcedure;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MasterSkillsProcedure.class)
public class MixinMasterSkills {
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;level()Lnet/minecraft/world/level/Level;", ordinal = 1), cancellable = true, remap = false)
    private static void grantBurnout(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity instanceof ServerPlayer serverPlayer) {
            Advancement rct2 = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsucraft:reverse_cursed_technique_2"));
            if (rct2 != null && serverPlayer.getAdvancements().getOrStartProgress(rct2).isDone()) {
                Advancement rctB = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsufin:burnout"));
                if (rctB != null) {
                    AdvancementProgress rctBProgress = serverPlayer.getAdvancements().getOrStartProgress(rctB);
                    if (!rctBProgress.isDone()) {
                        rctBProgress.getRemainingCriteria().forEach(c -> serverPlayer.getAdvancements().award(rctB, c));
                        itemstack.shrink(1);
                        ci.cancel();
                    }
                }
            }
        }
    }

    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;level()Lnet/minecraft/world/level/Level;", ordinal = 5), cancellable = true, remap = false)
    private static void grantVeil(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack, CallbackInfo ci) {
        if (entity instanceof ServerPlayer serverPlayer) {
            Advancement sd = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsucraft:mastery_simple_domain"));
            if (sd != null && serverPlayer.getAdvancements().getOrStartProgress(sd).isDone()) {
                Advancement veil = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsufin:veil"));
                if (veil != null) {
                    AdvancementProgress veilProgress = serverPlayer.getAdvancements().getOrStartProgress(veil);
                    if (!veilProgress.isDone()) {
                        veilProgress.getRemainingCriteria().forEach(c -> serverPlayer.getAdvancements().award(veil, c));
                        itemstack.shrink(1);
                        ci.cancel();
                    }
                }
            }
        }
    }
}
