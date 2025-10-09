package net.genachec.makitasmod.entity;

import net.genachec.makitasmod.abstracts.MakitasModObject;
import net.genachec.makitasmod.interfaces.registers.MakitasModEntityInterface;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;

public class MakitasModEntities extends MakitasModObject implements MakitasModEntityInterface {
    public static final DeferredRegister<EntityType<?>> ENTITIES = createRegistry(MakitasModEntities.class);

}
