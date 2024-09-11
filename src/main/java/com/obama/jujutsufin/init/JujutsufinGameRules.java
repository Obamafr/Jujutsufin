package com.obama.jujutsufin.init;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class JujutsufinGameRules {
    public static final GameRules.Key<GameRules.IntegerValue> BlackFlashChance = GameRules.register("jjkfinBlackFlashChance", GameRules.Category.PLAYER, GameRules.IntegerValue.create(998));
    public static final GameRules.Key<GameRules.IntegerValue> RCTCost = GameRules.register("jjkfinRCTCost", GameRules.Category.PLAYER, GameRules.IntegerValue.create(10));
    public static final GameRules.Key<GameRules.IntegerValue> SixEyesMultiplier = GameRules.register("jjkfinSixEyesMultiplier", GameRules.Category.PLAYER, GameRules.IntegerValue.create(1));
    public static final GameRules.Key<GameRules.IntegerValue> SukunaMultiplier = GameRules.register("jjkfinSukunaMultiplier", GameRules.Category.PLAYER, GameRules.IntegerValue.create(5));
    public static final GameRules.Key<GameRules.IntegerValue> FatigueRate = GameRules.register("jjkfinFatigueRate", GameRules.Category.PLAYER, GameRules.IntegerValue.create(20));
    public static final GameRules.Key<GameRules.IntegerValue> BurnoutCost = GameRules.register("jjkfinBurnoutCost", GameRules.Category.PLAYER, GameRules.IntegerValue.create(30));
}
