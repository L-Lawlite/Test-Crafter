package net.lawliet.testCrafter.blocks.customCrafter;

import net.lawliet.testCrafter.Registration;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.IContainerFactory;

public class CustomCrafterMenu extends AbstractContainerMenu{
    public static final int RESULT_SLOT = 1;
    private static final int MODIFICATION_SLOT = 1;
    private final int width = 3;
    private final int height = 2;

    private final ContainerLevelAccess access;
    private final Player player;
//    private boolean placingRecipe;

    public CustomCrafterMenu(int containerId, Inventory playerInventory) {
        this(containerId,playerInventory,ContainerLevelAccess.NULL);
    }

    public CustomCrafterMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(Registration.CUSTOM_CRATER_MENU.get(),containerId);
        this.access = access;
        this.player = playerInventory.player;

    }




    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access,player,Registration.CUSTOM_CRAFTER_BLOCK.get());
    }
}
