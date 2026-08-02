package com.obama.jujutsufin.init;

import com.obama.jujutsufin.JujutsufinMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, JujutsufinMod.MODID);
    public static final RegistryObject<Attribute> CURSE_ENERGY_REGEN = ATTRIBUTES.register("curse_energy_regen", () -> new RangedAttribute("jujutsufin.curse_regen", 0, Integer.MIN_VALUE, Integer.MAX_VALUE));
}
