package dev.propulsionteam.propulsionsimulated.network;

import dev.propulsionteam.propulsionsimulated.content.thruster.FluidThrusterProperties;
import dev.propulsionteam.propulsionsimulated.content.thruster.ItemThrusterProperties;
import dev.propulsionteam.propulsionsimulated.content.thruster.ThrusterParticleType;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FuelSyncPacketTest {
    @Test
    void fluidFuelPacketRoundTripsPropertiesRemovalsAndEfficiencyOverrides() throws Exception {
        ResourceLocation waterId = new ResourceLocation("minecraft", "water");
        ResourceLocation lavaId = new ResourceLocation("minecraft", "lava");
        FluidThrusterProperties properties = new FluidThrusterProperties(
            1.75f,
            0.4f,
            ThrusterParticleType.PLASMA,
            List.of(new ResourceLocation("createpropulsion", "textures/particle/test")),
            0x12ABEF,
            true
        );

        Map<ResourceLocation, FluidThrusterProperties> fuelMap = Map.of(waterId, properties);
        Set<ResourceLocation> removed = Set.of(lavaId);
        Map<ResourceLocation, Float> efficiencies = Map.of(waterId, 0.625f);
        Constructor<SyncThrusterFuelsPacket> constructor = SyncThrusterFuelsPacket.class
            .getDeclaredConstructor(Map.class, Set.class, Map.class);
        constructor.setAccessible(true);
        SyncThrusterFuelsPacket outbound = constructor.newInstance(fuelMap, removed, efficiencies);
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        outbound.encode(buffer);
        SyncThrusterFuelsPacket inbound = SyncThrusterFuelsPacket.decode(buffer);

        assertFalse(buffer.isReadable());
        assertEquals(fuelMap, field(inbound, "fuelMap"));
        assertEquals(removed, field(inbound, "removedFuelIds"));
        assertEquals(efficiencies, field(inbound, "efficiencyOverrides"));
    }

    @Test
    void solidFuelPacketRoundTripsPropertiesAndRemovals() throws Exception {
        ResourceLocation coalId = new ResourceLocation("minecraft", "coal");
        ResourceLocation charcoalId = new ResourceLocation("minecraft", "charcoal");
        ItemThrusterProperties properties = new ItemThrusterProperties(
            1.2f,
            0.8f,
            ThrusterParticleType.PLUME,
            List.of(new ResourceLocation("createpropulsion", "textures/particle/solid_test")),
            0xCC8844,
            true
        );

        Map<ResourceLocation, ItemThrusterProperties> fuelMap = Map.of(coalId, properties);
        Set<ResourceLocation> removed = Set.of(charcoalId);
        Constructor<SyncSolidThrusterFuelsPacket> constructor = SyncSolidThrusterFuelsPacket.class
            .getDeclaredConstructor(Map.class, Set.class);
        constructor.setAccessible(true);
        SyncSolidThrusterFuelsPacket outbound = constructor.newInstance(fuelMap, removed);
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        outbound.encode(buffer);
        SyncSolidThrusterFuelsPacket inbound = SyncSolidThrusterFuelsPacket.decode(buffer);

        assertFalse(buffer.isReadable());
        assertEquals(fuelMap, field(inbound, "fuelMap"));
        assertEquals(removed, field(inbound, "removedFuelIds"));
    }

    private static Object field(Object target, String name) throws ReflectiveOperationException {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(target);
    }
}
