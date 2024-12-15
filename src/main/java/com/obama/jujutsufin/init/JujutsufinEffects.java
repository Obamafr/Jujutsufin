package com.obama.jujutsufin.init;

import com.obama.jujutsufin.effects.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "jujutsufin");
    public static final RegistryObject<MobEffect> HWB = EFFECTS.register("hwb", HWBEffect::new);
    public static final RegistryObject<MobEffect> HWBCOOLDOWN = EFFECTS.register("hwbcooldown", HWBCooldown::new);
    public static final RegistryObject<MobEffect> SFA = EFFECTS.register("sfa", SFAEffect::new);
    public static final RegistryObject<MobEffect> SFABUFF = EFFECTS.register("sfabuff", SFABuffs::new);
    public static final RegistryObject<MobEffect> BURNOUT = EFFECTS.register("burnout", BurnoutEffect::new);
    public static final RegistryObject<MobEffect> GRAVITY = EFFECTS.register("gravity", GravityEffect::new);
    public static final RegistryObject<MobEffect> ABSOLUTE = EFFECTS.register("absolute", ModeAbsolute::new);
}
