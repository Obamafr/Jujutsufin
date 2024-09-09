package com.obama.jujutsufin.init;

import com.obama.jujutsufin.JujutsufinMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, JujutsufinMod.MODID);
    public static final RegistryObject<SimpleParticleType> HWBPARTICLE = PARTICLES.register("hwbparticle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SFAPARTICLE = PARTICLES.register("sfaparticle", () -> new SimpleParticleType(true));
}
