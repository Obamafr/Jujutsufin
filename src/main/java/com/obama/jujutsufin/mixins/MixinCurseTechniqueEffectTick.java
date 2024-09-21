package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.techniques.itadori.ItadoriUtils;
import com.obama.jujutsufin.techniques.kaori.KaoriUtils;
import com.obama.jujutsufin.techniques.utahime.UtahimeUtils;
import com.obama.jujutsufin.techniques.veils.VeilsUtils;
import net.mcreator.jujutsucraft.procedures.CursedTechniqueOnPotionActiveTickProcedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CursedTechniqueOnPotionActiveTickProcedure.class)
public class MixinCurseTechniqueEffectTick {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity && world instanceof ServerLevel serverLevel) {
            boolean found = false;
            int skill = (int)livingEntity.getPersistentData().getDouble("skill");
            if (skill == 50000) {
                found = VeilsUtils.chargeVeil(serverLevel, livingEntity);
            }
            if (skill >= 10000 && skill < 10100) {
                found = UtahimeUtils.execute(serverLevel, x, y, z, livingEntity, skill);
            } else if (skill >= 10200 && skill < 10300) {
                found = KaoriUtils.execute(serverLevel, x, y, z, livingEntity, skill);
            } else if (skill >= 2100 && skill < 2200) {
                found = ItadoriUtils.execute(serverLevel, x, y, z, livingEntity, skill);
            }
            if (found) {
                ci.cancel();
            }
        }
    }
}
