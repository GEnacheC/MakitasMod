package net.genachec.makitasmod.datagen;

import net.genachec.makitasmod.datagen.loot.MakitasModBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class MakitasModLootTableProvider {
    public static LootTableProvider create(PackOutput pOut) {

        return new LootTableProvider(pOut, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(MakitasModBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }

}
