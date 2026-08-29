package dev.propulsionteam.propulsionsimulated.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Shared 1.20.1 command and packet representation for configurable plume particles. */
public final class VariableParticleDataSerialization {
    private VariableParticleDataSerialization() {
    }

    @FunctionalInterface
    public interface Factory<T extends ParticleOptions> {
        T create(List<ResourceLocation> textures, Integer color, Float size, Float startup,
                 Vec3 inheritedVelocity, float trailCoverage);
    }

    public static <T extends ParticleOptions> ParticleOptions.Deserializer<T> deserializer(Factory<T> factory) {
        return new ParticleOptions.Deserializer<>() {
            @Override
            public T fromCommand(ParticleType<T> type, StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                int textureCount = reader.readInt();
                List<ResourceLocation> textures = new ArrayList<>(Math.max(textureCount, 0));
                for (int i = 0; i < textureCount; i++) {
                    reader.expect(' ');
                    textures.add(ResourceLocation.read(reader));
                }
                reader.expect(' ');
                Integer color = reader.readBoolean() ? readInt(reader) : null;
                reader.expect(' ');
                Float size = reader.readBoolean() ? readFloat(reader) : null;
                reader.expect(' ');
                Float startup = reader.readBoolean() ? readFloat(reader) : null;
                double velocityX = readDouble(reader);
                double velocityY = readDouble(reader);
                double velocityZ = readDouble(reader);
                float coverage = readFloat(reader);
                return factory.create(textures, color, size, startup,
                        new Vec3(velocityX, velocityY, velocityZ), coverage);
            }

            @Override
            public T fromNetwork(ParticleType<T> type, FriendlyByteBuf buffer) {
                State state = read(buffer);
                return factory.create(state.textures(), state.color(), state.size(), state.startup(),
                        state.inheritedVelocity(), state.trailCoverage());
            }
        };
    }

    public static void write(FriendlyByteBuf buffer, List<ResourceLocation> textures, Integer color,
                             Float size, Float startup, Vec3 inheritedVelocity, float trailCoverage) {
        buffer.writeCollection(textures, FriendlyByteBuf::writeResourceLocation);
        buffer.writeBoolean(color != null);
        if (color != null) {
            buffer.writeInt(color);
        }
        buffer.writeBoolean(size != null);
        if (size != null) {
            buffer.writeFloat(size);
        }
        buffer.writeBoolean(startup != null);
        if (startup != null) {
            buffer.writeFloat(startup);
        }
        buffer.writeDouble(inheritedVelocity.x);
        buffer.writeDouble(inheritedVelocity.y);
        buffer.writeDouble(inheritedVelocity.z);
        buffer.writeFloat(trailCoverage);
    }

    public static State read(FriendlyByteBuf buffer) {
        List<ResourceLocation> textures = buffer.readCollection(ArrayList::new, FriendlyByteBuf::readResourceLocation);
        Integer color = buffer.readBoolean() ? buffer.readInt() : null;
        Float size = buffer.readBoolean() ? buffer.readFloat() : null;
        Float startup = buffer.readBoolean() ? buffer.readFloat() : null;
        Vec3 inheritedVelocity = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        return new State(textures, color, size, startup, inheritedVelocity, buffer.readFloat());
    }

    public static String writeToString(String particleId, List<ResourceLocation> textures, Integer color,
                                       Float size, Float startup, Vec3 inheritedVelocity, float trailCoverage) {
        StringBuilder result = new StringBuilder(particleId).append(' ').append(textures.size());
        textures.forEach(texture -> result.append(' ').append(texture));
        appendOptional(result, color);
        appendOptional(result, size);
        appendOptional(result, startup);
        return String.format(Locale.ROOT, "%s %.17g %.17g %.17g %.9g", result,
                inheritedVelocity.x, inheritedVelocity.y, inheritedVelocity.z, trailCoverage);
    }

    private static void appendOptional(StringBuilder builder, Number value) {
        builder.append(' ').append(value != null);
        if (value != null) {
            builder.append(' ').append(value);
        }
    }

    private static int readInt(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        return reader.readInt();
    }

    private static float readFloat(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        return reader.readFloat();
    }

    private static double readDouble(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        return reader.readDouble();
    }

    public record State(List<ResourceLocation> textures, Integer color, Float size, Float startup,
                        Vec3 inheritedVelocity, float trailCoverage) {
    }
}
