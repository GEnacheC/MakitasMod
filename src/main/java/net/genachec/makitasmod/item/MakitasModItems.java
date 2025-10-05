package net.genachec.makitasmod.item;

import net.genachec.makitasmod.abstracts.MakitasModObject;
import net.genachec.makitasmod.interfaces.MakitasModItemsInterface;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;

public class MakitasModItems extends MakitasModObject implements MakitasModItemsInterface{

    public static final DeferredRegister<Item> ITEMS = createRegistry(MakitasModItems.class);

}
