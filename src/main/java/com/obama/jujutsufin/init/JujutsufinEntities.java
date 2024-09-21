package com.obama.jujutsufin.init;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.entity.VeilEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JujutsufinMod.MODID);
    public static final RegistryObject<EntityType<VeilEntity>> VEIL = ENTITIES.register("veil", () -> EntityType.Builder.of(VeilEntity::new, MobCategory.CREATURE).sized(1,1).build("jujutsufin:veil"));
}
