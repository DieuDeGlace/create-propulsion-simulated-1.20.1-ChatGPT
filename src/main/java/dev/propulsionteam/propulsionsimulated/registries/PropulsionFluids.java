package dev.propulsionteam.propulsionsimulated.registries;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class PropulsionFluids {
    private static final String TURPENTINE_DESCRIPTION = "fluid." + CreatePropulsion.ID + ".turpentine";
    private static final String CORAL_DESCRIPTION = "fluid." + CreatePropulsion.ID + ".coral";

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, CreatePropulsion.ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, CreatePropulsion.ID);

    public static final RegistryObject<FluidType> TURPENTINE_TYPE = FLUID_TYPES.register("turpentine_type",
            () -> coloredFluidType(TURPENTINE_DESCRIPTION, 500, 1000,
                    new ResourceLocation("minecraft", "block/water_still"),
                    new ResourceLocation("minecraft", "block/water_flow"), 0xFFD69E49));
    public static final RegistryObject<FluidType> CORAL_TYPE = FLUID_TYPES.register("coral_type",
            () -> coloredFluidType(CORAL_DESCRIPTION, 1000, 1200,
                    CreatePropulsion.loc("block/coral_still"), CreatePropulsion.loc("block/coral_flow"), 0xFFFFFFFF));
    public static final RegistryObject<FluidType> OXIDIZER_TYPE = FLUID_TYPES.register("oxidizer_type",
            () -> coloredFluidType("fluid." + CreatePropulsion.ID + ".oxidizer", 700, 500,
                    new ResourceLocation("minecraft", "block/water_still"),
                    new ResourceLocation("minecraft", "block/water_flow"), 0xFF88CCFF));

    public static final RegistryObject<ForgeFlowingFluid.Source> TURPENTINE = FLUIDS.register("turpentine",
            () -> new ProtectedFlowingFluid.Source(turpentineProperties()));
    public static final RegistryObject<ForgeFlowingFluid.Flowing> FLOWING_TURPENTINE = FLUIDS.register("flowing_turpentine",
            () -> new ProtectedFlowingFluid.Flowing(turpentineProperties()));
    public static final RegistryObject<ForgeFlowingFluid.Source> CORAL = FLUIDS.register("coral",
            () -> new ProtectedFlowingFluid.Source(coralProperties()));
    public static final RegistryObject<ForgeFlowingFluid.Flowing> FLOWING_CORAL = FLUIDS.register("flowing_coral",
            () -> new ProtectedFlowingFluid.Flowing(coralProperties()));
    public static final RegistryObject<ForgeFlowingFluid.Source> OXIDIZER = FLUIDS.register("oxidizer",
            () -> new ProtectedFlowingFluid.Source(oxidizerProperties()));
    public static final RegistryObject<ForgeFlowingFluid.Flowing> FLOWING_OXIDIZER = FLUIDS.register("flowing_oxidizer",
            () -> new ProtectedFlowingFluid.Flowing(oxidizerProperties()));

    public static final RegistryObject<LiquidBlock> TURPENTINE_BLOCK = PropulsionBlocks.BLOCKS.register("turpentine",
            () -> new LiquidBlock(TURPENTINE, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> CORAL_BLOCK = PropulsionBlocks.BLOCKS.register("coral",
            () -> new LiquidBlock(CORAL, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));
    public static final RegistryObject<LiquidBlock> OXIDIZER_BLOCK = PropulsionBlocks.BLOCKS.register("oxidizer",
            () -> new LiquidBlock(OXIDIZER, BlockBehaviour.Properties.copy(Blocks.WATER).noLootTable()));

    public static void register(IEventBus modBus) {
        FLUID_TYPES.register(modBus);
        FLUIDS.register(modBus);
    }

    private static ForgeFlowingFluid.Properties turpentineProperties() {
        return new ForgeFlowingFluid.Properties(
                TURPENTINE_TYPE,
                TURPENTINE,
                FLOWING_TURPENTINE
        ).bucket(PropulsionItems.TURPENTINE_BUCKET)
                .block(TURPENTINE_BLOCK)
                .levelDecreasePerBlock(1)
                .tickRate(7)
                .slopeFindDistance(3)
                .explosionResistance(100f);
    }
    private static ForgeFlowingFluid.Properties coralProperties() {
        return new ForgeFlowingFluid.Properties(
                CORAL_TYPE,
                CORAL,
                FLOWING_CORAL
        ).bucket(PropulsionItems.CORAL_BUCKET)
                .block(CORAL_BLOCK)
                .levelDecreasePerBlock(1)
                .tickRate(7)
                .slopeFindDistance(3)
                .explosionResistance(100f);
    }
    private static ForgeFlowingFluid.Properties oxidizerProperties() {
        return new ForgeFlowingFluid.Properties(
                OXIDIZER_TYPE,
                OXIDIZER,
                FLOWING_OXIDIZER
        ).bucket(PropulsionItems.OXIDIZER_BUCKET)
                .block(OXIDIZER_BLOCK)
                .levelDecreasePerBlock(1)
                .tickRate(5)
                .slopeFindDistance(3)
                .explosionResistance(100f);
    }

    private static FluidType coloredFluidType(String descriptionId, int density, int viscosity,
                                               ResourceLocation stillTexture, ResourceLocation flowingTexture,
                                               int tintColor) {
        return new FluidType(FluidType.Properties.create()
                .descriptionId(descriptionId)
                .density(density)
                .viscosity(viscosity)) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override
                    public ResourceLocation getStillTexture() {
                        return stillTexture;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return flowingTexture;
                    }

                    @Override
                    public int getTintColor() {
                        return tintColor;
                    }

                    @Override
                    public int getTintColor(net.minecraft.world.level.material.FluidState state,
                                            net.minecraft.world.level.BlockAndTintGetter getter,
                                            net.minecraft.core.BlockPos pos) {
                        return tintColor;
                    }

                    @Override
                    public int getTintColor(net.minecraftforge.fluids.FluidStack stack) {
                        return tintColor;
                    }
                });
            }
        };
    }
}
