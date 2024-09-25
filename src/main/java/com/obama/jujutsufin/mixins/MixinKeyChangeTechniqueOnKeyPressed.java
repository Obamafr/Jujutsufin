package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import com.obama.jujutsufin.techniques.itadori.Itadori;
import com.obama.jujutsufin.techniques.kaori.Kaori;
import com.obama.jujutsufin.techniques.kashimo.Kashimo;
import com.obama.jujutsufin.techniques.utahime.Utahime;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyChangeTechniqueOnKeyPressedProcedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyChangeTechniqueOnKeyPressedProcedure.class)
public class MixinKeyChangeTechniqueOnKeyPressed {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (entity == null) return;
        boolean found = false;
        if (entity instanceof Player player) {
            JujutsucraftModVariables.PlayerVariables playerVariables = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(new JujutsucraftModVariables.PlayerVariables());
            int selected = (int)playerVariables.PlayerSelectCurseTechnique;
            int Technique = (int)playerVariables.PlayerCurseTechnique;
            int Technique2 = (int)playerVariables.PlayerCurseTechnique2;
            if (!playerVariables.noChangeTechnique) {
                selected += (player.isShiftKeyDown() ? -1 : 1);
            }
            for (int i = 0; i < 52; i++) {
                if (selected == (player.isShiftKeyDown() ? -1 : 50)) {
                    selected = (player.isShiftKeyDown() ? 50 : 0);
                    player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                        if (Technique2 != 21 && Technique != 1) {
                            cap.SecondTechnique = !cap.SecondTechnique;
                        }
                        cap.syncPlayerVariables(player);
                    });
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
                    case 7: {
                        found = Kashimo.execute(player, selected);
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
                    MobEffectInstance STARRAGE = player.getEffect(JujutsucraftModMobEffects.STAR_RAGE.get());
                    MobEffectInstance SIXEYES = player.getEffect(JujutsucraftModMobEffects.SIX_EYES.get());
                    MobEffectInstance SUKUNA = player.getEffect(JujutsucraftModMobEffects.SUKUNA_EFFECT.get());
                    MobEffectInstance DOMAIN = player.getEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get());
                    double cost = cap.PlayerSelectCurseTechniqueCost;
                    double playerMultiplier;
                    double value;
                    if (STARRAGE != null && cap.PhysicalAttack && DOMAIN == null) {
                        cost += 10;
                        cost += 9 * (STARRAGE.getAmplifier() + 1);
                    }
                    if (SUKUNA != null) {
                        playerMultiplier = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).SukunaMultiplier;
                        value = (playerMultiplier != -1 ? playerMultiplier/10 : 0.5);
                        cost *= value;
                    }
                    if (SIXEYES != null) {
                        playerMultiplier = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).SixEyesMultiplier;
                        value = (playerMultiplier != -1 ? playerMultiplier/10 : 0.1);
                        cost *= Math.pow(value, (SIXEYES.getAmplifier() + 1));
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

    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 0.1), remap = false)
    private static double setSixEyes(double constant, LevelAccessor world, double x, double y, double z, Entity entity){
        double playerMultiplier = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).SixEyesMultiplier;
        double gameRuleMultiplier = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.SixEyesMultiplier);
        return (playerMultiplier != 1 ? playerMultiplier/10 : gameRuleMultiplier/10);
    }

    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 0.5), remap = false)
    private static double setSukuna(double constant, LevelAccessor world, double x, double y, double z, Entity entity){
        double playerMultiplier = entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).SukunaMultiplier;
        double gameRuleMultiplier = world.getLevelData().getGameRules().getInt(JujutsufinGameRules.SukunaMultiplier);
        return (playerMultiplier != 5 ? playerMultiplier/10 : gameRuleMultiplier/10);
    }
}
