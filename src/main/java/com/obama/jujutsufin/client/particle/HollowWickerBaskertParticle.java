package com.obama.jujutsufin.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class HollowWickerBaskertParticle extends TextureSheetParticle {

    protected HollowWickerBaskertParticle(ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, dx, dy, dz);
        this.friction = 0.8F;
        this.xd = dx;
        this.yd = dy;
        this.zd = dz;
        this.quadSize *= 0.4F;
        this.lifetime = 1;
        this.setSpriteFromAge(spriteSet);
    }

    public static Provider provider(SpriteSet spriteSet) {
        return new Provider(spriteSet);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {return ParticleRenderType.PARTICLE_SHEET_OPAQUE;}

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double x, double y, double z, double dx, double dy, double dz) {
            return new HollowWickerBaskertParticle(clientLevel, x, y, z, dx, dy, dz, this.spriteSet);
        }
    }
}
