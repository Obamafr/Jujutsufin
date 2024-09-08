package com.obama.jujutsufin.effects;

import com.obama.jujutsufin.init.JujutsufinParticles;
import com.obama.jujutsufin.utils.ParticleUtils;
import net.mcreator.jujutsucraft.entity.DomainExpansionEntityEntity;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HWBEffect extends MobEffect {
    public MobEffect SIMPLEDOMAIN = JujutsucraftModMobEffects.SIMPLE_DOMAIN.get();
    public MobEffect UNSTABLE = JujutsucraftModMobEffects.UNSTABLE.get();

    public HWBEffect() {
        super(MobEffectCategory.BENEFICIAL, 9672601);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            ParticleUtils.makeCircle(serverLevel, JujutsufinParticles.HWBPARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 90, 1, 1.25, 0,0,0, 0, true, false, true, false);
            ParticleUtils.makeCircle(serverLevel, JujutsufinParticles.HWBPARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 90, 1, 1.25, 0,0,0, 0, true, false, false, true);
            ParticleUtils.makeCircle(serverLevel, JujutsufinParticles.HWBPARTICLE.get(), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 90, 1, 1.25, 0,0,0, 0, false, true, false, true);
            boolean found = false;
            List<LivingEntity> entities = serverLevel.getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(25));
            for (LivingEntity entity : entities) {
                if (entity.hasEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get()) && entity.getPersistentData().getString("domain_floor").equals("minecraft:air")) {
                    found = true;
                    break;
                }
            }
            LazyOptional<JujutsucraftModVariables.PlayerVariables> playerVariables = livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null);
            double energy = playerVariables.orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower;
            if (energy <= 5 || livingEntity.getHealth() <= (livingEntity.getMaxHealth() / 3)) {
                livingEntity.removeEffect(this);
                livingEntity.removeEffect(UNSTABLE);
                livingEntity.removeEffect(SIMPLEDOMAIN);
            }
            playerVariables.ifPresent(cap -> {
                cap.PlayerCursePowerChange -= 5;
                cap.syncPlayerVariables(livingEntity);
            });
            livingEntity.addEffect(new MobEffectInstance(UNSTABLE, -1, 0));
            if (!found) {
                livingEntity.addEffect(new MobEffectInstance(SIMPLEDOMAIN, -1, 0));
            } else {
                livingEntity.removeEffect(SIMPLEDOMAIN);
            }
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap map, int a) {
        super.removeAttributeModifiers(livingEntity, map, a);
        livingEntity.removeEffect(UNSTABLE);
        livingEntity.removeEffect(SIMPLEDOMAIN);
    }

    @Override
    public boolean isDurationEffectTick(int a, int b) {return true;}

    @Override
    public @NotNull String getDescriptionId() {return "jujutsufin.effect.hwb";}
}
