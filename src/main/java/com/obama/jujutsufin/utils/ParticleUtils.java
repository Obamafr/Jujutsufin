package com.obama.jujutsufin.utils;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ParticleUtils {
    public static void makeCircle(Level level, ParticleOptions particle, double x, double y, double z, int particleTotal, int particlePer, double raidus, double dx, double dy, double dz, double speed, boolean cosX, boolean cosY, boolean sinY, boolean sinZ) {
        if (level instanceof ServerLevel serverLevel) {
            for (int loop = 0; loop < particleTotal; loop++) {
                double theta = (2 * Math.PI) / particleTotal * loop;
                double fx = (cosX ? x + Math.cos(theta) * raidus : x);
                double fy = (cosY ? y + Math.cos(theta) * raidus : y);
                fy = (sinY ? y + Math.sin(theta) * raidus : fy);
                double fz = (sinZ ? z + Math.sin(theta) * raidus : z);
                serverLevel.sendParticles(particle, fx, fy, fz, particlePer, dx, dy, dz, speed);
            }
        }
    }
}
