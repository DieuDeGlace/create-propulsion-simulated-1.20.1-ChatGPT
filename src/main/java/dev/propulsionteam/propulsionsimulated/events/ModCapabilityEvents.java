package dev.propulsionteam.propulsionsimulated.events;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import dev.propulsionteam.propulsionsimulated.content.cable.fe.FeCableBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.cable.hub.CableHubBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.heat.IHeatConsumer;
import dev.propulsionteam.propulsionsimulated.content.heat.IHeatSource;
import dev.propulsionteam.propulsionsimulated.content.heat.burners.liquid.LiquidBurnerBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.heat.burners.liquid.PassthroughFluidHandler;
import dev.propulsionteam.propulsionsimulated.content.heat.burners.solid.SolidBurnerBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.platinum.CoralGeneratorBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.ion_thruster.IonThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.solid_fuel_thruster.SolidFuelThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.thruster.ThrusterBlockEntity;
import dev.propulsionteam.propulsionsimulated.content.thruster.vector_thruster.liquid_vector_thruster.LiquidVectorThrusterBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/** Forge 1.20.1 bridge for upstream's NeoForge block capability callbacks. */
public final class ModCapabilityEvents {
    private ModCapabilityEvents() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IHeatSource.class);
        event.register(IHeatConsumer.class);
    }

    public static void attachBlockEntityCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        if (!hasPropulsionCapability(event.getObject())) {
            return;
        }

        BlockEntityCapabilityProvider provider = new BlockEntityCapabilityProvider(event.getObject());
        event.addCapability(CreatePropulsion.loc("block_entity_capabilities"), provider);
        event.addListener(provider::invalidate);
    }

    private static boolean hasPropulsionCapability(BlockEntity blockEntity) {
        return blockEntity instanceof ThrusterBlockEntity
                || blockEntity instanceof LiquidVectorThrusterBlockEntity
                || blockEntity instanceof LiquidBurnerBlockEntity
                || blockEntity instanceof SolidBurnerBlockEntity
                || blockEntity instanceof SolidFuelThrusterBlockEntity
                || blockEntity instanceof IonThrusterBlockEntity
                || blockEntity instanceof CoralGeneratorBlockEntity
                || blockEntity instanceof CableHubBlockEntity
                || blockEntity instanceof FeCableBlockEntity;
    }

    private static final class BlockEntityCapabilityProvider implements ICapabilityProvider {
        private final BlockEntity blockEntity;
        private final Map<CapabilityKey, LazyOptional<?>> issuedCapabilities = new HashMap<>();

        private BlockEntityCapabilityProvider(BlockEntity blockEntity) {
            this.blockEntity = blockEntity;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability,
                                                          @Nullable Direction side) {
            CapabilityKey key = new CapabilityKey(capability, side);
            LazyOptional<?> cached = issuedCapabilities.get(key);
            if (cached != null) {
                return cached.cast();
            }

            Object handler = findHandler(capability, side);
            if (handler == null) {
                return LazyOptional.empty();
            }

            LazyOptional<T> result = LazyOptional.of(() -> (T) handler);
            issuedCapabilities.put(key, result);
            return result;
        }

        @Nullable
        private Object findHandler(Capability<?> capability, @Nullable Direction side) {
            if (capability == ForgeCapabilities.FLUID_HANDLER) {
                if (blockEntity instanceof ThrusterBlockEntity thruster) {
                    return thruster.getFluidHandler(side);
                }
                if (blockEntity instanceof LiquidVectorThrusterBlockEntity thruster) {
                    return thruster.getFluidHandler(side);
                }
                if (blockEntity instanceof LiquidBurnerBlockEntity burner) {
                    IFluidHandler primary = burner.getPrimaryFluidHandler();
                    if (primary == null) {
                        return null;
                    }
                    return side == null ? primary : new PassthroughFluidHandler(burner, side);
                }
                if (blockEntity instanceof CoralGeneratorBlockEntity generator) {
                    return generator.getFluidHandler(side);
                }
            }

            if (capability == ForgeCapabilities.ITEM_HANDLER) {
                if (blockEntity instanceof SolidBurnerBlockEntity burner) {
                    IItemHandler handler = burner.getItemHandler(side);
                    return handler;
                }
                if (blockEntity instanceof SolidFuelThrusterBlockEntity thruster) {
                    return thruster.getItemHandler(side);
                }
            }

            if (capability == ForgeCapabilities.ENERGY) {
                if (blockEntity instanceof IonThrusterBlockEntity thruster) {
                    return thruster.getEnergyHandler(side);
                }
                if (blockEntity instanceof CoralGeneratorBlockEntity generator) {
                    return generator.getEnergyHandler(side);
                }
                if (blockEntity instanceof CableHubBlockEntity hub) {
                    return hub.getEnergyHandler(side);
                }
                if (blockEntity instanceof FeCableBlockEntity cable) {
                    return cable.getEnergyHandler(side);
                }
            }

            return null;
        }

        private void invalidate() {
            issuedCapabilities.values().forEach(LazyOptional::invalidate);
            issuedCapabilities.clear();
        }

        private record CapabilityKey(Capability<?> capability, @Nullable Direction side) {
        }
    }
}
