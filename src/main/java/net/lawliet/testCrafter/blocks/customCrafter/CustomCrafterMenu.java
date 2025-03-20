package net.lawliet.testCrafter.blocks.customCrafter;

import net.lawliet.testCrafter.Registration;
import net.lawliet.testCrafter.gui.ResultSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomCrafterMenu extends AbstractContainerMenu{
    private final ContainerLevelAccess access;
//    private final Player player;
    protected Container container;

    /*
     CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
     must assign a slot number to each of the slots used by the GUI.
     For this container, we can see both the tile inventory's slots and the player inventory slots and the hotbar.
     Each time we add a Slot to the container, it automatically increases the slotIndex, which means
      0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
      9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
      36 - 43 = TileInventory slots, which map to our TileEntity slot numbers 0 - 7)
     */
    protected static final int HOTBAR_SLOT_COUNT = 9;
    protected static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    protected static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    protected static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    protected static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    protected static final int VANILLA_FIRST_SLOT_INDEX = 0;
    protected static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int GRID_WIDTH = 3;
    private static final int GRID_HEIGHT = 2;
    private static final int MODIFICATION_SLOT_COUNT = 1;
    private static final int MODIFICATION_SLOT_INDEX = TE_INVENTORY_FIRST_SLOT_INDEX + GRID_HEIGHT * GRID_WIDTH + 1;
    protected static final int RESULT_SLOT_COUNT = 1;

    private static final int TE_INVENTORY_SLOT_COUNT = GRID_WIDTH * GRID_HEIGHT + MODIFICATION_SLOT_COUNT + RESULT_SLOT_COUNT;

    public CustomCrafterMenu(int containerId, Inventory playerInventory) {
        this(containerId,playerInventory,ContainerLevelAccess.NULL);
    }

    public CustomCrafterMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(Registration.CUSTOM_CRATER_MENU.get(),containerId);
        this.access = access;
//        this.player = playerInventory.player;
        this.container = new SimpleContainer(8);
        addPlayerInventory(playerInventory);
        addPlayerHotBar(playerInventory);

        addGrid(GRID_HEIGHT,GRID_WIDTH);
        addModificationSlot();
        addResultSlot(playerInventory.player);
    }

    protected void addResultSlot(Player player) {
        this.addSlot(new ResultSlot(player,container,7,134,32));
    }

    protected void addModificationSlot() {
        this.addSlot(new Slot(container, 6, 84,32));
    }

    protected void addGrid(int row, int col) {
        for (int i=0; i < row; ++i) {
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
}
