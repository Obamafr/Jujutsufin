package com.obama.jujutsufin.init;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.block.VeilBlockEntity;
import com.obama.jujutsufin.entity.VeilEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JujutsufinMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, JujutsufinMod.MODID);
    public static final RegistryObject<BlockEntityType<VeilBlockEntity>> VeilBlock = BLOCK_ENTITIES.register("veil", () -> BlockEntityType.Builder.of(VeilBlockEntity::new, JujutsufinBlocks.VeilBlock.get()).build(null));
    public static final RegistryObject<EntityType<VeilEntity>> Veil = ENTITIES.register("veil", () -> EntityType.Builder.of(VeilEntity::new, MobCategory.CREATURE).sized(1,1).build("jujutsufin:veil"));
}
