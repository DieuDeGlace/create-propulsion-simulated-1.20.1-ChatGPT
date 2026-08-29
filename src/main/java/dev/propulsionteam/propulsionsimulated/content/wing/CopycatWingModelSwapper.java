package dev.propulsionteam.propulsionsimulated.content.wing;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import dev.propulsionteam.propulsionsimulated.registries.PropulsionBlocks;
import com.simibubi.create.foundation.model.ModelSwapper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.ModelEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = CreatePropulsion.ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CopycatWingModelSwapper {
    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        ModelSwapper.swapModels(
            event.getModels(),
            ModelSwapper.getAllBlockStateModelLocations(PropulsionBlocks.COPYCAT_WING.get()),
            CopycatWingModel.create(4)
        );
        ModelSwapper.swapModels(
            event.getModels(),
            ModelSwapper.getAllBlockStateModelLocations(PropulsionBlocks.COPYCAT_WING_8.get()),
            CopycatWingModel.create(8)
        );
        ModelSwapper.swapModels(
            event.getModels(),
            ModelSwapper.getAllBlockStateModelLocations(PropulsionBlocks.COPYCAT_WING_12.get()),
            CopycatWingModel.create(12)
        );
    }
}

