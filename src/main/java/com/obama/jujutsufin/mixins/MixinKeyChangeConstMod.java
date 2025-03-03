package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.procedures.TechniqueDecideProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = TechniqueDecideProcedure.class, priority = 1001)
public class MixinKeyChangeConstMod {
    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 0.1), remap = false)
    private static double setSixEyes(double constant, Entity entity){
        double playerMultiplier = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).SixEyesMultiplier;
        double gameRuleMultiplier = entity.level().getLevelData().getGameRules().getInt(JujutsufinGameRules.SixEyesMultiplier);
        return (playerMultiplier != 1 ? playerMultiplier/10 : gameRuleMultiplier/10);
    }

    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 0.5), remap = false)
    private static double setSukuna(double constant, Entity entity){
        double playerMultiplier = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).SukunaMultiplier;
        double gameRuleMultiplier = entity.level().getLevelData().getGameRules().getInt(JujutsufinGameRules.SukunaMultiplier);
        return (playerMultiplier != 5 ? playerMultiplier/10 : gameRuleMultiplier/10);
    }
}
