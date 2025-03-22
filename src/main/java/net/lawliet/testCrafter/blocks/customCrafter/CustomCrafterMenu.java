package net.lawliet.testCrafter.blocks.customCrafter;

import net.lawliet.testCrafter.Registration;
import net.lawliet.testCrafter.blocks.customCrafter.recipes.CustomCrafterRecipe;
import net.lawliet.testCrafter.blocks.customCrafter.recipes.CustomCrafterRecipeInput;
import net.lawliet.testCrafter.gui.CustomCrafterResultSlot;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Optional;

public class CustomCrafterMenu extends AbstractContainerMenu{
    private final ContainerLevelAccess access;
//    private final Player player;
    protected Container container;
    protected ResultContainer resultContainer;
    private final Player player;
    Runnable slotUpdateListener = () -> {
    };
    /*
     CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
     must assign a slot number to each of the slots used by the GUI.
     For this container, we can see both the tile inventory's slots and the player inventory slots and the hotbar.
     Each time we add a Slot to the container, it automatically increases the slotIndex, which means
      0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
      9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
      36 - 43 = TileInventory slots, which map to our TileEntity slot numbers 0 - 7)
     */
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int GRID_WIDTH = 3;
    private static final int GRID_HEIGHT = 2;
    private static final int MODIFICATION_SLOT_COUNT = 1;
    private static final int MODIFICATION_SLOT_INDEX = TE_INVENTORY_FIRST_SLOT_INDEX + GRID_HEIGHT * GRID_WIDTH ;
    private static final int RESULT_SLOT_COUNT = 1;
    private static final int RESULT_SLOT_INDEX = MODIFICATION_SLOT_INDEX + MODIFICATION_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = GRID_WIDTH * GRID_HEIGHT + MODIFICATION_SLOT_COUNT + RESULT_SLOT_COUNT;

    public CustomCrafterMenu(int containerId, Inventory playerInventory) {
        this(containerId,playerInventory,ContainerLevelAccess.NULL);
    }

    public CustomCrafterMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(Registration.CUSTOM_CRATER_MENU.get(),containerId);
        this.access = access;
        this.player = playerInventory.player;
        this.container = new SimpleContainer(7) {
            @Override
            public void setChanged() {
                super.setChanged();
                CustomCrafterMenu.this.slotsChanged(this);
                CustomCrafterMenu.this.slotUpdateListener.run();
            }
        };
        this.resultContainer = new ResultContainer();
        addPlayerHotBar(playerInventory);
        addPlayerInventory(playerInventory);

        addGrid(GRID_HEIGHT,GRID_WIDTH);
        addModificationSlot();
        addResultSlot(playerInventory.player);
    }

    protected void addResultSlot(Player player) {
        this.addSlot(new CustomCrafterResultSlot(player,container,resultContainer,0,134,32,7));
    }

    protected void addModificationSlot() {
        this.addSlot(new Slot(container, 6, 84,32));
    }

    protected void addGrid(int row, int col) {
        for (int i=0; i < row; i++) {
            for (int j=0; j < col; j++) {
                this.addSlot(new Slot(container,i * col + j,20+j*18,25+i*18));
            }
        }
    }

    protected void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < PLAYER_INVENTORY_ROW_COUNT; ++i) {
            for (int l = 0; l < PLAYER_INVENTORY_COLUMN_COUNT; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * PLAYER_INVENTORY_COLUMN_COUNT + HOTBAR_SLOT_COUNT, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    protected void addPlayerHotBar(Inventory playerInventory) {
        for (int i = 0; i < HOTBAR_SLOT_COUNT; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }


    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return AbstractContainerMenu.stillValid(this.access,player,Registration.CUSTOM_CRAFTER_BLOCK.get());
    }

    @Override
    public void slotsChanged(Container container) {
        this.access.execute((level, pos) -> {
            if(level instanceof ServerLevel serverLevel) {
                slotChangedCraftingMenu(this,serverLevel,this.player,this.resultContainer, null);
            }
        });
        super.slotsChanged(container);
    }

    protected void slotChangedCraftingMenu(AbstractContainerMenu menu, ServerLevel level, Player player,Container resultContainer,@Nullable RecipeHolder<CustomCrafterRecipe> recipe) {
//        List<ItemStack> gridItems = IntStream.range(0,6).mapToObj(container::getItem).toList();
        List<ItemStack> gridItems = this.getGridSlots(GRID_HEIGHT * GRID_WIDTH).stream().map(Slot::getItem).toList();
        ItemStack extraItem = this.getExtraSlot().getItem();

        CustomCrafterRecipeInput craftingInput = new CustomCrafterRecipeInput(gridItems,extraItem);

        ServerPlayer serverPlayer = (ServerPlayer) player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<RecipeHolder<CustomCrafterRecipe>> optional = level.getServer().getRecipeManager().getRecipeFor(Registration.CUSTOM_CRAFTER_RECIPE_TYPE.get(),craftingInput,level,recipe);
        if (optional.isPresent()) {
            RecipeHolder<CustomCrafterRecipe> recipeHolder = optional.get();
            CustomCrafterRecipe customCrafterRecipe = recipeHolder.value();
            ItemStack itemStack1 = customCrafterRecipe.assemble(craftingInput,level.registryAccess());
            if (itemStack1.isItemEnabled(level.enabledFeatures())) {
                itemStack = itemStack1;
            }
        }

        resultContainer.setItem(0,itemStack);
        menu.setRemoteSlot(0,itemStack);
        serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), RESULT_SLOT_INDEX, itemStack));
    }

    private List<Slot> getGridSlots(int size) {
        return this.slots.subList(TE_INVENTORY_FIRST_SLOT_INDEX,TE_INVENTORY_FIRST_SLOT_INDEX + size);
    }

    private Slot getExtraSlot() {
        return this.slots.get(MODIFICATION_SLOT_INDEX);
    }

    private Slot getResultSlot() {
        return this.slots.get(RESULT_SLOT_INDEX);
    }

    private Player getPlayer() {
        return this.player;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player,this.container));
    }
}
