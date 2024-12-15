package com.obama.jujutsufin.techniques.rozetsu;

import net.mcreator.jujutsucraft.procedures.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class AIMaster {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        switch (entity.getPersistentData().getInt("rozTechnique")) {
            case 1 -> AISukunaProcedure.execute(world, x, y, z, entity);
            case 2 -> AIGojoSchoolDaysProcedure.execute(world, x, y, z, entity);
            case 3 -> AIInumakiTogeProcedure.execute(world, x, y, z, entity);
            case 4 -> AIJogoProcedure.execute(world, x, y, z, entity);
            case 5 -> AIOkkotsuProcedure.execute(world, x, y, z, entity);
            case 6 -> AIFushiguroMegumiProcedure.execute(world, x, y, z, entity);
            case 7 -> AIKashimoHajimeProcedure.execute(world, x, y, z, entity);
            case 8 -> AIDagonProcedure.execute(world, x, y, z, entity);
            case 9 -> AITsukumoYukiProcedure.execute(world, x, y, z, entity);
            case 10 -> AIChosoProcedure.execute(world, x, y, z, entity);
            case 11 -> AIMeiMeiProcedure.execute(world, x, y, z, entity);
            case 12 -> AIIshigoriRyuProcedure.execute(world, x, y, z, entity);
            case 13 -> AINanamiKentoProcedure.execute(world, x, y, z, entity);
            case 14 -> AIHanamiProcedure.execute(world, x, y, z, entity);
            case 15 -> AIMahitoProcedure.execute(world, x, y, z, entity);
            case 16 -> AIEightHandledSwrodDivergentSilaDivineGeneralMahoragaProcedure.execute(world, x, y, z, entity);
            case 17 -> AITakabaFumihikoProcedure.execute(world, x, y, z, entity);
            case 18 -> AIGetoSchoolDaysProcedure.execute(world, x, y, z, entity);
            case 19 -> AIZeninNaoyaProcedure.execute(world, x, y, z, entity);
            case 20 -> AITodoProcedure.execute(world, x, y, z, entity);
            case 21 -> AIItadoriYujiProcedure.execute(world, x, y, z, entity);
            case 22 -> AIZeninJinichiProcedure.execute(world, x, y, z, entity);
            case 23 -> AIKurourushiProcedure.execute(world, x, y, z, entity);
            case 24 -> AIUraumeProcedure.execute(world, x, y, z, entity);
            case 25 -> AICursedSpirit05Procedure.execute(world, x, y, z, entity);
            case 26 -> AIZeninOgiProcedure.execute(world, x, y, z, entity);
            case 27 -> AIHigurumaProcedure.execute(world, x, y, z, entity);
            case 28 -> AIHanaKurusuProcedure.execute(world, x, y, z, entity);
            case 29 -> AIHakariProcedure.execute(world, x, y, z, entity);
            case 30 -> AIMiguelProcedure.execute(world, x, y, z, entity);
            case 31 -> AIKusakabeAtsuyaProcedure.execute(world, x, y, z, entity);
            case 32 -> AIZeninChojuroProcedure.execute(world, x, y, z, entity);
            case 33 -> AIYagaProcedure.execute(world, x, y, z, entity);
            case 34 -> AIKugisakiNobaraProcedure.execute(world, x, y, z, entity);
            case 35 -> AIYoshinoJunpeiProcedure.execute(world, x, y, z, entity);
            case 36 -> AINishimiyaProcedure.execute(world, x, y, z, entity);
            case 37 -> AIDhruvrakdawalaProcedure.execute(world, x, y, z, entity);
            case 38 -> AITakakoUroProcedure.execute(world, x, y, z, entity);
            case 39 -> AIYorozuProcedure.execute(world, x, y, z, entity);
            case 40 -> AIInoTakumaProcedure.execute(world, x, y, z, entity);
            default -> AIActiveProcedure.execute(world, x, y, z, entity);
        }
    }
}
