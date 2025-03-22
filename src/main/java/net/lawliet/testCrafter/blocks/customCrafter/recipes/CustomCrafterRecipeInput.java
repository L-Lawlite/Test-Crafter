package net.lawliet.testCrafter.blocks.customCrafter.recipes;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record CustomCrafterRecipeInput(List<ItemStack> input, ItemStack extraItem) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {
        if (i == 6) return extraItem;
        return input.get(i);
    }

    @Override
    public int size() {
        return 7;
    }
}
