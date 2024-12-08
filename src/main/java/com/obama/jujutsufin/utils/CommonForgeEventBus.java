package com.obama.jujutsufin.utils;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinEffects;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import com.obama.jujutsufin.techniques.kenjaku.KenjakuUtils;
import com.obama.jujutsufin.techniques.rozetsu.RozetsuUtils;
import com.obama.jujutsufin.techniques.utahime.UtahimeUtils;
import net.mcreator.jujutsucraft.entity.BlackHoleEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyReverseCursedTechniqueOnKeyPressedProcedure;
import net.mcreator.jujutsucraft.procedures.KeyReverseCursedTechniqueOnKeyReleasedProcedure;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JujutsufinMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEventBus {
    @SubscribeEvent
    public static void entityDies(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.level().getLevelData().getGameRules().getBoolean(JujutsufinGameRules.KenjakuKeepTechniques)) {
            entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                cap.KenjakuCopies = new ListTag();
            });
        }
        Entity source = event.getSource().getEntity();
        if (source instanceof Player sourcePlayer && sourcePlayer.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique == 101) {
            if (entity instanceof Player targetPlayer) {
                RozetsuUtils.playerKillPlayer(sourcePlayer, targetPlayer);
            } else {
                RozetsuUtils.playerKillEntity(sourcePlayer, entity);
            }
        }
    }
    @SubscribeEvent
    public static void PlayerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.getPersistentData().getBoolean("AUTORCT")) {
                if (!player.hasEffect(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get())
                && player.getHealth() < player.getMaxHealth() - 0.5
                && player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower >= 10) {
                    KeyReverseCursedTechniqueOnKeyPressedProcedure.execute(player);
                } else {
                    KeyReverseCursedTechniqueOnKeyReleasedProcedure.execute(player);
                }
        }
    }
    @SubscribeEvent
    public static void PlayerJoinServer(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            Advancement jujutsufin = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsufin:jujutsufin"));
            if (jujutsufin != null) {
                AdvancementProgress jujutsufinProgress = serverPlayer.getAdvancements().getOrStartProgress(jujutsufin);
                if (!jujutsufinProgress.isDone()) {
                    jujutsufinProgress.getRemainingCriteria().forEach(c -> serverPlayer.getAdvancements().award(jujutsufin, c));
                }
            }
        }
    }
    @SubscribeEvent
    public static void EntityTakesDamage(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity source = damageSource.getEntity();
        if (target.hasEffect(MobEffects.LEVITATION) && source instanceof BlackHoleEntity && event.isCancelable()) {
            event.setCanceled(true);
        }
        if (target.hasEffect(JujutsufinEffects.GRAVITY.get())) {
            event.setAmount(event.getAmount()/1.5F);
        }
        if (!damageSource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge:animation")))
                && !damageSource.is(DamageTypes.GENERIC_KILL)
                && Math.random() < 0.005
                && target instanceof ServerPlayer serverPlayer) {
            Advancement rct2 = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsucraft:reverse_cursed_technique_2"));
            if (rct2 != null && serverPlayer.getAdvancements().getOrStartProgress(rct2).isDone()) {
                Advancement rctB = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsufin:burnout"));
                if (rctB != null) {
                    AdvancementProgress rctBProgress = serverPlayer.getAdvancements().getOrStartProgress(rctB);
                    if (!rctBProgress.isDone()) {
                        rctBProgress.getRemainingCriteria().forEach(c -> serverPlayer.getAdvancements().award(rctB, c));
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void PlayerRightClicksEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        JujutsucraftModVariables.PlayerVariables playerVariables = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables());
        JujutsufinPlayerCaps.PlayerCaps playerCaps = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps());
        if (event.getHand() == player.getUsedItemHand()) {
            if (player.isShiftKeyDown()) {
                if (playerVariables.PlayerCurseTechnique2 == 100) {
                    UtahimeUtils.utahimeRightClick(player, target);
                }
                if (playerCaps.CustomCT == 1 && target instanceof LivingEntity livingTarget && livingTarget.getHealth() < livingTarget.getMaxHealth() / 8) {
                    if (livingTarget instanceof Player playerTarget) {
                        KenjakuUtils.rightClickPlayer(player, playerTarget);
                    } else {
                        KenjakuUtils.rightClickEntity(player, livingTarget);
                    }
                }
            }
        }
    }
}
