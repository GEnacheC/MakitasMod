package net.genachec.makitasmod;

import com.mojang.logging.LogUtils;
import net.genachec.makitasmod.block.MakitasModBlocks;
import net.genachec.makitasmod.item.MakitasModCreativeTabs;
import net.genachec.makitasmod.item.MakitasModItems;
import net.genachec.makitasmod.sound.MakitasModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MakitasMod.MODID)
public class MakitasMod
{
    public static final String MODID = "makitasmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MakitasMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MakitasModCreativeTabs.register(modEventBus);
        MakitasModItems.register(MakitasModItems.ITEMS, modEventBus);
        MakitasModBlocks.register(MakitasModBlocks.BLOCKS, modEventBus);
        MakitasModSounds.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

   @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
    }
}
