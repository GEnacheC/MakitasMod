package net.genachec.makitasmod.block;

import net.genachec.makitasmod.abstracts.MakitasModObject;
import net.genachec.makitasmod.interfaces.MakitasModBlockInterface;
import net.genachec.makitasmod.item.MakitasModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MakitasModBlocks extends MakitasModObject implements MakitasModBlockInterface {
    public static final DeferredRegister<Block> BLOCKS = createRegistry(MakitasModBlocks.class);

    private static final BlockBehaviour.Properties MAKITAS_BASE_PROPS =
            BlockBehaviour.Properties.copy(Blocks.ANVIL).noOcclusion();

    public static final RegistryObject<Block> IRON_MAKITA_BLOCK =
                registerBlock("makitas_iron_makita_block",
                            () -> new Block(MAKITAS_BASE_PROPS));
    public static final RegistryObject<Block> GOLD_MAKITA_BLOCK =
            registerBlock("makitas_gold_makita_block",
                    () -> new Block(MAKITAS_BASE_PROPS));
    public static final RegistryObject<Block> DIAMOND_MAKITA_BLOCK =
            registerBlock("makitas_diamond_makita_block",
                    () -> new Block(MAKITAS_BASE_PROPS));
    public static final RegistryObject<Block> END_MAKITA_BLOCK =
            registerBlock("makitas_end_makita_block",
                    () -> new Block(MAKITAS_BASE_PROPS));

    private static <T extends  Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        registerBlockItem(name, toReturn);

        return  toReturn;
    }


    private static <T extends  Block>void registerBlockItem(String name, RegistryObject<T> block){
        MakitasModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}
