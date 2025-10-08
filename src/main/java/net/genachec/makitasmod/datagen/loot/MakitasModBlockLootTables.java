package net.genachec.makitasmod.datagen.loot;

import net.genachec.makitasmod.block.MakitasModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class MakitasModBlockLootTables extends BlockLootSubProvider {
    public MakitasModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        MakitasModBlocks.BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get).forEach(this::dropSelf);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MakitasModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
