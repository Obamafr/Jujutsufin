package com.obama.jujutsufin.techniques.rozetsu;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.techniques.Technique;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Rozetsu extends Technique {
    public static boolean execute(Player player, int selected) {
        boolean found = false;
        switch (selected) {
            case 5: {
                ListTag RozetsuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RozetsuCopies;
                if (!RozetsuCopies.isEmpty()) {
                    found = setInfo(player, selected, Component.literal(RozetsuUtils.getTechName(player, 0)).getString(), 500, false, false);
                }
                player.getPersistentData().putInt("rozIndex", 0);
                break;
            }
            case 6 : {
                int index = player.getPersistentData().getInt("rozIndex");
                index += (player.isShiftKeyDown() ? -1 : 1);
                ListTag RozetsuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RozetsuCopies;
                if (!RozetsuCopies.isEmpty() && index < RozetsuCopies.size() - 1 && RozetsuCopies.size() != 1) {
                    found = setInfo(player, selected, Component.literal(RozetsuUtils.getTechName(player, index)).getString(), 500, false, false);
                }
                break;
            }
            case 7 : {
                ListTag RozetsuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RozetsuCopies;
                if (!RozetsuCopies.isEmpty() && RozetsuCopies.size() != 1) {
                    found = setInfo(player, selected, Component.literal(RozetsuUtils.getTechName(player, RozetsuCopies.size() - 1)).getString(), 500, false, false);
                }
                player.getPersistentData().putInt("rozIndex", RozetsuCopies.size() - 1);
                break;
            }
            case 20: {
                if (canDomain(player) && !player.hasEffect(DOMAINEXPANSIONEFT)) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.rozetsu.20").getString(), 1250, false, false);
                }
                break;
            }
            default : {
                found = switchDefault(player, selected);
                break;
            }
        }
        return found;
    }
}
