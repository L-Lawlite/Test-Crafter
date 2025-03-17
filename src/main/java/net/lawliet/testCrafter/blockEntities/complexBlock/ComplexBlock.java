//package net.lawliet.testCrafter.blockEntities.complexBlock;
//
//import net.lawliet.testCrafter.Registration;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.EntityBlock;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityTicker;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.block.state.BlockState;
//
//import javax.annotation.Nullable;
//
//public class ComplexBlock extends Block implements EntityBlock {
//    public ComplexBlock(BlockBehaviour.Properties properties) {
//        super(properties);
//
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pos,BlockState state) {
//        return new ComplexBlockEntity(pos,state);
//    }
//
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
//        return blockEntityType == Registration.COMPLEX_BLOCK_ENTITY.get() ? ComplexBlockEntity::tick : null;
//    }
//}
