package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.techniques.itadori.Itadori;
import com.obama.jujutsufin.techniques.kaori.Kaori;
import com.obama.jujutsufin.techniques.utahime.Utahime;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyChangeTechniqueOnKeyPressed2Procedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyChangeTechniqueOnKeyPressed2Procedure.class)
public abstract class MixinKeyChangeTechniqueOnKeyPressed2 {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (entity == null) return;
        boolean found = false;
        if (entity instanceof Player player) {
            int selected = (int)player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerSelectCurseTechnique;
            int Technique = (int)player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique;
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
                    ci.cancel();
                } else {
                    selected += (entity.isShiftKeyDown() ? -1 : 1);
                }
            }
        }
    }
}
