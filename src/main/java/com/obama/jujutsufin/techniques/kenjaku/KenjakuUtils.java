package com.obama.jujutsufin.techniques.kenjaku;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

import static com.obama.jujutsufin.techniques.veils.VeilsUtils.CTNames;
import static com.obama.jujutsufin.techniques.veils.VeilsUtils.CustomNames;

public class KenjakuUtils {
    public static void rightClickPlayer(Player player, Player target) {
        double playerCurseTechnique = target.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique;
        if (playerCurseTechnique != -1 && playerCurseTechnique != 21 && addTechnique(player, (int) playerCurseTechnique)) {
            target.kill();
        }
    }

    public static void rightClickEntity(Player player, LivingEntity entity) {
        final String[] NameIDs = {"jujutsucraft:cursed_spirit_grade_05", "jujutsucraft:gojo_satoru", "jujutsucraft:gojo_satoru_school_days", "jujutsucraft:fushiguro_megumi", "jujutsucraft:fushiguro_megumi_shibuya", "jujutsucraft:inumaki_toge", "jujutsucraft:okkotsu_yuta", "jujutsucraft:okkotsu_yuta_culling_game", "jujutsucraft:hakari_kinji", "jujutsucraft:todo_aoi", "jujutsucraft:zenin_naoya", "jujutsucraft:zenin_naoya_cursed_spirit_3", "jujutsucraft:zenin_jinichi", "jujutsucraft:zenin_ogi", "jujutsucraft:nanami_kento", "jujutsucraft:mei_mei", "jujutsucraft:tsukumo_yuki", "jujutsucraft:higuruma_hiromi", "jujutsucraft:ishigori_ryu", "jujutsucraft:kashimo_hajime", "jujutsucraft:kurusu_hana", "jujutsucraft:sukuna", "jujutsucraft:sukuna_fushiguro", "jujutsucraft:sukuna_perfect", "jujutsucraft:uraume", "jujutsucraft:dagon_2", "jujutsucraft:hanami", "jujutsucraft:mahito", "jujutsucraft:jogo", "jujutsucraft:choso", "jujutsucraft:cursed_spirit_grade_04", "jujutsucraft:eight_handled_swrod_divergent_sila_divine_general_mahoraga", "jujutsucraft:takaba_fumihiko", "jujutsucraft:miguel_dancer", "jujutsucraft:miguel", "jujutsucraft:kusakabe_yatsuya", "jujutsucraft:zenin_chojuro", "jujutsucraft:yaga_masamichi", "jujutsucraft:nobara_kugisaki", "jujutsucraft:yoshino_junpei", "jujutsucraft:nishimiya_momo", "jujutsucraft:dhruv_lakdawalla", "jujutsucraft:uro_takako", "jujutsucraft:yorozu", "jujutsucraft:geto_suguru", "jujutsucraft:geto_suguru_curse_user", "jujutsucraft:kenjaku", "jujutsucraft:ino_takuma"};
        final int[] IDIndex = {25, 2, 2, 6, 6, 3, 5, 5, 29, 20, 19, 19, 22, 26, 13, 11, 9, 27, 12, 7, 28, 1, 1, 1, 24, 8, 14, 15, 4, 10, 23, 16, 17, 30, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 18, 18, 18, 40};
        String entityID = String.valueOf(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
        for (int i = 0; i < NameIDs.length; i++) {
            if (entityID.equals(NameIDs[i])) {
                if (addTechnique(player, IDIndex[i])) {
                    entity.discard();
                    break;
                }
            }
        }
    }

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
        player.displayClientMessage(Component.literal(getCurrentTechnique2(player)), true);
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
        int Technique2 = (int) player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2;
        if (Technique2 >= 100 && Technique2 - 100 < CustomNames.length) {
            Technique2 -= 100;
            return CustomNames[Technique2] + " - " + (Technique2 + 100);
        }
        Technique2++;
        if (Technique2 < 0 || Technique2 >= CTNames.length) return "Error - " + (Technique2 - 1);
        return CTNames[Technique2] + " - " + (Technique2 - 1);
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

    public static boolean addTechnique(Player player, int technique) {
        ListTag KenjakuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).KenjakuCopies;
        if (player.level().getLevelData().getGameRules().getInt(JujutsufinGameRules.KenjakuLimit) <= KenjakuCopies.size()) return false;
        if (KenjakuCopies.contains(IntTag.valueOf(technique))) return false;
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            cap.KenjakuCopies.add(IntTag.valueOf(technique));
            cap.syncPlayerCaps(player);
        });
        return true;
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
