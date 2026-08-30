package dev.propulsionteam.propulsionsimulated.registries;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import dev.propulsionteam.propulsionsimulated.assemblerstick.item.ModItems;
import dev.simulated_team.simulated.client.sections.SimulatedSection;
import dev.simulated_team.simulated.index.SimResourceManagers;
import dev.simulated_team.simulated.registrate.SimulatedRegistrate;
import dev.simulated_team.simulated.registrate.simulated_tab.CreativeTabItemTransforms;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class PropulsionCreativeTab {
    private static final int ITEMS_PER_ROW = 9;

    public static final ResourceLocation MAIN_SECTION = new ResourceLocation(CreatePropulsion.ID, "propulsion_main");
    public static final ResourceLocation TOOLS_SECTION = new ResourceLocation(CreatePropulsion.ID, "propulsion_tools");
    public static final List<ResourceLocation> SECTIONS = List.of(MAIN_SECTION, TOOLS_SECTION);
    public static final Map<ResourceLocation, Integer> SECTION_ROWS = new HashMap<>();

    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreatePropulsion.ID);
    private static final List<Supplier<Item>> TAB_ITEMS = new ArrayList<>();
    private static final List<Integer> SECTION_ITEM_COUNTS = new ArrayList<>();

    public static final CreativeModeTab TAB = CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.createpropulsion.base"))
            .icon(() -> new ItemStack(PropulsionBlocks.THRUSTER_BLOCK.get()))
            .displayItems((parameters, output) -> buildContents(output))
            .build();

    private static boolean sectionsInitialized = false;

    public static void register(IEventBus modBus) {
        TABS.register("main_tab", () -> TAB);
        TABS.register(modBus);
        registerSections();
    }

    private static synchronized void registerSections() {
        if (sectionsInitialized) {
            return;
        }

        registerSectionItem(MAIN_SECTION, "thruster", () -> PropulsionBlocks.THRUSTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "creative_thruster", () -> PropulsionBlocks.CREATIVE_THRUSTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "ion_thruster", () -> PropulsionBlocks.ION_THRUSTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "vector_thruster", () -> PropulsionBlocks.VECTOR_THRUSTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "creative_vector_thruster", () -> PropulsionBlocks.CREATIVE_VECTOR_THRUSTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "liquid_vector_thruster", () -> PropulsionBlocks.LIQUID_VECTOR_THRUSTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "solid_fuel_thruster", () -> PropulsionBlocks.SOLID_FUEL_THRUSTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "redstone_transmission", () -> PropulsionBlocks.REDSTONE_TRANSMISSION_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "redstone_converter", () -> PropulsionBlocks.REDSTONE_CONVERTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "turpentine_bucket", () -> PropulsionItems.TURPENTINE_BUCKET.get());
        registerSectionItem(MAIN_SECTION, "pine_resin", () -> PropulsionItems.PINE_RESIN.get());
        registerSectionItem(MAIN_SECTION, "wing", () -> PropulsionBlocks.WING_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "tempered_wing", () -> PropulsionBlocks.TEMPERED_WING_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "copycat_wing", () -> PropulsionBlocks.COPYCAT_WING.get().asItem());
        registerSectionItem(MAIN_SECTION, "solid_burner", () -> PropulsionBlocks.SOLID_BURNER.get().asItem());
        registerSectionItem(MAIN_SECTION, "liquid_burner", () -> PropulsionBlocks.LIQUID_BURNER.get().asItem());
        registerSectionItem(MAIN_SECTION, "stirling_engine", () -> PropulsionBlocks.STIRLING_ENGINE_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "tilt_adapter", () -> PropulsionBlocks.TILT_ADAPTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "advanced_tilt_adapter", () -> PropulsionBlocks.ADVANCED_TILT_ADAPTER_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "platinum_ore", () -> PropulsionBlocks.PLATINUM_ORE.get().asItem());
        registerSectionItem(MAIN_SECTION, "deepslate_platinum_ore", () -> PropulsionBlocks.DEEPSLATE_PLATINUM_ORE.get().asItem());
        registerSectionItem(MAIN_SECTION, "platinum_block", () -> PropulsionBlocks.PLATINUM_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "raw_platinum_block", () -> PropulsionBlocks.RAW_PLATINUM_BLOCK.get().asItem());
        registerSectionItem(MAIN_SECTION, "platinum_casing", () -> PropulsionBlocks.PLATINUM_CASING.get().asItem());
        registerSectionItem(MAIN_SECTION, "platinum_fluid_tank", () -> PropulsionBlocks.PLATINUM_FLUID_TANK.get().asItem());
        registerSectionItem(MAIN_SECTION, "platinum_fluid_vessel", () -> PropulsionBlocks.PLATINUM_FLUID_VESSEL.get().asItem());
        registerSectionItem(MAIN_SECTION, "coral_generator", () -> PropulsionBlocks.CORAL_GENERATOR.get().asItem());
        registerSectionItem(MAIN_SECTION, "platinum_ingot", () -> PropulsionItems.PLATINUM_INGOT.get());
        registerSectionItem(MAIN_SECTION, "platinum_nugget", () -> PropulsionItems.PLATINUM_NUGGET.get());
        registerSectionItem(MAIN_SECTION, "platinum_sheet", () -> PropulsionItems.PLATINUM_SHEET.get());
        registerSectionItem(MAIN_SECTION, "raw_platinum", () -> PropulsionItems.RAW_PLATINUM.get());
        registerSectionItem(MAIN_SECTION, "coral_bucket", () -> PropulsionItems.CORAL_BUCKET.get());
        registerSectionItem(MAIN_SECTION, "oxidizer_bucket", () -> PropulsionItems.OXIDIZER_BUCKET.get());
        registerSectionItem(MAIN_SECTION, "cable", () -> PropulsionBlocks.FE_CABLE.get().asItem());
        registerSectionItem(MAIN_SECTION, "cable_relay", () -> PropulsionBlocks.CABLE_RELAY.get().asItem());

        registerSectionItem(TOOLS_SECTION, "assembler_stick", () -> ModItems.ASSEMBLER_STICK.get());
        registerSectionItem(TOOLS_SECTION, "auto_glue", () -> ModItems.AUTO_GLUE.get());
        registerSectionItem(TOOLS_SECTION, "glued_contraption_mover", () -> ModItems.GLUED_CONTRAPTION_MOVER.get());
        registerSectionItem(TOOLS_SECTION, "glued_contraption_cloner", () -> ModItems.GLUED_CONTRAPTION_CLONER.get());
        registerSectionItem(TOOLS_SECTION, "contraption_remover", () -> ModItems.CONTRAPTION_REMOVER.get());

        sectionsInitialized = true;
    }

    private static void registerSectionItem(ResourceLocation sectionId, String itemPath, Supplier<Item> itemSupplier) {
        TAB_ITEMS.add(itemSupplier);
        SimulatedRegistrate.ITEM_TO_SECTION.put(new ResourceLocation(CreatePropulsion.ID, itemPath), sectionId);
    }

    private static void buildContents(CreativeModeTab.Output output) {
        Map<SimulatedSection, List<ItemStack>> sectionItems = new HashMap<>();

        for (Supplier<Item> itemSupplier : TAB_ITEMS) {
            Item item = itemSupplier.get();
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
            ResourceLocation sectionId = SimulatedRegistrate.ITEM_TO_SECTION.get(itemId);
            if (sectionId == null || !SECTIONS.contains(sectionId)) {
                continue;
            }

            SimulatedSection section = SimResourceManagers.SIMULATED_SECTION.get(sectionId);
            if (section != null) {
                sectionItems.computeIfAbsent(section, ignored -> new ArrayList<>())
                        .add(item.getDefaultInstance());
            }
        }

        SECTION_ROWS.clear();
        SECTION_ITEM_COUNTS.clear();

        List<SimulatedSection> sortedSections = sectionItems.keySet().stream().sorted().toList();
        int row = 0;
        for (SimulatedSection section : sortedSections) {
            int itemCount = 0;

            for (ItemStack originalStack : sectionItems.get(section)) {
                ItemStack stack = CreativeTabItemTransforms.applyTransform(originalStack);
                if (CreativeTabItemTransforms.VisibilityType.SEARCH_ONLY.has(stack.getItem())) {
                    output.accept(stack, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
                } else if (!CreativeTabItemTransforms.VisibilityType.INVISIBLE.has(stack.getItem())) {
                    output.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    itemCount++;
                }
            }

            ResourceLocation sectionId = SimResourceManagers.SIMULATED_SECTION.getId(section);
            SECTION_ROWS.put(sectionId, row);
            SECTION_ITEM_COUNTS.add(itemCount);
            row += (itemCount + ITEMS_PER_ROW - 1) / ITEMS_PER_ROW + 1;
        }
    }

    public static void padMenuItems(List<ItemStack> items) {
        CreativeTabLayout.padSections(items, SECTION_ITEM_COUNTS, ITEMS_PER_ROW, ItemStack.EMPTY);
    }
}
