package com.obama.jujutsufin.techniques.itadori;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import com.obama.jujutsufin.techniques.Technique;
import net.mcreator.jujutsucraft.entity.BloodBallEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class Itadori extends Technique {
    public static boolean execute(Player player, int selected){
        boolean found = false;
        int CurseWombs = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).EatenWombs;
        if (!player.level().getLevelData().getGameRules().getBoolean(JujutsufinGameRules.YujiBloodManipulation)) return false;
        if (player.hasEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get())) return false;
        if (player.hasEffect(JujutsucraftModMobEffects.DEATH_PAINTING_BLOOD.get())) {
            if (selected == 10 && CurseWombs >= 7) {
                return setInfo(player, selected, Component.translatable("jujutsu.technique.choso5").getString(),0, true, false);
            } else {
                return switchDefault(player, selected);
            }
        }
        switch (selected) {
            case 5: {
                found = setInfo(player, selected, Component.translatable("jujutsu.technique.itadori1").getString(), 50, false, false);
                break;
            }
            case 6: {
                if (CurseWombs >= 2) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso1").getString(), 120, false, false);
                }
                break;
            }
            case 7: {
                if (CurseWombs >= 5) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso2").getString(), 25, true, false);
                }
                break;
            }
            case 8: {
                if (CurseWombs >= 1) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso3").getString(), 200, false, false);
                }
                break;
            }
            case 9: {
                if (CurseWombs >= 5 && isBloodBall(player)) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso4").getString(), 100, false, false);
                }
                break;
            }
            case 10: {
                if (CurseWombs >= 7) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso5").getString(),100, true, false);
                }
                break;
            }
            case 16: {
                if (CurseWombs >= 3) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso6").getString(), 400, false, false);
                }
                break;
            }
            case 18: {
                if (CurseWombs >= 4) {
                    found = setInfo(player, selected, Component.translatable("item.jujutsucraft.wing_king_chestplate").getString(), 150, false, false);
                }
                break;
            }
            case 19: {
                if (player instanceof ServerPlayer serverPlayer) {
                    Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsucraft:sukuna_finger_1"));
                    if (advancement != null && serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone()) {
                        found = setInfo(player, selected, Component.translatable("effect.sukuna_effect").getString(), 0, true, true);
                    }
                }
                break;
            }
            case 20: {
                if (canDomain(player) && !player.hasEffect(DOMAINEXPANSIONEFT)) {
                    found = setInfo(player, selected, Component.translatable("effect.domain_expansion").getString() + " Itadori", 1000, false, false);
                }
                break;
            }
            case 22: {
                if (canDomain(player) && CurseWombs >= 9 && !player.hasEffect(DOMAINEXPANSIONEFT)) {
                    found = setInfo(player, selected, Component.translatable("effect.domain_expansion").getString() + " Blood Manipulation", 1250, false, false);
                }
                break;
            }
            default: {
                found = switchDefault(player, selected);
                break;
            }
        }
        return found;
    }

    private static boolean isBloodBall(Player player) {
        return !player.level().getEntitiesOfClass(BloodBallEntity.class, new AABB(player.blockPosition()).inflate(4), p -> p.getPersistentData().getString("OWNER_UUID").equals(player.getStringUUID())).isEmpty();
    }
}
