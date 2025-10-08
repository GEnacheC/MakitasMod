package net.genachec.makitasmod.datagen;

import net.genachec.makitasmod.MakitasMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MakitasMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MakitasModDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput pOut = gen.getPackOutput();

        ExistingFileHelper helper = event.getExistingFileHelper();

        CompletableFuture<HolderLookup.Provider> lProvider = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new MakitasModRecipeProvider(pOut));
        gen.addProvider(event.includeServer(), MakitasModLootTableProvider.create(pOut));

        MakitasModBlockTagGenerator bTagGen = gen
                .addProvider(event.includeServer(),
                            new MakitasModBlockTagGenerator(pOut, lProvider, helper));

    }
}
