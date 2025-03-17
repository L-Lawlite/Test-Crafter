//package net.lawliet.testCrafter.blockEntities.complexBlock;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import com.mojang.math.Axis;
//import net.lawliet.testCrafter.TestCrafter;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.LightTexture;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//import net.minecraft.client.renderer.entity.ItemRenderer;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemDisplayContext;
//import net.minecraft.world.item.ItemStack;
//import net.neoforged.neoforge.capabilities.Capabilities;
//import net.neoforged.neoforge.items.IItemHandler;
//import org.joml.Matrix4f;
//import org.joml.Quaternionf;
//
//public class ComplexBlockRenderer implements BlockEntityRenderer<ComplexBlockEntity> {
//    public static final ResourceLocation LIGHT = ResourceLocation.fromNamespaceAndPath(TestCrafter.MODID,"block/light");
//
//    public ComplexBlockRenderer(BlockEntityRendererProvider.Context context) {}
//
//    @Override
//    public void render(ComplexBlockEntity blockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
//        IItemHandler itemHandler = blockEntity.getLevel().getCapability(Capabilities.ItemHandler.BLOCK,blockEntity.getBlockPos(),null);
//        if (itemHandler == null) return;
//
//        ItemStack stack = itemHandler.getStackInSlot(ComplexBlockEntity.SLOT);
//        if (stack.isEmpty()) return;
//
//        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//        long millis = System.currentTimeMillis();
//
//        poseStack.pushPose();
//        poseStack.pushPose();
//        poseStack.scale(.5f,.5f,.5f);
//        poseStack.translate(1f,2.8f,1f);
//        float angle = ((millis / 45f) % 360);
//        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
//        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT,combinedOverlay,poseStack,multiBufferSource,Minecraft.getInstance().level,0);
//        poseStack.popPose();
//    }
//
//    private static void renderBillboardQuadBright(PoseStack matrixStack, VertexConsumer builder,float scale, ResourceLocation texture) {
//        int b1 = LightTexture.FULL_BRIGHT >> 16 & 65535;
//        int b2 = LightTexture.FULL_BRIGHT & 65535;
//
//        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(texture).apply(texture);
//        matrixStack.pushPose();
//        matrixStack.translate(0.5f,0.95f,0.5f);
//        Quaternionf rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
//        matrixStack.mulPose(rotation);
//        Matrix4f matrix = matrixStack.last().pose();
//        builder.addVertex(matrix,-scale,-scale,0f).setColor(255,255,255,255).setUv(sprite.getU0(),sprite.getV0()).setUv2(b1,b2).setNormal(1,0,0);
//        builder.addVertex(matrix,-scale,scale,0f).setColor(255,255,255,255).setUv(sprite.getU0(),sprite.getV1()).setUv2(b1,b2).setNormal(1,0,0);
//        builder.addVertex(matrix,scale,scale,0f).setColor(255,255,255,255).setUv(sprite.getU1(),sprite.getV1()).setUv2(b1,b2).setNormal(1,0,0);
//        builder.addVertex(matrix,scale,-scale,0f).setColor(255,255,255,255).setUv(sprite.getU1(),sprite.getV0()).setUv2(b1,b2).setNormal(1,0,0);
//        matrixStack.popPose();
//    }
//}
