package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.procedures.ReverseCursedTechniqueOnEffectActiveTickProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = ReverseCursedTechniqueOnEffectActiveTickProcedure.class, priority = 1001)
public class MixinReverseCurseTechnique {
    @ModifyConstant(method = "execute", constant = @Constant(intValue = 20), remap = false)
    private static int changeFatigue(int constant, LevelAccessor world, double x, double y, double z, Entity entity) {
        int playerFatigue = (int)entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).FatigueRate;
        int gameRuleFatigue = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.FatigueRate);
        return (playerFatigue != 20 ? playerFatigue : gameRuleFatigue);
    }

    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 10), remap = false)
    private static double changeCostN(double constant, LevelAccessor world, double x, double y, double z, Entity entity) {
        double playerCost = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RCTCost;
        double gameRuleCost = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.RCTCost);
        return (playerCost != 10 ? playerCost : gameRuleCost);
    }

    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 5), remap = false)
    private static double changeCostC(double constant, LevelAccessor world, double x, double y, double z, Entity entity) {
        double playerCost = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RCTCost;
        double gameRuleCost = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.RCTCost);
        return (playerCost != 10 ? playerCost : gameRuleCost) / 2;
    }
}
