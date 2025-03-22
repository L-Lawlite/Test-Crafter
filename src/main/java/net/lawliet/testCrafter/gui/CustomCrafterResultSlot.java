package net.lawliet.testCrafter.gui;

import net.lawliet.testCrafter.blocks.customCrafter.recipes.CustomCrafterRecipeInput;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;

public class CustomCrafterResultSlot extends Slot {
    private final Player player;
    private final Container craftingContainer;
    private final int containerSize;
    private int removeCount;
    private final NonNullList<ItemStack> items;

    public CustomCrafterResultSlot(Player player, Container craftingContainer,Container resultContainer , int slot, int xPosition, int yPosition, int containerSize) {
//        super(player,(CraftingContainer) null ,resultContainer,slot , xPosition, yPosition);
        super(resultContainer,slot,xPosition,yPosition);
        this.player = player;
        this.craftingContainer = craftingContainer;
        this.containerSize = containerSize;
        this.items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }


    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        if (this.removeCount > 0) {
            stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
            EventHooks.firePlayerCraftingEvent(this.player, stack, this.craftingContainer);
        }


        this.removeCount = 0;
    }




//    @Override
//    public void onTake(Player player, ItemStack stack) {
//        this.checkTakeAchievements(stack);
//        CustomCrafterRecipeInput.Positioned craftinginput$positioned = CustomCrafterRecipeInput.ofPositioned;
//        CustomCrafterRecipeInput craftinginput = craftinginput$positioned.input();
//        CommonHooks.setCraftingPlayer(player);
//        NonNullList<ItemStack> nonnulllist = this.getRemainingItems(craftinginput, player.level());
//        CommonHooks.setCraftingPlayer((Player)null);
//
//        for(int k = 0; k < containerSize; ++k) {
//            ItemStack itemstack = this.craftingContainer.getItem(k);
//            ItemStack itemstack1 = nonnulllist.get(k);
//                if (!itemstack.isEmpty()) {
//                    this.craftingContainer.removeItem(k, 1);
//                    itemstack = this.craftingContainer.getItem(k);
//                }
//
//                if (!itemstack1.isEmpty()) {
//                    if (itemstack.isEmpty()) {
//                        this.craftingContainer.setItem(k, itemstack1);
//                    } else if (ItemStack.isSameItemSameComponents(itemstack, itemstack1)) {
//                        itemstack1.grow(itemstack.getCount());
//                        this.craftingContainer.setItem(k, itemstack1);
//                    } else if (!this.player.getInventory().add(itemstack1)) {
//                        this.player.drop(itemstack1, false);
//                    }
//            }
//        }
//    }
//
//    private NonNullList<ItemStack> getRemainingItems(CustomCrafterRecipeInput craftinginput, Level level) {
//    }

}
