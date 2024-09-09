package com.obama.jujutsufin.effects;

import com.obama.jujutsufin.init.JujutsufinEffects;
import com.obama.jujutsufin.init.JujutsufinParticles;
import com.obama.jujutsufin.utils.ParticleUtils;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.init.JujutsucraftModParticleTypes;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SFAEffect extends MobEffect {
    public SFAEffect(){
        super(MobEffectCategory.BENEFICIAL, 9672601);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            ParticleUtils.makeCircle(serverLevel, JujutsufinParticles.SFAPARTICLE.get(), livingEntity.getX(), livingEntity.getY() + .3, livingEntity.getZ(), 90, 1, 8, 0, 0, 0, 0, true, false, false, true);
            serverLevel.sendParticles(JujutsucraftModParticleTypes.PARTICLE_CURSE_POWER_BLUE.get(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1, 4,0,4, 1);
            livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                if (!livingEntity.hasEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get())) cap.PlayerCursePower -= 2;
                cap.syncPlayerVariables(livingEntity);
            });
            AABB radius = new AABB(livingEntity.blockPosition()).inflate(8);
            List<Player> listPlayer = serverLevel.getEntitiesOfClass(Player.class, radius, p -> p.getPersistentData().getString("UtahimeUUID").equals(livingEntity.getStringUUID()));
            List<LivingEntity> listLivEnt = serverLevel.getEntitiesOfClass(LivingEntity.class, radius, p-> p.getPersistentData().getString("UtahimeUUID").equals(livingEntity.getStringUUID()));
            MobEffect SFABuff = JujutsufinEffects.SFABUFF.get();
            livingEntity.addEffect(new MobEffectInstance(SFABuff, 22, 0));
            for (LivingEntity livEnt : listLivEnt) {
                livEnt.addEffect(new MobEffectInstance(SFABuff, 22, 0));
            }
            for (Player player : listPlayer) {
                if (player != livingEntity) {
                    player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                        cap.PlayerCursePowerChange += 2.0/(listPlayer.size()-1);
                        cap.syncPlayerVariables(player);
                    });
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int a, int b) {return true;}

    @Override
    public @NotNull String getDescriptionId() {return "jujutsufin.effect.sfa";}
}
