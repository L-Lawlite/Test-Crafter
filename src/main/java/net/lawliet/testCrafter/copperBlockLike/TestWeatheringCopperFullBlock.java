package net.lawliet.testCrafter.copperBlockLike;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class TestWeatheringCopperFullBlock extends Block implements WeatheringCopper {
    public static final MapCodec<TestWeatheringCopperFullBlock> CODEC = RecordCodecBuilder.mapCodec((p_368452_) -> p_368452_.group(WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopper::getAge), propertiesCodec()).apply(p_368452_, TestWeatheringCopperFullBlock::new));
    private final WeatherState weatherState;

    @Override
    public MapCodec<TestWeatheringCopperFullBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.changeOverTime(state,level,pos,random);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return this.weatherState != WeatherState.OXIDIZED;
    }

    public TestWeatheringCopperFullBlock(WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
    }


    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }
}
