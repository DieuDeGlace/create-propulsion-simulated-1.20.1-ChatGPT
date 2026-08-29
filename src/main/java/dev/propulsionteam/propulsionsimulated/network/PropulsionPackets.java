package dev.propulsionteam.propulsionsimulated.network;

import dev.propulsionteam.propulsionsimulated.CreatePropulsion;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Supplier;

public final class PropulsionPackets {
    private static final String PROTOCOL_VERSION = "2";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            CreatePropulsion.loc("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);
    private static int nextPacketId;

    private PropulsionPackets() {
    }

    public static void register() {
        CHANNEL.registerMessage(
                nextPacketId++,
                SyncThrusterFuelsPacket.class,
                SyncThrusterFuelsPacket::encode,
                SyncThrusterFuelsPacket::decode,
                PropulsionPackets::handleThrusterFuels,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(
                nextPacketId++,
                SyncSolidThrusterFuelsPacket.class,
                SyncSolidThrusterFuelsPacket::encode,
                SyncSolidThrusterFuelsPacket::decode,
                PropulsionPackets::handleSolidThrusterFuels,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    private static void handleThrusterFuels(SyncThrusterFuelsPacket packet,
                                            Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(packet::handle);
        context.setPacketHandled(true);
    }

    private static void handleSolidThrusterFuels(SyncSolidThrusterFuelsPacket packet,
                                                 Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(packet::handle);
        context.setPacketHandled(true);
    }

    public static void sendToPlayer(Object message, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static void sendToAll(Object message) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }

    public static void sendToTracking(Object message, LevelChunk chunk) {
        CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), message);
    }

    public static void sendToServer(Object message) {
        CHANNEL.sendToServer(message);
    }
}
