package dev.propulsionteam.propulsionsimulated.registries;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class PropulsionSoundEvents {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS =
        DeferredRegister.create(Registries.SOUND_EVENT, CreatePropulsion.ID);

    public static final RegistryObject<SoundEvent> THRUSTER_STARTUP = register("thruster_startup");
    public static final RegistryObject<SoundEvent> THRUSTER_LOOP = SOUND_EVENTS.register(
        "thruster_loop",
        () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreatePropulsion.ID, "thruster_loop"))
    );
    public static final RegistryObject<SoundEvent> THRUSTER_OFF = register("thruster_off");
    public static final RegistryObject<SoundEvent> ION_THRUSTER_STARTUP = register("ion_thruster_startup");
    public static final RegistryObject<SoundEvent> ION_THRUSTER_LOOP = register("ion_thruster_loop");
    public static final RegistryObject<SoundEvent> ION_THRUSTER_OFF = register("ion_thruster_off");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(
            name,
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreatePropulsion.ID, name))
        );
    }

    public static void register(IEventBus modBus) {
        SOUND_EVENTS.register(modBus);
    }
}
