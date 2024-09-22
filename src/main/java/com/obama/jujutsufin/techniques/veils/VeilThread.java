package com.obama.jujutsufin.techniques.veils;

import com.obama.jujutsufin.entity.VeilEntity;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.minecraft.server.level.ServerLevel;

public class VeilThread extends Thread {
    private final boolean madeVeil;
    private final ServerLevel serverLevel;
    private final VeilEntity veil;

    @Override
    public void run() {
        int radius = serverLevel.getLevelData().getGameRules().getInt(JujutsufinGameRules.VeilRadius);
        for(int loopX = veil.getVeilX() - radius; loopX <= veil.getVeilX() + radius; ++loopX) {
            for (int loopY = veil.getVeilY() - radius; loopY <= veil.getVeilY() + radius; ++loopY) {
                for (int loopZ = veil.getVeilZ() - radius; loopZ <= veil.getVeilZ() + radius; ++loopZ) {
                    double distance = Math.sqrt(Math.pow((loopX - veil.getVeilX()), 2.0) + Math.pow((loopY - veil.getVeilY()), 2.0) + Math.pow(loopZ - veil.getVeilZ(), 2.0));
                    if (distance <= radius && distance >= (radius - 1)) {
                        int finalLoopX = loopX;
                        int finalLoopY = loopY;
                        int finalLoopZ = loopZ;
                        if (madeVeil) {
                            serverLevel.getServer().execute(() -> veil.makeVeil(serverLevel, finalLoopX, finalLoopY, finalLoopZ));
                        } else {
                            serverLevel.getServer().execute(() -> veil.breakVeil(serverLevel, finalLoopX, finalLoopY, finalLoopZ));
                        }
                    }
                }
            }
        }
    }


    public VeilThread(ServerLevel serverLevel, VeilEntity veil, boolean madeVeil) {
        this.serverLevel = serverLevel;
        this.veil = veil;
        this.madeVeil = madeVeil;
    }

}
