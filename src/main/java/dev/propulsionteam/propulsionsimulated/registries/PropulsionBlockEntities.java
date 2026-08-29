package dev.propulsionteam.propulsionsimulated.registries;

import dev.propulsionteam.propulsionsimulated.content.heat.burners.liquid.LiquidBurnerBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.heat.burners.solid.SolidBurnerBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.cable.fe.FeCableBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.cable.hub.CableHubBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.cable.relay.CableRelayBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.heat.engine.StirlingEngineBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.platinum.CoralGeneratorBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.platinum.PlatinumFluidTankBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.platinum.PlatinumFluidVesselBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.vector_thruster.creative_vector_thruster.CreativeVectorThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.ion_thruster.IonThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.vector_thruster.liquid_vector_thruster.LiquidVectorThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.thruster.ThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.vector_thruster.VectorThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.tilt_adapter.AdvancedTiltAdapterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.tilt_adapter.TiltAdapterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.thruster.creative_thruster.CreativeThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.redstone_converter.RedstoneConverterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.redstone_transmission.RedstoneTransmissionBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.solid_fuel_thruster.SolidFuelThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.wing.PropulsionCopycatWingBlockEntity;
import dev.propulsionteam.propulsionsimulated.CreatePropulsion;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class PropulsionBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreatePropulsion.ID);

    public static final RegistryObject<BlockEntityType<ThrusterBlockEntity>> THRUSTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("thruster_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new ThrusterBlockEntity(pos, state), PropulsionBlocks.THRUSTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<CreativeThrusterBlockEntity>> CREATIVE_THRUSTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("creative_thruster_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new CreativeThrusterBlockEntity(pos, state), PropulsionBlocks.CREATIVE_THRUSTER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<CreativeVectorThrusterBlockEntity>> CREATIVE_VECTOR_THRUSTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("creative_vector_thruster_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new CreativeVectorThrusterBlockEntity(pos, state), PropulsionBlocks.CREATIVE_VECTOR_THRUSTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<IonThrusterBlockEntity>> ION_THRUSTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("ion_thruster_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> {
                if (state.getBlock() == PropulsionBlocks.VECTOR_THRUSTER_BLOCK.get()) {
                    return new VectorThrusterBlockEntity(pos, state);
                }
                return new IonThrusterBlockEntity(pos, state);
            }, PropulsionBlocks.ION_THRUSTER_BLOCK.get(), PropulsionBlocks.VECTOR_THRUSTER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<LiquidVectorThrusterBlockEntity>> LIQUID_VECTOR_THRUSTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("liquid_vector_thruster_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new LiquidVectorThrusterBlockEntity(pos, state),
                PropulsionBlocks.LIQUID_VECTOR_THRUSTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolidFuelThrusterBlockEntity>> SOLID_FUEL_THRUSTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("solid_fuel_thruster_block_entity",
            () -> BlockEntityType.Builder.of(SolidFuelThrusterBlockEntity::new, PropulsionBlocks.SOLID_FUEL_THRUSTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<RedstoneConverterBlockEntity>> REDSTONE_CONVERTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("redstone_converter_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new RedstoneConverterBlockEntity(pos, state),
                PropulsionBlocks.REDSTONE_CONVERTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<RedstoneTransmissionBlockEntity>> REDSTONE_TRANSMISSION_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("redstone_transmission_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new RedstoneTransmissionBlockEntity(pos, state), PropulsionBlocks.REDSTONE_TRANSMISSION_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<SolidBurnerBlockEntity>> SOLID_BURNER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("solid_burner_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new SolidBurnerBlockEntity(pos, state), PropulsionBlocks.SOLID_BURNER.get()).build(null));

    public static final RegistryObject<BlockEntityType<LiquidBurnerBlockEntity>> LIQUID_BURNER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("liquid_burner_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new LiquidBurnerBlockEntity(pos, state), PropulsionBlocks.LIQUID_BURNER.get()).build(null));

    public static final RegistryObject<BlockEntityType<StirlingEngineBlockEntity>> STIRLING_ENGINE_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("stirling_engine_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new StirlingEngineBlockEntity(pos, state), PropulsionBlocks.STIRLING_ENGINE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<TiltAdapterBlockEntity>> TILT_ADAPTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("tilt_adapter_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new TiltAdapterBlockEntity(pos, state), PropulsionBlocks.TILT_ADAPTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<AdvancedTiltAdapterBlockEntity>> ADVANCED_TILT_ADAPTER_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("advanced_tilt_adapter_block_entity",
            () -> BlockEntityType.Builder.of(AdvancedTiltAdapterBlockEntity::new, PropulsionBlocks.ADVANCED_TILT_ADAPTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<PropulsionCopycatWingBlockEntity>> COPYCAT_WING_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("copycat_wing_block_entity",
            () -> BlockEntityType.Builder.of(
                (pos, state) -> new PropulsionCopycatWingBlockEntity(pos, state),
                PropulsionBlocks.COPYCAT_WING.get(),
                PropulsionBlocks.COPYCAT_WING_8.get(),
                PropulsionBlocks.COPYCAT_WING_12.get()
            ).build(null));
    public static final RegistryObject<BlockEntityType<CoralGeneratorBlockEntity>> CORAL_GENERATOR_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("coral_generator_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new CoralGeneratorBlockEntity(pos, state), PropulsionBlocks.CORAL_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<PlatinumFluidTankBlockEntity>> PLATINUM_FLUID_TANK_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("platinum_fluid_tank_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new PlatinumFluidTankBlockEntity(pos, state), PropulsionBlocks.PLATINUM_FLUID_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<PlatinumFluidVesselBlockEntity>> PLATINUM_FLUID_VESSEL_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("platinum_fluid_vessel_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new PlatinumFluidVesselBlockEntity(pos, state), PropulsionBlocks.PLATINUM_FLUID_VESSEL.get()).build(null));
    public static final RegistryObject<BlockEntityType<FeCableBlockEntity>> FE_CABLE_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("fe_cable_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new FeCableBlockEntity(pos, state), PropulsionBlocks.FE_CABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CableHubBlockEntity>> CABLE_HUB_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("cable_hub_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new CableHubBlockEntity(pos, state), PropulsionBlocks.CABLE_HUB.get()).build(null));
    public static final RegistryObject<BlockEntityType<CableRelayBlockEntity>> CABLE_RELAY_BLOCK_ENTITY =
        BLOCK_ENTITY_TYPES.register("cable_relay_block_entity",
            () -> BlockEntityType.Builder.of((pos, state) -> new CableRelayBlockEntity(pos, state), PropulsionBlocks.CABLE_RELAY.get()).build(null));

    public static void register(IEventBus modBus) {
        BLOCK_ENTITY_TYPES.register(modBus);
    }
}
