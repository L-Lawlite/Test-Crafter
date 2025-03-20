package net.lawliet.testCrafter.gui;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ResultSlot extends Slot {
    private final Player player;
    private int removeCount;

    public ResultSlot(Player player, Container container, int slot, int xPosition, int yPosition) {
        super(container, slot, xPosition, yPosition);
        this.player = player;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }

        return super.remove(amount);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void onSwapCraft(int numItemsCrafted) {
        this.removeCount += numItemsCrafted;
    }



    private static NonNullList<ItemStack> copyAllInputItems(CraftingInput input) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            nonnulllist.set(i, input.getItem(i));
        }

        return nonnulllist;
    }

    private NonNullList<ItemStack> getRemainingItems(CraftingInput input, Level level) {
        NonNullList var10000;
        if (level instanceof ServerLevel serverlevel) {
            var10000 = (NonNullList)serverlevel.recipeAccess().getRecipeFor(RecipeType.CRAFTING, input, serverlevel).map((p_380214_) -> ((CraftingRecipe)p_380214_.value()).getRemainingItems(input)).orElseGet(() -> copyAllInputItems(input));
        } else {
            var10000 = CraftingRecipe.defaultCraftingReminder(input);
        }
//
        return var10000;
    }



    public boolean isFake() {
        return true;
    }
}
