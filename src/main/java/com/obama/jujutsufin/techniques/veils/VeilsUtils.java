package com.obama.jujutsufin.techniques.veils;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.entity.VeilEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class VeilsUtils {

    public static String[] CTNames = {"Maki", "None", "Sukuna", "Gojo", "Inumaki", "Jogo", "Okkotsu", "Megumi", "Kashimo", "Dagon", "Tsukumo", "Choso", "Mei Mei", "Ishigori", "Nanami", "Hanami", "Mahito", "Mahoraga", "Takaba", "Geto", "Naoya", "Todo", "Itadori", "Jinichi", "Kurourushi", "Uraume", "Small Pox Deity", "Ogi", "Higuruma", "Angel", "Hakari", "Miguel", "Kusukabe", "Chojuro", "Yaga", "Nobara", "Junpei", "Nishimiya", "Lakdawalla", "Uro", "Yorozu", "Ino", "Kaori", "None", "Rozetsu"};

    public static String[] CustomNames = {"Utahime", "Rozetsu", "Kaori"};

    public static void veilP4(Player player, int i) {
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            int P4 = cap.Veils.getInt("P4") + i;
            if (P4 >= CTNames.length - 1 && P4 < 99) {
                P4 = 100;
            } else if (P4 < 0) {
                P4 = 102;
            } else if (P4 == 99) {
                P4 = CTNames.length - 2;
            } else if (P4 == CustomNames.length + 100) {
                P4 = 0;
            }
            cap.Veils.putInt("P4", P4);
            cap.syncPlayerCaps(player);
        });
    }

    public static boolean chargeVeil(ServerLevel serverLevel, LivingEntity livingEntity) {
        boolean spawn = true;
        VeilEntity veil = null;
        for (Entity entity : serverLevel.getAllEntities()) {
            if (entity instanceof VeilEntity e && entity.getPersistentData().getString("veilOwner").equals(livingEntity.getStringUUID())) {
                spawn = false;
                veil = e;
            }
        }
        CompoundTag tag = livingEntity.getPersistentData();
        if (!tag.getBoolean("PRESS_Z")) {
            if (livingEntity instanceof Player player) {
                player.displayClientMessage(Component.empty(), true);
            }
            livingEntity.removeEffect(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get());
            return true;
        }
        tag.putInt("cnt_v", tag.getInt("cnt_v") + 1);
        int power = (int) ((20 - tag.getInt("cnt_v")) * 0.25);
        StringBuilder string = new StringBuilder((spawn) ? Component.translatable("jujutsufin.veils.spawn").getString() : Component.translatable("jujutsufin.veils.despawn").getString());
        for(int i = 0; i < power; ++i) {
            string.insert(0, "■");
            string.append("■");
        }
        if (livingEntity instanceof Player player) {
            player.displayClientMessage(Component.literal(string.toString()), true);
        }
        if (tag.getInt("cnt_v") == 20) {
            tag.putBoolean("PRESS_Z", false);
            if (spawn) {
                spawnVeil(serverLevel, livingEntity);
            } else {
                veil.getPersistentData().putBoolean("break", true);
            }
        }
        return true;
    }

    private static void spawnVeil(ServerLevel serverLevel, LivingEntity livingEntity) {
        serverLevel.getServer().getCommands().performPrefixedCommand(livingEntity.createCommandSourceStack().withSuppressedOutput().withPermission(4), "summon jujutsufin:veil ~ ~ ~");
        List<VeilEntity> list = serverLevel.getEntitiesOfClass(VeilEntity.class, new AABB(livingEntity.blockPosition()).inflate(1));
        if (list.isEmpty()) return;
        for (VeilEntity veil : list) {
            if (veil.getPersistentData().getString("veilOwner").isEmpty()) {
                veil.getPersistentData().putString("veilOwner", livingEntity.getStringUUID());
                veil.getPersistentData().put("Veils", livingEntity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).Veils);
                veil.getPersistentData().putDouble("friend_num", livingEntity.getPersistentData().getDouble("friend_num"));
                break;
            }
        }
    }
}
