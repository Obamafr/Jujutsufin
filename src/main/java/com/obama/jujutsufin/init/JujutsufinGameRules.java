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
    public static final GameRules.Key<GameRules.IntegerValue> VeilRadius = GameRules.register("jjkfinVeilRadius", GameRules.Category.PLAYER, GameRules.IntegerValue.create(40));
    public static final GameRules.Key<GameRules.IntegerValue> KenjakuLimit = GameRules.register("jjkfinKenjakuTechniquesLimit", GameRules.Category.PLAYER, GameRules.IntegerValue.create(3));
    public static final GameRules.Key<GameRules.BooleanValue> SukunaPVP = GameRules.register("jjkfinSukunaPVP", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> MBANoDeath = GameRules.register("jjkfinMBANoDeath", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
    public static final GameRules.Key<GameRules.BooleanValue> KenjakuKeepTechniques = GameRules.register("jjkfinKenjakuKeepTechniques", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
}
