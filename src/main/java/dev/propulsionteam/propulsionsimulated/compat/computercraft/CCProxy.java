package dev.propulsionteam.propulsionsimulated.compat.computercraft;

import java.util.function.Function;

import com.simibubi.create.compat.Mods;
import com.simibubi.create.compat.computercraft.AbstractComputerBehaviour;
import com.simibubi.create.compat.computercraft.FallbackComputerBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dan200.computercraft.api.ForgeComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraftforge.common.util.LazyOptional;

public class CCProxy {
    public static void register() {
        secondaryFactory = FallbackComputerBehaviour::new;
        Mods.COMPUTERCRAFT.executeIfInstalled(() -> CCProxy::registerWithDependency);
    }

    private static void registerWithDependency() {
        primaryFactory = ComputerBehaviour::new;
        ForgeComputerCraftAPI.registerPeripheralProvider((level, pos, side) -> {
            AbstractComputerBehaviour behaviour = BlockEntityBehaviour.get(
                level, pos, AbstractComputerBehaviour.TYPE);
            if (behaviour instanceof ComputerBehaviour computerBehaviour) {
                return computerBehaviour.<IPeripheral>getPeripheralCapability();
            }
            return LazyOptional.empty();
        });
    }

    private static Function<SmartBlockEntity, ? extends AbstractComputerBehaviour> secondaryFactory;
    private static Function<SmartBlockEntity, ? extends AbstractComputerBehaviour> primaryFactory;

    public static AbstractComputerBehaviour behaviour(SmartBlockEntity blockEntity) {
        if (primaryFactory == null) {
            secondaryFactory = FallbackComputerBehaviour::new;
            return secondaryFactory.apply(blockEntity);
        }
        return primaryFactory.apply(blockEntity);
    }
}
