package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.LogicAttackProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LogicAttackProcedure.class, priority = 1001)
public abstract class MixinLogicAttackProcedure {
    @ModifyVariable(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getPersistentData()Lnet/minecraft/nbt/CompoundTag;", ordinal = 30), ordinal = 0, remap = false)
    private static boolean onExecute(boolean logic, LevelAccessor world, Entity entity) {
        return world.getLevelData().getGameRules().getBoolean(JujutsufinGameRules.SukunaPVP) && entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).flag_sukuna || logic;
    }
}
