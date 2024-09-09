package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.AIBloodBallProcedure;
import net.mcreator.jujutsucraft.procedures.LogicOwnerExistProcedure;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.UUID;

@Mixin(AIBloodBallProcedure.class)
public class MixinBloodBall {
    @ModifyConstant(method = "execute", constant = @Constant(doubleValue = 10.0), remap = false)
    private static double allowAny(double constant, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (LogicOwnerExistProcedure.execute(world, entity)) {
            if (world instanceof ServerLevel serverLevel) {
                Entity entity1 = serverLevel.getEntity(UUID.fromString(entity.getPersistentData().getString("OWNER_UUID")));
                if (entity1 != null) return entity1.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique;
            }
        }
        return constant;
    }
}
