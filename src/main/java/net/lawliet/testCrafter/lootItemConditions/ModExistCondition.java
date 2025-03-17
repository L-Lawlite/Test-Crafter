package net.lawliet.testCrafter.lootItemConditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lawliet.testCrafter.Registration;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.fml.ModList;


public record ModExistCondition(String value) implements LootItemCondition {
    public static final MapCodec<ModExistCondition> CODEC = RecordCodecBuilder.mapCodec(
            ist -> ist.group(
                    Codec.STRING.fieldOf("value").forGetter(ModExistCondition::value)
            )
            .apply(ist,ModExistCondition::new)
    );

    public ModExistCondition(String value) {
        this.value = value;
    }

    @Override
    public LootItemConditionType getType() {
        return Registration.MOD_EXIST.get();
    }


    @Override
    public boolean test(LootContext lootContext) {
        return ModList.get().isLoaded(value);
    }

    public static ModExistCondition.Builder mod(String mod_id) {
        return new ModExistCondition.Builder(mod_id);
    }

    public static class Builder implements LootItemCondition.Builder {
        private final String value;
        public Builder(String mod_id) {
            this.value = mod_id;
        }

        public ModExistCondition build() {return new ModExistCondition(this.value);}
    }
}
