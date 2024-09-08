package com.obama.jujutsufin.init;

import com.obama.jujutsufin.effects.HWBCooldown;
import com.obama.jujutsufin.effects.HWBEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "jujutsufin");
    public static final RegistryObject<MobEffect> HWB = EFFECTS.register("hwb", HWBEffect::new);
    public static final RegistryObject<MobEffect> HWBCOOLDOWN = EFFECTS.register("hwbcooldown", HWBCooldown::new);
}
