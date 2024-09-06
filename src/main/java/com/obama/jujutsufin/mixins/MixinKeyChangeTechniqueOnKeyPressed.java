package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.techniques.itadori.Itadori;
import com.obama.jujutsufin.techniques.kaori.Kaori;
import com.obama.jujutsufin.techniques.utahime.Utahime;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyChangeTechniqueOnKeyPressedProcedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyChangeTechniqueOnKeyPressedProcedure.class)
public abstract class MixinKeyChangeTechniqueOnKeyPressed {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (entity == null) return;
        boolean found = false;
        if (entity instanceof Player player) {
            JujutsucraftModVariables.PlayerVariables playerVariables = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(new JujutsucraftModVariables.PlayerVariables());
            int selected = (int)playerVariables.PlayerSelectCurseTechnique;
            int Technique = (int)playerVariables.PlayerCurseTechnique;
            if (!playerVariables.noChangeTechnique) {
                selected += (player.isShiftKeyDown() ? -1 : 1);
            }
            for (int i = 0; i < 52; i++) {
                if (selected == (player.isShiftKeyDown() ? -1 : 50)) {
                    selected = (player.isShiftKeyDown() ? 50 : 0);
                }
                switch (Technique) {
                    case 100: {
                        found = Utahime.execute(player, selected);
                        break;
                    }
                    case 102: {
                        found = Kaori.execute(player, selected);
                        break;
                    }
                    case 21: {
                        found = Itadori.execute(player, selected);
                        break;
                    }
                    default: {
                        return;
                    }
                }
                if (found) {
                    break;
                } else {
                    selected += (entity.isShiftKeyDown() ? -1 : 1);
                }
            }
            if (found) {
                player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                    MobEffect STARRAGE = JujutsucraftModMobEffects.STAR_RAGE.get();
                    MobEffect SIXEYES = JujutsucraftModMobEffects.SIX_EYES.get();
                    MobEffect SUKUNA = JujutsucraftModMobEffects.SUKUNA_EFFECT.get();
                    MobEffect DOMAIN = JujutsucraftModMobEffects.DOMAIN_EXPANSION.get();
                    double cost = cap.PlayerSelectCurseTechniqueCost;
                    if (player.hasEffect(STARRAGE) && cap.PhysicalAttack && !player.hasEffect(DOMAIN)) {
                        cost += 10;
                        cost += 9 * (player.getEffect(STARRAGE).getAmplifier() + 1);
                    }
                    if (player.hasEffect(SUKUNA)) {
                        cost *= 0.5;
                    }
                    if (player.hasEffect(SIXEYES)) {
                        cost *= Math.pow(0.1, (player.getEffect(SIXEYES).getAmplifier() + 1));
                    }
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == JujutsucraftModItems.LOUDSPEAKER.get()) {
                        cost = 0;
                    }
                    cap.PlayerSelectCurseTechniqueCost = cost;
                    cap.syncPlayerVariables(player);
                });
                ci.cancel();
            }
        }
    }
}
