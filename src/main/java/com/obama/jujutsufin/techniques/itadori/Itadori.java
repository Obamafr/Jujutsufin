package com.obama.jujutsufin.techniques.itadori;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.techniques.Technique;
import net.mcreator.jujutsucraft.entity.BloodBallEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class Itadori extends Technique {
    public static boolean execute(Player player, int selected){
        boolean found = false;
        int CurseWombs = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).EatenWombs;
        if (player.hasEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get())) return switchDefault(player, selected);
        switch (selected) {
            case 5: {
                if (CurseWombs >= 2) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso1").getString(), 120, false, false);
                }
                break;
            }
            case 6: {
                if (CurseWombs >= 5) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso2").getString(), 25, true, false);
                }
                break;
            }
            case 7: {
                if (CurseWombs >= 1) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso3").getString(), 200, false, false);
                }
                break;
            }
            case 8: {
                if (CurseWombs >= 5 && isBloodBall(player)) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso4").getString(), 100, false, false);
                }
                break;
            }
            case 9: {
                if (CurseWombs >= 7) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso5").getString(), (player.hasEffect(JujutsucraftModMobEffects.DEATH_PAINTING_BLOOD.get()) ? 0 : 100), true, false);
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
                if (player instanceof ServerPlayer serverPlayer && serverPlayer.getAdvancements().getOrStartProgress(serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsucraft:sukuna_finger_1"))).isDone()) {
                    found = setInfo(player, selected, Component.translatable("effect.sukuna_effect").getString(), 0, true, true);
                }
                break;
            }
            case 20: {
                if (canDomain(player) && CurseWombs >= 9 && !player.hasEffect(DOMAINEXPANSIONEFT)) {
                    found = setInfo(player, selected, Component.translatable("effect.domain_expansion").getString(), 1250, false, false);
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
