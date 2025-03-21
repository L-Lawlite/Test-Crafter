package net.lawliet.testCrafter.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.WithConditions;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class utilityCodec {
    public static <T> Codec<List<T>> sizeLimitedList(Codec<T> baseCodec, int minSize, int maxSize) {
        return baseCodec.listOf().comapFlatMap(
                list -> list.size() >= minSize && list.size() <= maxSize
                        ? DataResult.success(list)
                        : DataResult.error(() -> String.format("List %s must have between %d and %d elements", list, minSize, maxSize)),
                Function.identity());
    }

    public static <T> Codec<Optional<WithConditions<T>>> conditionalCodec(Codec<T> baseCodec) {
        return ConditionalOps.createConditionalCodecWithConditions(baseCodec);
    };

    public static final Codec<Map<String, Criterion<?>>> CRITERIA_CODEC = Codec.unboundedMap(Codec.STRING, Criterion.CODEC).validate(
            validation -> validation.isEmpty()
            ? DataResult.error(() -> "Criteria cannot be empty")
            : DataResult.success(validation)
    );

//    public static final StreamCodec<RegistryFriendlyByteBuf,Optional<Map<String, Criterion<?>>>> CRITERIA_STREAM_CODEC = StreamCodec.composite();

}
