package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.DeathPaintingBloodOnEffectActiveTickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DeathPaintingBloodOnEffectActiveTickProcedure.class)
public class MixinDPBEffectTick {
    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 10.0, ordinal = 2), remap = false)
    private static double allowYuji(double constant, LevelAccessor world, Entity entity) {
        double technique = entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique;
        return (technique == 21 ? technique : constant);
    }
}
