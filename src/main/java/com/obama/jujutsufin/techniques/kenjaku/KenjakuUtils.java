package com.obama.jujutsufin.techniques.kenjaku;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;

public class KenjakuUtils {

    public static void moveTechnique(Player player) {
        double Technique2 = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2;
        ListTag KenjakuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).KenjakuCopies;
        if (KenjakuCopies.isEmpty()) KenjakuCopies.add(IntTag.valueOf(102));
        int index = KenjakuCopies.indexOf(IntTag.valueOf((int) Technique2));
        index += (player.isShiftKeyDown() ? -1 : 1);
        if (index >= KenjakuCopies.size()) index = 0;
        if (index <= -1) index = KenjakuCopies.size() - 1;
        int value = index;
        player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent((cap) -> {
            cap.PlayerCurseTechnique2 = (KenjakuCopies.get(value) instanceof IntTag it ? it.getAsDouble() : cap.PlayerCurseTechnique2);
            cap.syncPlayerVariables(player);
        });
    }

    public static void moveGUI(Player player, int type) {
        double Technique2 = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2;
        ListTag KenjakuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).KenjakuCopies;
        if (KenjakuCopies.isEmpty()) KenjakuCopies.add(IntTag.valueOf(102));
        int index = KenjakuCopies.indexOf(IntTag.valueOf((int) Technique2));
        index += type;
        if (index >= KenjakuCopies.size()) index = 0;
        if (index <= -1) index = KenjakuCopies.size() - 1;
        int value = index;
        player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent((cap) -> {
            cap.PlayerCurseTechnique2 = (KenjakuCopies.get(value) instanceof IntTag it ? it.getAsDouble() : cap.PlayerCurseTechnique2);
            cap.syncPlayerVariables(player);
        });
    }

    public static String getCurrentTechnique2(Player player) {
        double Technique2 = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2;
        return "" + Technique2;
    }

    public static void deleteTechnique(Player player) {
        double Technique2 = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2;
        ListTag KenjakuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).KenjakuCopies;
        if (KenjakuCopies.isEmpty()) KenjakuCopies.add(IntTag.valueOf(102));
        int index = KenjakuCopies.indexOf(IntTag.valueOf((int) Technique2));
        if (index == -1) return;
        KenjakuCopies.remove(index);
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            cap.KenjakuCopies = KenjakuCopies;
            cap.syncPlayerCaps(player);
        });
        moveTechnique(player);
    }

    public static void addTechnique(Player player, int technique) {
        ListTag KenjakuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).KenjakuCopies;
        if (KenjakuCopies.contains(IntTag.valueOf(technique))) return;
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            cap.KenjakuCopies.add(IntTag.valueOf(technique));
            cap.syncPlayerCaps(player);
        });
    }

    public static void removeTechnique(Player player, int number) {
        ListTag KenjakuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).KenjakuCopies;
        if (KenjakuCopies.isEmpty()) KenjakuCopies.add(IntTag.valueOf(102));
        if (number < 0 || number >= KenjakuCopies.size()) return;
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            cap.KenjakuCopies.remove(number);
            cap.syncPlayerCaps(player);
        });
    }
}
