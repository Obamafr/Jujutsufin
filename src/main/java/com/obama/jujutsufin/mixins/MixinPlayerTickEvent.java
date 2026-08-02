package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinAttributes;
import com.obama.jujutsufin.init.JujutsufinEffects;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.init.JujutsucraftModAttributes;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.PlayerTickEventProcedure;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTickEventProcedure.class)
public class MixinPlayerTickEvent {
    @ModifyVariable(method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getHealth()F"), name = "healCursePower", remap = false)
    private static double CurseEnergyRegen(double healCursePower, Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            AttributeInstance attributeInstance = livingEntity.getAttribute(JujutsufinAttributes.CURSE_ENERGY_REGEN.get());
            if (attributeInstance != null) {
                if (attributeInstance.getBaseValue() != healCursePower) {
                    attributeInstance.setBaseValue(healCursePower);
                }
                return attributeInstance.getValue();
            }
        }
        return healCursePower;
    }

    @ModifyConstant(method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V", constant = @Constant(doubleValue = 0.1), remap = false)
    private static double setSixEyes(double constant, Event event, LevelAccessor world, double x, double y, double z, Entity entity){
        double playerMultiplier = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).SixEyesMultiplier;
        double gameRuleMultiplier = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.SixEyesMultiplier);
        return (playerMultiplier != 1 ? playerMultiplier/10 : gameRuleMultiplier/10);
    }

    @ModifyConstant(method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V", constant = @Constant(doubleValue = 0.5), remap = false)
    private static double setSukuna(double constant, Event event, LevelAccessor world, double x, double y, double z, Entity entity){
        double playerMultiplier = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).SukunaMultiplier;
        double gameRuleMultiplier = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.SukunaMultiplier);
        return (playerMultiplier != 5 ? playerMultiplier/10 : gameRuleMultiplier/10);
    }
}
