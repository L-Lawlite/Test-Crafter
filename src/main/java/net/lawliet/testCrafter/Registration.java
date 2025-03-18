package net.lawliet.testCrafter;

//import net.lawliet.testCrafter.blockEntities.complexBlock.ComplexBlockEntity;
//import net.lawliet.testCrafter.blockEntities.complexBlock.ComplexBlock;
import net.lawliet.testCrafter.blocks.customCrafter.CustomCrafterBlock;
import net.lawliet.testCrafter.blocks.customCrafter.CustomCrafterMenu;
import net.lawliet.testCrafter.blocks.customCrafter.CustomCrafterScreen;
import net.lawliet.testCrafter.lootItemConditions.ModExistCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.lawliet.testCrafter.copperBlockLike.TestWeatheringCopperFullBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public class Registration {

    public static final DeferredRegister.Blocks BLOCKS;
    public static final DeferredRegister.Items ITEMS;
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;
    public static final DeferredRegister<ResourceLocation> CUSTOM_STATS;
    public static final DeferredRegister<MenuType<?>> MENU_TYPES;
    public static final DeferredRegister<LootItemConditionType> LOOT_ITEM_CONDITION_TYPE;

    //Simple Block
    public static final DeferredBlock<Block> SIMPLE_BLOCK;
    public static final DeferredItem<BlockItem> SIMPLE_BLOCK_ITEM;

//    public static final DeferredBlock<Block> COMPLEX_BLOCK;
//    public static final DeferredItem<BlockItem> COMPLEX_BLOCK_ITEM;
//    public static final Supplier<BlockEntityType<ComplexBlockEntity>> COMPLEX_BLOCK_ENTITY;

    // Custom Crafter
    public static final DeferredBlock<Block> CUSTOM_CRAFTER_BLOCK;
    public static final DeferredItem<BlockItem> CUSTOM_CRAFTER_ITEM;
    public static final DeferredHolder<ResourceLocation,ResourceLocation> INTERACT_WITH_CUSTOM_CRAFTER;
    public static final Supplier<MenuType<CustomCrafterMenu>> CUSTOM_CRATER_MENU;

    //LootItemConditionType
    public static final DeferredHolder<LootItemConditionType,LootItemConditionType> MOD_EXIST;

    //Withering Blocks
    public static final DeferredBlock<Block> TEST_COPPER_BLOCK;
    public static final DeferredBlock<Block> TEST_EXPOSED_COPPER_BLOCK;
    public static final DeferredBlock<Block> TEST_WEATHERED_COPPER_BLOCK;
    public static final DeferredBlock<Block> TEST_OXIDIZED_COPPER_BLOCK;

    public static final DeferredItem<BlockItem> TEST_COPPER_BLOCK_ITEM;
    public static final DeferredItem<BlockItem> TEST_EXPOSED_COPPER_ITEM;
    public static final DeferredItem<BlockItem> TEST_WEATHERED_COPPER_ITEM;
    public static final DeferredItem<BlockItem> TEST_OXIDIZED_COPPER_ITEM;


    // Registries
    static {
        BLOCKS = DeferredRegister.createBlocks(TestCrafter.MODID);
        ITEMS = DeferredRegister.createItems(TestCrafter.MODID);
        BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,TestCrafter.MODID);
        CUSTOM_STATS = DeferredRegister.create(Registries.CUSTOM_STAT,TestCrafter.MODID);
        MENU_TYPES = DeferredRegister.create(Registries.MENU,TestCrafter.MODID);
        LOOT_ITEM_CONDITION_TYPE = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE,TestCrafter.MODID);
    }

    //block registries
    static {
        SIMPLE_BLOCK = BLOCKS.registerSimpleBlock("simple_block",
                BlockBehaviour.Properties.of()
                        .strength(3.5F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.METAL)
                        .randomTicks()
        );
        SIMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("simple_block",SIMPLE_BLOCK);

//        COMPLEX_BLOCK = BLOCKS.registerBlock("complex_block", ComplexBlock::new ,
//                BlockBehaviour.Properties.of()
//                        .strength(3.5F)
//                        .requiresCorrectToolForDrops()
//                        .sound(SoundType.METAL)
//        );
//        COMPLEX_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("complex_block",COMPLEX_BLOCK);
//        COMPLEX_BLOCK_ENTITY = BLOCK_ENTITIES.register("complex_block", () -> new BlockEntityType<>(
//                ComplexBlockEntity::new,
//                COMPLEX_BLOCK.get()
//        ));

        CUSTOM_CRAFTER_BLOCK = BLOCKS.registerBlock("custom_crafter", CustomCrafterBlock::new,
                BlockBehaviour.Properties.of()
                        .strength(3.5F)
                        .sound(SoundType.WOOD)
                );
        CUSTOM_CRAFTER_ITEM = ITEMS.registerSimpleBlockItem("custom_crafter",CUSTOM_CRAFTER_BLOCK);

        TEST_COPPER_BLOCK = BLOCKS.registerBlock("test_copper_block",
                (properties) -> new TestWeatheringCopperFullBlock(WeatheringCopper.WeatherState.UNAFFECTED,properties),
                BlockBehaviour.Properties.of()
                        .requiresCorrectToolForDrops()
                        .strength(3.0F,6.0F)
                        .sound(SoundType.COPPER)
                        .randomTicks()
        );
        TEST_COPPER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("test_copper_block",TEST_COPPER_BLOCK);

        TEST_EXPOSED_COPPER_BLOCK = BLOCKS.registerBlock("test_exposed_copper_block",
                (properties) -> new TestWeatheringCopperFullBlock(WeatheringCopper.WeatherState.EXPOSED,properties),
                BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
        );
        TEST_EXPOSED_COPPER_ITEM = ITEMS.registerSimpleBlockItem("test_exposed_copper_block",TEST_EXPOSED_COPPER_BLOCK);

        TEST_WEATHERED_COPPER_BLOCK = BLOCKS.registerBlock("test_weathered_copper_block",
                (properties) -> new TestWeatheringCopperFullBlock(WeatheringCopper.WeatherState.WEATHERED,properties),
                BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).randomTicks()
        );
        TEST_WEATHERED_COPPER_ITEM = ITEMS.registerSimpleBlockItem("test_weathered_copper_block",TEST_WEATHERED_COPPER_BLOCK);

        TEST_OXIDIZED_COPPER_BLOCK = BLOCKS.registerBlock("test_oxidized_copper_block",
                (properties) -> new TestWeatheringCopperFullBlock(WeatheringCopper.WeatherState.OXIDIZED,properties),
                BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).randomTicks()
        );
        TEST_OXIDIZED_COPPER_ITEM = ITEMS.registerSimpleBlockItem("test_oxidized_copper_block",TEST_OXIDIZED_COPPER_BLOCK);
    }

    //Stats
    static {
        INTERACT_WITH_CUSTOM_CRAFTER =  CUSTOM_STATS.register("interact_with_custom_crafter", Function.identity());
    }

    //Menus
    static {
        CUSTOM_CRATER_MENU = MENU_TYPES.register("custom_crafter_menu", () -> new MenuType<>(CustomCrafterMenu::new, FeatureFlags.DEFAULT_FLAGS));
    }

    //Predicates
    static {
        MOD_EXIST = LOOT_ITEM_CONDITION_TYPE.register("mod_exist",() -> new LootItemConditionType( ModExistCondition.CODEC));
    }

    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CUSTOM_STATS.register(modEventBus);
        MENU_TYPES.register(modEventBus);
        LOOT_ITEM_CONDITION_TYPE.register(modEventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(SIMPLE_BLOCK_ITEM);
//            event.accept(COMPLEX_BLOCK_ITEM);
            event.accept(CUSTOM_CRAFTER_ITEM);
            event.accept(TEST_COPPER_BLOCK_ITEM);
            event.accept(TEST_EXPOSED_COPPER_ITEM);
            event.accept(TEST_WEATHERED_COPPER_ITEM);
            event.accept(TEST_OXIDIZED_COPPER_ITEM);

        }
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Stats.CUSTOM.get(INTERACT_WITH_CUSTOM_CRAFTER.get(),StatFormatter.DEFAULT);
        });
    }

    public static void addCapabilities(RegisterCapabilitiesEvent event) {
//        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,Registration.COMPLEX_BLOCK_ENTITY.get(),(be,direction) -> be.getItemHandler());

    }

    public static void registerScreen(RegisterMenuScreensEvent event) {
        event.register(CUSTOM_CRATER_MENU.get(), CustomCrafterScreen::new);
    }
}
