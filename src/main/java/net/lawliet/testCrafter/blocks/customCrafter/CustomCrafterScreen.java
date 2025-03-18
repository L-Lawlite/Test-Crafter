package net.lawliet.testCrafter.blocks.customCrafter;

import net.lawliet.testCrafter.TestCrafter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CustomCrafterScreen extends AbstractContainerScreen<CustomCrafterMenu> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(TestCrafter.MODID,"textures/gui/custom_crafter_gui.png");

    public CustomCrafterScreen(CustomCrafterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderType::guiTextured,GUI_TEXTURE,x,y,0.0F,0.0F,imageWidth,imageHeight,256,256);
    }
}
