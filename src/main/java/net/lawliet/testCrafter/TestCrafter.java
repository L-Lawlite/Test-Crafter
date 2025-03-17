package net.lawliet.testCrafter;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TestCrafter.MODID)
public class TestCrafter
{
    public static final String MODID = "test_crafter";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TestCrafter(IEventBus modEventBus, ModContainer modContainer)
    {

        Registration.init(modEventBus);
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(Registration::addCreative);
        modEventBus.addListener(Registration::addCapabilities);
//        modEventBus.addListener(DataGeneration::generate);



        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        Registration.commonSetup(event);
    }




}
