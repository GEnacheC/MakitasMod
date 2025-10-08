package net.genachec.makitasmod.util;

import net.genachec.makitasmod.MakitasMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MakitasModTags {
    public static class Blocks {
        public static final TagKey<Block> MAKITAS_BLOCK = tag("makitas_block");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MakitasMod.MODID, name));
        }
    }
}
