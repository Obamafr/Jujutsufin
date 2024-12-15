package com.obama.jujutsufin.init;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.block.VeilBlockEntity;
import com.obama.jujutsufin.entity.BeamProjectile;
import com.obama.jujutsufin.entity.PigeonViola;
import com.obama.jujutsufin.entity.Shikigami;
import com.obama.jujutsufin.entity.VeilEntity;
import net.mcreator.jujutsucraft.entity.RedEntity;
import net.mcreator.jujutsucraft.entity.RozetsuShikigamiEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JujutsufinMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, JujutsufinMod.MODID);
    public static final RegistryObject<BlockEntityType<VeilBlockEntity>> VeilBlock = BLOCK_ENTITIES.register("veil", () -> BlockEntityType.Builder.of(VeilBlockEntity::new, JujutsufinBlocks.VeilBlock.get()).build(null));
    public static final RegistryObject<EntityType<VeilEntity>> Veil = ENTITIES.register("veil", () -> EntityType.Builder.of(VeilEntity::new, MobCategory.CREATURE).sized(1,1).build("jujutsufin:veil"));
    public static final RegistryObject<EntityType<RozetsuShikigamiEntity>> Shikigami = ENTITIES.register("shikigami", () -> EntityType.Builder.of(Shikigami::new, MobCategory.CREATURE).sized(1,2).build("jujutsufin:shikigami"));
    public static final RegistryObject<EntityType<BeamProjectile>> Beam = ENTITIES.register("beam", () -> EntityType.Builder.<BeamProjectile>of(BeamProjectile::new, MobCategory.MISC).sized(1, 1).build("jujutsufin:beam"));
    public static final RegistryObject<EntityType<PigeonViola>> Viola = ENTITIES.register("viola", () -> EntityType.Builder.<PigeonViola>of(PigeonViola::new, MobCategory.MISC).sized(1, 1).build("jujutsufin:viola"));
}
