package net.genachec.makitasmod.event;

import net.genachec.makitasmod.MakitasMod;
import net.genachec.makitasmod.entity.ai.SearchMakitaBlockGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MakitasMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MakitasModBusEvent {


    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Chicken chicken)) return;
        chicken.goalSelector.getAvailableGoals().clear();
        chicken.goalSelector.addGoal(0, new SearchMakitaBlockGoal(chicken));

    }
}
