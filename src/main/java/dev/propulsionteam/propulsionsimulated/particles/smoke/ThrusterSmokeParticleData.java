package dev.propulsionteam.propulsionsimulated.particles.smoke;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.foundation.particle.ICustomParticleData;
import dev.propulsionteam.propulsionsimulated.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;

public record ThrusterSmokeParticleData(
        float scale,
        int lifetime,
        float r,
        float g,
        float b
) implements ParticleOptions, ICustomParticleData<ThrusterSmokeParticleData> {

    public ThrusterSmokeParticleData() {
        this(2.5f, 55, 0.24f, 0.24f, 0.27f);
    }

    public static final Codec<ThrusterSmokeParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            com.mojang.serialization.Codec.FLOAT.fieldOf("scale").forGetter(ThrusterSmokeParticleData::scale),
            com.mojang.serialization.Codec.INT.fieldOf("lifetime").forGetter(ThrusterSmokeParticleData::lifetime),
            com.mojang.serialization.Codec.FLOAT.fieldOf("r").forGetter(ThrusterSmokeParticleData::r),
            com.mojang.serialization.Codec.FLOAT.fieldOf("g").forGetter(ThrusterSmokeParticleData::g),
            com.mojang.serialization.Codec.FLOAT.fieldOf("b").forGetter(ThrusterSmokeParticleData::b)
    ).apply(instance, ThrusterSmokeParticleData::new));

    public static final ParticleOptions.Deserializer<ThrusterSmokeParticleData> DESERIALIZER =
            new ParticleOptions.Deserializer<>() {
                @Override
                public ThrusterSmokeParticleData fromCommand(ParticleType<ThrusterSmokeParticleData> type,
                                                              StringReader reader) throws CommandSyntaxException {
                    reader.expect(' ');
                    float scale = reader.readFloat();
                    reader.expect(' ');
                    int lifetime = reader.readInt();
                    reader.expect(' ');
                    float red = reader.readFloat();
                    reader.expect(' ');
                    float green = reader.readFloat();
                    reader.expect(' ');
                    float blue = reader.readFloat();
                    return new ThrusterSmokeParticleData(scale, lifetime, red, green, blue);
                }

                @Override
                public ThrusterSmokeParticleData fromNetwork(ParticleType<ThrusterSmokeParticleData> type,
                                                              FriendlyByteBuf buffer) {
                    return new ThrusterSmokeParticleData(buffer.readFloat(), buffer.readVarInt(),
                            buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
                }
            };

    @Override
    public ParticleType<?> getType() {
        return ParticleTypes.getSmokeType();
    }

    @Override
    public Codec<ThrusterSmokeParticleData> getCodec(ParticleType<ThrusterSmokeParticleData> type) {
        return CODEC;
    }

    @Override
    public ParticleOptions.Deserializer<ThrusterSmokeParticleData> getDeserializer() {
        return DESERIALIZER;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(scale);
        buffer.writeVarInt(lifetime);
        buffer.writeFloat(r);
        buffer.writeFloat(g);
        buffer.writeFloat(b);
    }

    @Override
    public String writeToString() {
        return String.format(java.util.Locale.ROOT, "%s %.9g %d %.9g %.9g %.9g",
                ParticleTypes.SMOKE.parameter(), scale, lifetime, r, g, b);
    }

    @Override
    public net.minecraft.client.particle.ParticleProvider<ThrusterSmokeParticleData> getFactory() {
        return (data, level, x, y, z, xd, yd, zd) -> null;
    }

    @Override
    public void register(ParticleType<ThrusterSmokeParticleData> type, RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(type, ThrusterSmokeParticle.Provider::new);
    }
}
