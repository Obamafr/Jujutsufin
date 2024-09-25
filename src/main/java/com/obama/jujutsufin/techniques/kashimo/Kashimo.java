package com.obama.jujutsufin.techniques.kashimo;

import com.obama.jujutsufin.init.JujutsufinGameRules;
import com.obama.jujutsufin.techniques.Technique;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

public class Kashimo extends Technique {
    public static boolean execute(Player player, int selected) {
        boolean found = false;
        MobEffect MBA = JujutsucraftModMobEffects.MYTHICAL_BEAST_AMBER_EFFECT.get();
        switch (selected) {
            case 5: {
                found = setInfo(player, selected, Component.translatable("jujutsu.technique.kashimo1").getString(), 200, true, true);
                break;
            }
            case 10: {
                if (!player.hasEffect(JujutsucraftModMobEffects.ZONE.get())) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.kashimo2").getString(), 100, true, true);
                }
                break;
            }
            case 15: {
                if (!player.hasEffect(MBA)) {
                    found = setInfo(player, selected, Component.translatable("effect.mythical_beast_amber_effect").getString(), 0, true, false);
                }
                break;
            }
            case 16: {
                if (player.hasEffect(MBA)) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.kashimo_ah").getString(), 100, false, false);
                }
                break;
            }
            case 17: {
                if (player.hasEffect(MBA)) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.kashimo_energy_wave").getString(), 250, false, false);
                }
                break;
            }
            case 20: {
                if (canDomain(player) && !player.hasEffect(DOMAINEXPANSIONEFT) && player.level().getLevelData().getGameRules().getBoolean(JujutsufinGameRules.KashimoDomain)) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.kashimo_domain").getString(), 1250, false, false);
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
}
