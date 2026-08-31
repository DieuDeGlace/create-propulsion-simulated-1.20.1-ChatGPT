package dev.propulsionteam.propulsionsimulated.events;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import dev.propulsionteam.propulsionsimulated.PropulsionConfig;
import dev.propulsionteam.propulsionsimulated.content.platinum.CoralGeneratorFuelManager;
import dev.propulsionteam.propulsionsimulated.content.thruster.SolidThrusterFuelManager;
import dev.propulsionteam.propulsionsimulated.content.thruster.ThrusterFuelManager;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid = CreatePropulsion.ID, bus = EventBusSubscriber.Bus.MOD)
public final class PropulsionModConfigEvents {

    private PropulsionModConfigEvents() {}

    @SubscribeEvent
    public static void onCommonConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() != PropulsionConfig.COMMON_SPEC) {
            return;
        }
        ThrusterFuelManager.rebuildThrusterFuelsAfterCommonConfigReload();
        SolidThrusterFuelManager.rebuildAfterCommonConfigReload();
        CoralGeneratorFuelManager.rebuildCoralFuelsAfterCommonConfigReload();
    }
}
