package com.obama.jujutsufin.effects;

import com.obama.jujutsufin.init.JujutsufinEffects;
import com.obama.jujutsufin.init.JujutsufinParticles;
import com.obama.jujutsufin.utils.ParticleUtils;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HWBEffect extends MobEffect {

    public HWBEffect() {
        super(MobEffectCategory.BENEFICIAL, 9672601);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        MobEffect SIMPLEDOMAIN = JujutsucraftModMobEffects.SIMPLE_DOMAIN.get();
        MobEffect UNSTABLE = JujutsucraftModMobEffects.UNSTABLE.get();
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            double x = livingEntity.getX();
            double y = livingEntity.getY();
            double z = livingEntity.getZ();
            ParticleUtils.makeCircle(serverLevel, JujutsufinParticles.HWBPARTICLE.get(), x, y + 1, z, 90, 1, 1.25, 0,0,0, 0, true, false, true, false);
            ParticleUtils.makeCircle(serverLevel, JujutsufinParticles.HWBPARTICLE.get(), x, y + 1, z, 90, 1, 1.25, 0,0,0, 0, true, false, false, true);
            ParticleUtils.makeCircle(serverLevel, JujutsufinParticles.HWBPARTICLE.get(), x, y + 1, z, 90, 1, 1.25, 0,0,0, 0, false, true, false, true);
            boolean found = false;
            List<LivingEntity> entities = serverLevel.getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(25));
            for (LivingEntity entity : entities) {
                if (entity.hasEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get()) && entity.getPersistentData().getString("domain_floor").equals("minecraft:air")) {
                    found = true;
                    break;
                }
            }
            double energy = livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower;
            if (energy <= 5 || livingEntity.getHealth() <= (livingEntity.getMaxHealth() / 3) || livingEntity.hasEffect(JujutsucraftModMobEffects.DOMAIN_EXPANSION.get())) {
                livingEntity.removeEffect(this);
                livingEntity.addEffect(new MobEffectInstance(JujutsufinEffects.HWBCOOLDOWN.get(), 600));
                livingEntity.removeEffect(SIMPLEDOMAIN);
                return;
            }
            livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                cap.PlayerCursePower -= 2.5;
                cap.syncPlayerVariables(livingEntity);
            });
            livingEntity.addEffect(new MobEffectInstance(UNSTABLE, 22, 0));
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
        livingEntity.removeEffect(JujutsucraftModMobEffects.SIMPLE_DOMAIN.get());
    }

    @Override
    public boolean isDurationEffectTick(int a, int b) {return true;}

    @Override
    public @NotNull String getDescriptionId() {return "jujutsufin.effect.hwb";}
}
