package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.ConvergenceEffectStartedappliedProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ConvergenceEffectStartedappliedProcedure.class)
public class MixinConvergence {
    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 10.0), remap = false)
    private static double allowAny(double constant, LevelAccessor world, double x, double y, double z, Entity entity) {
        return entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique;
    }
}
