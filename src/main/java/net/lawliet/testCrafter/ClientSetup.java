package net.lawliet.testCrafter;

//import net.lawliet.testCrafter.blockEntities.complexBlock.ComplexBlockRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = TestCrafter.MODID,bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void entityRenderingRegister(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(Registration.COMPLEX_BLOCK_ENTITY.get(), ComplexBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event) {
        Registration.registerScreen(event);
    }
}
