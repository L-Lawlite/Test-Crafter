package net.lawliet.testCrafter.blocks.customCrafter.recipes;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;

public class CustomCrafterResultSlot extends ResultSlot {
    private final Player player;
    private final Container craftingContainer;
    private final int containerSize;
    private int removeCount;

    public CustomCrafterResultSlot(Player player, Container craftingContainer,Container resultContainer , int slot, int xPosition, int yPosition, int containerSize) {
        super(player,(CraftingContainer) null ,resultContainer,slot , xPosition, yPosition);
        this.player = player;
        this.craftingContainer = craftingContainer;
        this.containerSize = containerSize;
    }


    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        if (this.removeCount > 0) {
            stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
            EventHooks.firePlayerCraftingEvent(this.player, stack, this.craftingContainer);
        }


        this.removeCount = 0;
    }




    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        CommonHooks.setCraftingPlayer(player);
        CommonHooks.setCraftingPlayer((Player)null);

        for(int k = 0; k < containerSize; ++k) {
            ItemStack itemstack = this.craftingContainer.getItem(k);
                if (!itemstack.isEmpty()) {
                    this.craftingContainer.removeItem(k, 1);
                }

        }
    }

}
