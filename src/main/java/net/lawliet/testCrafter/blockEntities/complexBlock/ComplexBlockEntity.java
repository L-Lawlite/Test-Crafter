//package net.lawliet.testCrafter.blockEntities.complexBlock;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.Connection;
//import net.minecraft.network.protocol.Packet;
//import net.minecraft.network.protocol.game.ClientGamePacketListener;
//import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.state.BlockState;
//import net.neoforged.jarjar.nio.util.Lazy;
//import net.neoforged.neoforge.items.IItemHandler;
//import net.neoforged.neoforge.items.ItemStackHandler;
//import org.checkerframework.checker.nullness.qual.NonNull;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import static net.lawliet.testCrafter.Registration.COMPLEX_BLOCK_ENTITY;
//
//public class ComplexBlockEntity extends BlockEntity {
//    public static final String ITEM_TAG = "Inventory";
//
//
//    // For Capabilities
//    public static int SLOT_COUNT = 1;
//    public static int SLOT = 0;
//    private final ItemStackHandler items = createItemHandler();
//    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> items);
//
//
//    // Idk what this function is doing
//    @NonNull
//    private ItemStackHandler createItemHandler() {
//        return new ItemStackHandler(SLOT_COUNT) {
//            @Override
//            protected void onContentsChanged(int slot) {
//                setChanged();
//                if (level == null) return;
//                level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(), Block.UPDATE_ALL);
//            }
//        };
//    }
//
//
//
//    public ComplexBlockEntity(BlockPos pos, BlockState blockState) {
//        super(COMPLEX_BLOCK_ENTITY.get(), pos, blockState);
//    }
//
//
//
//    public IItemHandler getItemHandler() {
//        return itemHandler.get();
//    }
//
//    // For Saving and Loading
//
//
//    @Override
//    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
//        super.saveAdditional(tag, registries);
//        saveClientData(tag,registries);
//    }
//
//    private void saveClientData(CompoundTag tag, HolderLookup.Provider provider) {
//        tag.put(ITEM_TAG,items.serializeNBT(provider));
//    }
//
//    @Override
//    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
//        super.loadAdditional(tag, registries);
//        loadClientData(tag,registries);
//    }
//
//    private void loadClientData(CompoundTag tag, HolderLookup.Provider provider) {
//        if(tag.contains(ITEM_TAG)) {
//            items.deserializeNBT(provider,tag.getCompound(ITEM_TAG));
//        }
//    }
//
//
//    //Server to Client synchronization
//    @NotNull
//    @Override
//    public CompoundTag getUpdateTag(@NotNull HolderLookup.Provider registries) {
//        CompoundTag tag =  super.getUpdateTag(registries);
//        saveClientData(tag,registries);
//        return tag;
//    }
//
//    @Override
//    public void handleUpdateTag(@NotNull CompoundTag tag,@NotNull HolderLookup.Provider lookupProvider) {
//        loadClientData(tag, lookupProvider);
//    }
//
//    @Nullable
//    @Override
//    public Packet<ClientGamePacketListener> getUpdatePacket() {
//        return ClientboundBlockEntityDataPacket.create(this);
//    }
//
//    @Override
//    public void onDataPacket(@NotNull Connection net, ClientboundBlockEntityDataPacket pkt,@NotNull HolderLookup.Provider lookupProvider) {
//        CompoundTag tag = pkt.getTag();
//        handleUpdateTag(tag,lookupProvider);
//    }
//
//
//    // Logic
//    public void tickServer() {
//        // Add per tick logic here
//
//        assert level != null;
//        if (level.getGameTime() % 20 != 0) return;
//
//        ItemStack stack = items.getStackInSlot(SLOT);
//        if (stack.isEmpty()) return;
//        if (!stack.isDamageableItem()) {
//            ejectItem();
//            return;
//        }
//
//        int value = stack.getDamageValue();
//        if (value <= 0) {
//            ejectItem();
//            return;
//        }
//        stack.setDamageValue(value - 1);
//
//
//    }
//
//    private void ejectItem() {
//        BlockPos pos = worldPosition.relative(Direction.UP);
//        assert level != null;
//        Block.popResource(level,pos,items.extractItem(SLOT,1,false));
//    }
//
//
//    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T t) {
//        if (t instanceof ComplexBlockEntity be) {
//
//        }
//    }
//}
