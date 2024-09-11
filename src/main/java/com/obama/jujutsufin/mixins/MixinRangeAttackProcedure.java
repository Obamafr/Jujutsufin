package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.procedures.RangeAttackProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RangeAttackProcedure.class)
public class MixinRangeAttackProcedure {
    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 0.998), remap = false)
    private static double blackFlashChance(double constant, LevelAccessor world, double x, double y, double z, Entity entity){
        double playerChance = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).BlackFlashChance;
        double gameRuleChance = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.BlackFlashChance);
        return (playerChance != 998 ? playerChance/1000 : gameRuleChance/1000);
    }
}
