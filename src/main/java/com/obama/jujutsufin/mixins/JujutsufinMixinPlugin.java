package com.obama.jujutsufin.mixins;

import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JujutsufinMixinPlugin implements IMixinConfigPlugin {
    public static final boolean GT_2 = LoadingModList.get().getModFileById("jujutsu_kaisen_gt_2") != null;

    private final Map<String, Boolean> REQUIREMENTS = Map.of(
            "com.obama.jujutsufin.mixins.MixinGTAuthTechnique", GT_2,
            "com.obama.jujutsufin.mixins.MixinGTTechnique1", GT_2,
            "com.obama.jujutsufin.mixins.MixinGTTechnique2", GT_2
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return REQUIREMENTS.getOrDefault(mixinClassName, true);
    }

    // Below not in use
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
