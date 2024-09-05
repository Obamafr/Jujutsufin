package com.obama.jujutsufin.techniques.itadori;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.techniques.Technique;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

public class Itadori extends Technique {
    private static final MobEffect DEATHPAINTINGBLOOD = JujutsucraftModMobEffects.DEATH_PAINTING_BLOOD.get();

    public static boolean execute(Player player, int selected){
        boolean found = false;
        int CurseWombs = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).EatenWombs;
        switch (selected) {
            case 5: {
                if (CurseWombs >= 2) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso1").getString(), 120, false, false);
                    break;
                }
            }
            case 6: {
                if (CurseWombs >= 5) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso2").getString(), 25, true, false);
                    break;
                }
            }
            case 7: {
                if (CurseWombs >= 1) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso3").getString(), 200, false, false);
                    break;
                }
            }
            case 8: {
                if (CurseWombs >= 5) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso4").getString(), 100, false, false);
                    break;
                }
            }
            case 9: {
                if (CurseWombs >= 7) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso5").getString(), (player.hasEffect(DEATHPAINTINGBLOOD)? 100 : 0), true, false);
                    break;
                }
            }
            case 16: {
                if (CurseWombs >= 3) {
                    found = setInfo(player, selected, Component.translatable("jujutsu.technique.choso6").getString(), 400, false, false);
                    break;
                }
            }
            case 18: {
                if (CurseWombs >= 4) {
                    found = setInfo(player, selected, Component.translatable("item.jujutsucraft.wing_king_chestplate").getString(), 150, false, false);
                    break;
                }
            }
            case 19: {
                if (player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).flag_sukuna) {
                    found = setInfo(player, selected, Component.translatable("effect.sukuna_effect").getString(), 0, true, true);
                    break;
                }
            }
            case 20: {
                if (canDomain(player) && CurseWombs >= 9) {
                    found = setInfo(player, selected, Component.translatable("effect.domain_expansion").getString(), 1250, false, false);
                    break;
                }
            }
            default: {
                found = switchDefault(player, selected);
                break;
            }
        }
        return found;
    }
}
