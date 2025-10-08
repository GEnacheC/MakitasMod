package net.genachec.makitasmod.datagen;

import net.genachec.makitasmod.MakitasMod;
import net.genachec.makitasmod.block.MakitasModBlocks;
import net.genachec.makitasmod.util.MakitasModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MakitasModBlockTagGenerator extends BlockTagsProvider {


    public MakitasModBlockTagGenerator(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MakitasMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        MakitasModBlocks.BLOCKS.getEntries().forEach((p) -> {
            this.tag(MakitasModTags.Blocks.MAKITAS_BLOCK)
                    .add(p.get());

            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(p.get());

            this.tag(BlockTags.NEEDS_IRON_TOOL).add(p.get());
        });


    }
}
