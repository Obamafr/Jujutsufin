package com.obama.jujutsufin.init;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.block.VeilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, JujutsufinMod.MODID);
    public static final RegistryObject<Block> VeilBlock = BLOCKS.register("veil", VeilBlock::new);

}
