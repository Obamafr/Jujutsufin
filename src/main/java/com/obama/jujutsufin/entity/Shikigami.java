package com.obama.jujutsufin.entity;

import net.mcreator.jujutsucraft.entity.RozetsuShikigamiEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;


import java.util.UUID;

public class Shikigami extends RozetsuShikigamiEntity {
    public Shikigami(EntityType<RozetsuShikigamiEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        String ownerUuid = this.getPersistentData().getString("OWNER_UUID");
        if (!ownerUuid.isEmpty()) {
            Player player = this.level().getPlayerByUUID(UUID.fromString(ownerUuid));
            if (player != null) {
                if (!player.isAlive()) {
                    this.discard();
                } else if (this.level().getEntitiesOfClass(Player.class, new AABB(blockPosition()).inflate(32), player1 -> player1 == player).isEmpty()) {
                    this.teleportTo(player.getX() + Math.random(), player.getY(), player.getZ() + Math.random());
                }
            }
        }
    }
}
