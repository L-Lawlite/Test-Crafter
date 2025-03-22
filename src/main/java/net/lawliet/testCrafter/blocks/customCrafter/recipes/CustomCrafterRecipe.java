package net.lawliet.testCrafter.blocks.customCrafter.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lawliet.testCrafter.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static net.lawliet.testCrafter.codec.utilityCodec.sizeLimitedList;


public record CustomCrafterRecipe(List<Ingredient> input, Ingredient extraItem, ItemStack output) implements Recipe<CustomCrafterRecipeInput> {

    @Override
    public boolean matches(CustomCrafterRecipeInput customCrafterRecipeInput, Level level) {
        if (level.isClientSide) {
            return false;
        }

        List<ItemStack> nonEmptyList = new ArrayList<>();
        for (ItemStack item: customCrafterRecipeInput.input()) {
            if (!item.isEmpty()) {
                nonEmptyList.add(item);
            }
        }

        return extraItem.test(customCrafterRecipeInput.getItem(6)) && RecipeMatcher.findMatches(nonEmptyList,input) != null;
    }

    @Override @NotNull
    public ItemStack assemble(@NotNull CustomCrafterRecipeInput customCrafterRecipeInput, HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<CustomCrafterRecipeInput>> getSerializer() {
        return Registration.CUSTOM_CRAFTER_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<CustomCrafterRecipeInput>> getType() {
        return Registration.CUSTOM_CRAFTER_RECIPE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<CustomCrafterRecipe> {
        public static final MapCodec<CustomCrafterRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                sizeLimitedList(Ingredient.CODEC,1,6).fieldOf("ingredients").forGetter(CustomCrafterRecipe::input),
                Ingredient.CODEC.fieldOf("extra_item").forGetter(CustomCrafterRecipe::extraItem),
                ItemStack.CODEC.fieldOf("output").forGetter(CustomCrafterRecipe::output)
        ).apply(inst,CustomCrafterRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf,CustomCrafterRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), CustomCrafterRecipe::input,
                Ingredient.CONTENTS_STREAM_CODEC, CustomCrafterRecipe::extraItem,
                ItemStack.STREAM_CODEC, CustomCrafterRecipe::output,
                CustomCrafterRecipe::new
        );

        @Override
        public MapCodec<CustomCrafterRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CustomCrafterRecipe> streamCodec() {
            return STREAM_CODEC;
        }


    }


}
