package com.obama.jujutsufin.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class HWBCooldown extends MobEffect {
    public HWBCooldown() {
        super(MobEffectCategory.NEUTRAL, 9672601);
    }

    @Override
    public String getDescriptionId() {return "jujutsufin.effect.hwbcooldown";}
}
