package dev.propulsionteam.propulsionsimulated.registries;

import com.simibubi.create.api.stress.BlockStressValues;
import net.createmod.catnip.config.ConfigBase;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

import javax.annotation.Nonnull;

@SuppressWarnings("removal")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class PropulsionDefaultStress extends ConfigBase {
    private static final int VERSION = 1;
    public static final PropulsionDefaultStress INSTANCE = new PropulsionDefaultStress();

    private static final Map<ResourceLocation, Double> DEFAULT_IMPACTS = new HashMap<>();
    private static final Map<ResourceLocation, Double> INTERNAL_IMPACTS = new HashMap<>();

    protected final Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> impacts = new HashMap<>();

    public static void init(ForgeConfigSpec spec) {
        INSTANCE.specification = spec;
        BlockStressValues.IMPACTS.registerProvider(INSTANCE::getImpact);
    }

    @Override
    public String getName() {
        return "stress-values.v" + VERSION;
    }

    @Override
    public void registerAll(@Nonnull ForgeConfigSpec.Builder builder) {
        builder.push("Stress impacts");

        //Internal impacts are not added into spec as they must not be modified!
        DEFAULT_IMPACTS.forEach((id, value) ->
            impacts.put(id, builder.define(id.getPath(), value))
        );

        builder.pop();
    }

    @Nullable
    public DoubleSupplier getImpact(Block block) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);

        //Check config
        ForgeConfigSpec.ConfigValue<Double> configValue = this.impacts.get(id);
        if (configValue != null) return configValue::get;

        //Check internal
        Double internalValue = INTERNAL_IMPACTS.get(id);
        if (internalValue != null) return () -> internalValue;

        return null;
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        if (INSTANCE.specification == event.getConfig().getSpec())
            INSTANCE.onLoad();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        if (INSTANCE.specification == event.getConfig().getSpec())
            INSTANCE.onReload();
    }

    public static void setImpact(ResourceLocation id, double impact, boolean addToConfig) {
        if (addToConfig) {
            DEFAULT_IMPACTS.put(id, impact);
        } else {
            INTERNAL_IMPACTS.put(id, impact);
        }
    }
}