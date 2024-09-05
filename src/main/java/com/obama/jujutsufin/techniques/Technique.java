package com.obama.jujutsufin.techniques;

import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

public class Technique {
    public static final MobEffect DOMAINEXPANSIONEFT = JujutsucraftModMobEffects.DOMAIN_EXPANSION.get();
    public static final ResourceLocation REVERSECURSETECHNIQUE1 = new ResourceLocation("jujutsucraft:reverse_cursed_technique_1");
    public static final ResourceLocation DOMAINEXPANSIONADV = new ResourceLocation("jujutsucraft:mastery_domain_expansion");

    public static boolean setInfo(Player player, int selected, String name, double cost, boolean passive, boolean physical){
        player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
            cap.PlayerSelectCurseTechnique = selected;
            cap.PlayerSelectCurseTechniqueName = name;
            cap.PlayerSelectCurseTechniqueCost = cost;
            cap.PlayerSelectCurseTechniqueCostOrgin = cost;
            cap.PassiveTechnique = passive;
            cap.PhysicalAttack = physical;
            cap.noChangeTechnique = false;
            cap.syncPlayerVariables(player);
        });
        return true;
    }

    public static boolean switchDefault(Player player, int selected) {
        boolean found = false;
        if (selected >= 0 && selected <= 2) {
            found = setInfo(player, selected, Component.translatable("jujutsu.technique.attack" + Math.round(selected + 1.0)).getString(), 0, false, true);
        } else if (selected == 21 && player.hasEffect(DOMAINEXPANSIONEFT)) {
            found = setInfo(player, selected, Component.translatable("jujutsu.technique.cancel_domain").getString(), 0, false, true);
        }
        return found;
    }

    public static boolean ignoreRequirements(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            return serverPlayer.isCreative();
        }
        return false;
    }

    public static boolean canDomain(Player player) {
        if (ignoreRequirements(player)) return true;
        return (player instanceof ServerPlayer serverPlayer && serverPlayer.getAdvancements().getOrStartProgress(serverPlayer.server.getAdvancements().getAdvancement(DOMAINEXPANSIONADV)).isDone());
    }

    public static boolean canRCT(Player player) {
        if (ignoreRequirements(player)) return true;
        return (player instanceof ServerPlayer serverPlayer && serverPlayer.getAdvancements().getOrStartProgress(serverPlayer.server.getAdvancements().getAdvancement(REVERSECURSETECHNIQUE1)).isDone());
    }
}
