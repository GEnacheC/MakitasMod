package net.genachec.makitasmod.abstracts;

import net.genachec.makitasmod.interfaces.MakitasModBaseInterface;
import net.genachec.makitasmod.interfaces.MakitasModBlockInterface;
import net.genachec.makitasmod.interfaces.MakitasModItemsInterface;
import net.genachec.makitasmod.MakitasMod;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public abstract class MakitasModObject {

    public static <B, T extends MakitasModBaseInterface> DeferredRegister<B> createRegistry(Class<T> clazz) {
        return DeferredRegister.create(getForgeRegistryByClass(clazz), MakitasMod.MODID);
    }

    @SuppressWarnings("unchecked")
    protected static <B, T extends MakitasModBaseInterface> IForgeRegistry<B> getForgeRegistryByClass(Class<T> clazz) {
        if (MakitasModItemsInterface.class.isAssignableFrom(clazz)) {
            return (IForgeRegistry<B>) ForgeRegistries.ITEMS;
        } else if (MakitasModBlockInterface.class.isAssignableFrom(clazz)) {
            return (IForgeRegistry<B>) ForgeRegistries.BLOCKS;
        } else if (EntityType.class.isAssignableFrom(clazz)) {
            return (IForgeRegistry<B>) ForgeRegistries.ENTITY_TYPES;
        }
        throw new IllegalArgumentException("Tipo desconhecido: " + clazz);
    }

    public static <T> void register(DeferredRegister<T> objects, IEventBus eventBus) {
        objects.register(eventBus);
    }
}
