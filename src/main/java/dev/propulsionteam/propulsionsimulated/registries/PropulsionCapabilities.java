package dev.propulsionteam.propulsionsimulated.registries;

import dev.propulsionteam.propulsionsimulated.content.heat.IHeatConsumer;
import dev.propulsionteam.propulsionsimulated.content.heat.IHeatSource;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public final class PropulsionCapabilities {
    private PropulsionCapabilities() {
    }

    public static final Capability<IHeatSource> HEAT_SOURCE =
            CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IHeatConsumer> HEAT_CONSUMER =
            CapabilityManager.get(new CapabilityToken<>() {});
}
