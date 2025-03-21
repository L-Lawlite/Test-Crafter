package net.lawliet.testCrafter.blocks.customCrafter.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lawliet.testCrafter.Registration;
import net.lawliet.testCrafter.codec.utilityCodec;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.lawliet.testCrafter.codec.utilityCodec.CRITERIA_CODEC;
import static net.lawliet.testCrafter.codec.utilityCodec.sizeLimitedList;


public record CustomCrafterRecipe(List<Ingredient> input, Ingredient extraItem, Optional<Map<String, Criterion<?>>> conditions, ItemStack output) implements Recipe<CustomCrafterRecipeInput> {

//    public NonNullList<Ingredient> getGridItems() {
//        NonNullList<Ingredient> list = NonNullList.create();
//        input.forEach(list::add);
//        return list;
//    }



    @Override
    public boolean matches(CustomCrafterRecipeInput customCrafterRecipeInput, Level level) {
        if (level.isClientSide) {
            return false;
        }
        return false;
    }

    @Override
    public ItemStack assemble(CustomCrafterRecipeInput customCrafterRecipeInput, HolderLookup.Provider provider) {
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
        return null;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<CustomCrafterRecipe> {
        public static final MapCodec<CustomCrafterRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                sizeLimitedList(Ingredient.CODEC,1,6).fieldOf("ingredients").forGetter(CustomCrafterRecipe::input),
                Ingredient.CODEC.fieldOf("extra_item").forGetter(CustomCrafterRecipe::extraItem),
                CRITERIA_CODEC.optionalFieldOf("conditions").forGetter(CustomCrafterRecipe::conditions),
                ItemStack.CODEC.fieldOf("output").forGetter(CustomCrafterRecipe::output)
        ).apply(inst,CustomCrafterRecipe::new));

//        public static final StreamCodec<RegistryFriendlyByteBuf,CustomCrafterRecipe> STREAM_CODEC = StreamCodec.composite(
//                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), CustomCrafterRecipe::input,
//                Ingredient.CONTENTS_STREAM_CODEC, CustomCrafterRecipe::extraItem,
//                ByteBufCodecs.map(HashMap::new,ByteBufCodecs.STRING_UTF8,C).apply(ByteBufCodecs::optional), CustomCrafterRecipe::conditions,
//                ItemStack.STREAM_CODEC, CustomCrafterRecipe::output,
//                CustomCrafterRecipe::new
//        );

        @Override
        public MapCodec<CustomCrafterRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CustomCrafterRecipe> streamCodec() {
//            return STREAM_CODEC;
            return null;
        }


    }


}
