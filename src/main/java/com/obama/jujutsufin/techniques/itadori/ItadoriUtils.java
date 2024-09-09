package com.obama.jujutsufin.techniques.itadori;

import net.mcreator.jujutsucraft.entity.BloodBallEntity;
import net.mcreator.jujutsucraft.procedures.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class ItadoriUtils {
    public static boolean execute(ServerLevel serverLevel, double x, double y, double z, LivingEntity livingEntity, int skill) {
        boolean found = false;
        CompoundTag ForgeData = livingEntity.getPersistentData();
        skill %= 2100;
        switch (skill) {
            case 5: {
                found = true;
                ForgeData.putDouble("skill", 1005);
                break;
            }
            case 6: {
                found = true;
                ForgeData.putDouble("skill", 1006);
                break;
            }
            case 7: {
                found = true;
                PiercingBloodProcedure.execute(serverLevel, x, y, z, livingEntity);
                break;
            }
            case 8: {
                found = true;
                ForgeData.putDouble("skill", 1008);
                break;
            }
            case 9: {
                found = true;
                TechniqueFlowingRedScaleProcedure.execute(serverLevel, x, y, z, livingEntity);
                break;
            }
            case 16: {
                found = true;
                livingEntity.getPersistentData().putDouble("skill", 1016);
                break;
            }
            case 18: {
                found = true;
                ForgeData.putDouble("skill", 1018);
                break;
            }
            case 20: {
                ChosoDomainExpansionProcedure.execute(serverLevel, x, y, z, livingEntity);
                found = true;
                break;
            }
        }
        return found;
    }
}
