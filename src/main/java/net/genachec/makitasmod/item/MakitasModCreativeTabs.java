package net.genachec.makitasmod.item;

import net.genachec.makitasmod.MakitasMod;
import net.genachec.makitasmod.block.MakitasModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MakitasModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MakitasMod.MODID);

    public static final RegistryObject<CreativeModeTab> MAKITAS_MOD_TAB = CREATIVE_MOD_TABS.register("makitasmod_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(MakitasModItems.ITEMS.getEntries().stream().findFirst().get().get()))
                    .title(Component.translatable("creativetab.makitasmod_tab"))
                    .displayItems(((pParams, pOut) -> {
                        MakitasModBlocks.BLOCKS.getEntries()
                                .stream()
                                .map(RegistryObject::get).forEach(pOut::accept);
                    }))
                    .build()
            );

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
