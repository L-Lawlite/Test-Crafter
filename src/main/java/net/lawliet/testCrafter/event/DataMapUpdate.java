package net.lawliet.testCrafter.event;

import net.lawliet.testCrafter.Registration;
import net.lawliet.testCrafter.TestCrafter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapsUpdatedEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

@EventBusSubscriber(modid = TestCrafter.MODID)
public class DataMapUpdate {
    @SubscribeEvent
    public static void onDataMapsUpdated(DataMapsUpdatedEvent event) {
        event.ifRegistry(Registries.BLOCK, registry -> registry.getDataMap(NeoForgeDataMaps.OXIDIZABLES));
    }
}
