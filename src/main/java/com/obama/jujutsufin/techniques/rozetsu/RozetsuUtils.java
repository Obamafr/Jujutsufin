package com.obama.jujutsufin.techniques.rozetsu;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.entity.Shikigami;
import com.obama.jujutsufin.techniques.Skill;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyChangeTechniqueOnKeyPressedProcedure;
import net.mcreator.jujutsucraft.procedures.OtherDomainExpansionProcedure;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static com.obama.jujutsufin.techniques.veils.VeilsUtils.CTNames;
import static com.obama.jujutsufin.techniques.veils.VeilsUtils.CustomNames;

public class RozetsuUtils extends Skill {

    public static void pickUpShikigami(Player player, Shikigami shikigami) {
        if (shikigami.getPersistentData().getString("OWNER_UUID").equals(player.getStringUUID())) {
            LazyOptional<JujutsucraftModVariables.PlayerVariables> capability = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY);
            float difference = shikigami.getMaxHealth() - shikigami.getHealth();
            if (difference * 2.5 <= capability.orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower) {
                capability.ifPresent(cap -> {
                    cap.PlayerCursePowerChange -= difference * 2.5;
                    cap.syncPlayerVariables(player);
                });
                addTechnique(player, shikigami.getPersistentData().getInt("rozTechnique"));
                shikigami.discard();
            } else {
                player.displayClientMessage(Component.literal("Not Enough Energy"), true);
            }
        }
    }

    public static void playerKillPlayer(Player player, Player target) {
        double playerCurseTechnique = target.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique;
        if (playerCurseTechnique != -1) addTechnique(player, (int) playerCurseTechnique);
    }

    public static void playerKillEntity(Player player, LivingEntity entity) {
        final String[] NameIDs = {"jujutsucraft:cursed_spirit_grade_05", "jujutsucraft:gojo_satoru", "jujutsucraft:gojo_satoru_school_days", "jujutsucraft:fushiguro_megumi", "jujutsucraft:fushiguro_megumi_shibuya", "jujutsucraft:inumaki_toge", "jujutsucraft:okkotsu_yuta", "jujutsucraft:okkotsu_yuta_culling_game", "jujutsucraft:hakari_kinji", "jujutsucraft:todo_aoi", "jujutsucraft:zenin_naoya", "jujutsucraft:zenin_naoya_cursed_spirit_3", "jujutsucraft:zenin_jinichi", "jujutsucraft:zenin_ogi", "jujutsucraft:nanami_kento", "jujutsucraft:mei_mei", "jujutsucraft:tsukumo_yuki", "jujutsucraft:higuruma_hiromi", "jujutsucraft:ishigori_ryu", "jujutsucraft:kashimo_hajime", "jujutsucraft:kurusu_hana", "jujutsucraft:sukuna", "jujutsucraft:sukuna_fushiguro", "jujutsucraft:sukuna_perfect", "jujutsucraft:uraume", "jujutsucraft:dagon_2", "jujutsucraft:hanami", "jujutsucraft:mahito", "jujutsucraft:jogo", "jujutsucraft:choso", "jujutsucraft:cursed_spirit_grade_04", "jujutsucraft:eight_handled_swrod_divergent_sila_divine_general_mahoraga", "jujutsucraft:takaba_fumihiko", "jujutsucraft:miguel_dancer", "jujutsucraft:miguel", "jujutsucraft:kusakabe_yatsuya", "jujutsucraft:zenin_chojuro", "jujutsucraft:yaga_masamichi", "jujutsucraft:nobara_kugisaki", "jujutsucraft:yoshino_junpei", "jujutsucraft:nishimiya_momo", "jujutsucraft:dhruv_lakdawalla", "jujutsucraft:uro_takako", "jujutsucraft:yorozu", "jujutsucraft:geto_suguru", "jujutsucraft:geto_suguru_curse_user", "jujutsucraft:kenjaku", "jujutsucraft:ino_takuma", "jujutsucraft:itadori_yuji", "jujutsucraft:itadori_yuji_shibuya", "jujutsucraft:itadori_yuji_shinjuku"};
        final int[] IDIndex = {25, 2, 2, 6, 6, 3, 5, 5, 29, 20, 19, 19, 22, 26, 13, 11, 9, 27, 12, 7, 28, 1, 1, 1, 24, 8, 14, 15, 4, 10, 23, 16, 17, 30, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 18, 18, 18, 40, 21, 21, 21};
        String entityID = String.valueOf(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
        for (int i = 0; i < NameIDs.length; i++) {
            if (entityID.equals(NameIDs[i])) {
                addTechnique(player, IDIndex[i]);
            }
        }
    }

    public static String getTechName(Player player, int index) {
        int Tech = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RozetsuCopies.getInt(index);
        if (Tech == 0) {
            return "Blank Shikigami";
        }
        if (Tech >= 100 && Tech - 100 < CustomNames.length) {
            Tech -= 100;
            return CustomNames[Tech] + " Shikigami";
        }
        Tech++;
        if (Tech < 0 || Tech >= CTNames.length) return "Error";
        return CTNames[Tech] + " Shikigami";
    }

    public static void addTechnique(Player player, int technique) {
        ListTag RozetsuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RozetsuCopies;
        if (RozetsuCopies.contains(IntTag.valueOf(technique))) return;
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            cap.RozetsuCopies.add(IntTag.valueOf(technique));
            cap.syncPlayerCaps(player);
        });
    }

    public static void removeTechnique(Player player, int number) {
        ListTag RozetsuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RozetsuCopies;
        if (RozetsuCopies.isEmpty()) return;
        if (number < 0 || number >= RozetsuCopies.size()) return;
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            cap.RozetsuCopies.remove(number);
            cap.syncPlayerCaps(player);
        });
    }

    public static boolean spawnShikigami(Player player) {
        ListTag RozetsuCopies = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS).orElse(new JujutsufinPlayerCaps.PlayerCaps()).RozetsuCopies;
        MinecraftServer server = player.getServer();
        if (server == null) return false;
        server.getCommands().performPrefixedCommand(player.createCommandSourceStack().withSuppressedOutput().withPermission(4), "summon jujutsufin:shikigami ~ ~ ~");
        List<Shikigami> list = player.level().getEntitiesOfClass(Shikigami.class, new AABB(player.blockPosition()).inflate(1));
        if (list.isEmpty()) return false;
        for (Shikigami shikigami : list) {
            if (shikigami.getPersistentData().getString("OWNER_UUID").isEmpty()) {
                MobEffectInstance strength = player.getEffect(MobEffects.DAMAGE_BOOST);
                if (strength != null) {
                    shikigami.addEffect(strength);
                }
                shikigami.getPersistentData().putString("OWNER_UUID", player.getStringUUID());
                shikigami.getPersistentData().putBoolean("JujutsuSorcerer", player.getPersistentData().getBoolean("JujutsuSorcerer"));
                shikigami.getPersistentData().putBoolean("CurseUser", player.getPersistentData().getBoolean("CurseUser"));
                shikigami.getPersistentData().putBoolean("CursedSpirit", player.getPersistentData().getBoolean("CursedSpirit"));
                shikigami.getPersistentData().putDouble("friend_num", player.getPersistentData().getDouble("friend_num"));
                AttributeInstance instance = shikigami.getAttributes().getInstance(Attributes.MAX_HEALTH);
                if (instance == null) return false;
                instance.addPermanentModifier(new AttributeModifier(UUID.randomUUID(), "PlayerHealth", player.getMaxHealth() / 10, AttributeModifier.Operation.MULTIPLY_BASE));
                shikigami.setHealth(shikigami.getMaxHealth());
                instance = shikigami.getAttributes().getInstance(Attributes.ARMOR);
                if (instance == null) return false;
                instance.addPermanentModifier(new AttributeModifier(UUID.randomUUID(), "PlayerArmor", player.getArmorValue() - 8, AttributeModifier.Operation.ADDITION));
                shikigami.getPersistentData().putInt("rozTechnique", RozetsuCopies.getInt(player.getPersistentData().getInt("rozIndex")));
            }
        }
        removeTechnique(player, player.getPersistentData().getInt("rozIndex"));
        player.removeEffect(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get());
        KeyChangeTechniqueOnKeyPressedProcedure.execute(player.level(), player.getX(), player.getY(), player.getZ(), player);
        return true;
    }

    public static boolean execute(ServerLevel serverLevel, double x, double y, double z, LivingEntity livingEntity, int skill) {
        boolean found = false;
        skill %= 10100;
        switch (skill) {
            case 5,6,7: {
                if (livingEntity instanceof Player player) found = spawnShikigami(player);
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
}
