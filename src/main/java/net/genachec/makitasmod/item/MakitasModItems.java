package net.genachec.makitasmod.item;

import net.genachec.makitasmod.MakitasMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MakitasModItems {

    public static final DeferredRegister<Item> ITEMS =
                            DeferredRegister.create(ForgeRegistries.ITEMS, MakitasMod.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
