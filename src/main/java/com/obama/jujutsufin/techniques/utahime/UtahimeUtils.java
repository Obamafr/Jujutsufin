package com.obama.jujutsufin.techniques.utahime;

import com.obama.jujutsufin.init.JujutsufinEffects;
import com.obama.jujutsufin.techniques.Skill;
import net.mcreator.jujutsucraft.procedures.OtherDomainExpansionProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class UtahimeUtils extends Skill {
    public static boolean execute(ServerLevel serverLevel, double x, double y, double z, LivingEntity livingEntity, int skill) {
        boolean found = false;
        skill %= 10000;
        switch (skill) {
            case 5: {
                found = soloForbiddenArea(livingEntity);
                break;
            }
            case 20: {
                OtherDomainExpansionProcedure.execute(serverLevel, x, y, z, livingEntity);
                found = true;
                break;
            }
        }
        return found;
    }

    public static void utahimeRightClick(Player player, Entity entity) {
        String endingString;
        if (entity.getPersistentData().getString("UtahimeUUID").equals(player.getStringUUID())) {
            entity.getPersistentData().remove("UtahimeUUID");
            endingString = entity.getDisplayName().getString() + " removed";
        } else {
            entity.getPersistentData().putString("UtahimeUUID", player.getStringUUID());
            endingString = entity.getDisplayName().getString() + " added";
        }
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.sendSystemMessage(Component.literal(endingString), false);
        }
    }

    private static boolean soloForbiddenArea(LivingEntity livingEntity) {
        noCharge(livingEntity, 0);
        MobEffect SFA = JujutsufinEffects.SFA.get();
        if (livingEntity.hasEffect(SFA)) {
            livingEntity.removeEffect(SFA);
        } else {
            livingEntity.addEffect(new MobEffectInstance(SFA, -1, 0));
        }
        return true;
    }
}
