package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.procedures.ReverseCursedTechniqueOnEffectActiveTickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ReverseCursedTechniqueOnEffectActiveTickProcedure.class)
public class MixinReverseCurseTechnique {
    @ModifyConstant(method = "execute", constant = @Constant(intValue = 20), remap = false)
    private static int changeFatigue(int constant, LevelAccessor world, double x, double y, double z, Entity entity) {
        int playerFatigue = (int)entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).FatigueRate;
        int gameRuleFatigue = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.FatigueRate);
        return (playerFatigue != 20 ? playerFatigue : gameRuleFatigue);
    }


    @ModifyVariable(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getCapability(Lnet/minecraftforge/common/capabilities/Capability;Lnet/minecraft/core/Direction;)Lnet/minecraftforge/common/util/LazyOptional;", ordinal = 0), ordinal = 3, remap = false)
    private static double changeCost(double amount, LevelAccessor world, double x, double y, double z, Entity entity) {
        boolean CursedSpirit = entity.getPersistentData().getBoolean("CursedSpirit");
        double fatigue = (CursedSpirit ? 5 : 10) / amount;
        double playerCost = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RCTCost;
        double gameRuleCost = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.RCTCost);
        double newAmount = (playerCost != 10 ? playerCost : gameRuleCost);
        return (CursedSpirit ? newAmount/2 : newAmount)/fatigue;
    }
}
