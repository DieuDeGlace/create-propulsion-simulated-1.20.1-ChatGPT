package dev.propulsionteam.propulsionsimulated.particles.ion;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.foundation.particle.ICustomParticleDataWithSprite;

import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import dev.propulsionteam.propulsionsimulated.particles.ParticleTypes;
import dev.propulsionteam.propulsionsimulated.particles.VariableParticleDataSerialization;

public class IonParticleData implements ParticleOptions, ICustomParticleDataWithSprite<IonParticleData> {
    public static final Codec<IonParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.listOf().optionalFieldOf("override_textures", List.of()).forGetter(IonParticleData::overrideTextures),
        Codec.INT.optionalFieldOf("override_color").forGetter(data -> java.util.Optional.ofNullable(data.overrideColor())),
        Codec.FLOAT.optionalFieldOf("override_size").forGetter(data -> java.util.Optional.ofNullable(data.overrideSize())),
        Codec.FLOAT.optionalFieldOf("startup_progress").forGetter(data -> java.util.Optional.ofNullable(data.startupProgress())),
        Codec.DOUBLE.optionalFieldOf("inherited_velocity_x", 0.0d).forGetter(data -> data.inheritedVelocity.x),
        Codec.DOUBLE.optionalFieldOf("inherited_velocity_y", 0.0d).forGetter(data -> data.inheritedVelocity.y),
        Codec.DOUBLE.optionalFieldOf("inherited_velocity_z", 0.0d).forGetter(data -> data.inheritedVelocity.z),
        Codec.FLOAT.optionalFieldOf("trail_coverage", 0.0f).forGetter(IonParticleData::trailCoverage)
    ).apply(instance, (textures, color, size, startup, vx, vy, vz, coverage) -> new IonParticleData(
            textures, color.orElse(null), size.orElse(null), startup.orElse(null), new Vec3(vx, vy, vz), coverage)));
    public static final ParticleOptions.Deserializer<IonParticleData> DESERIALIZER =
            VariableParticleDataSerialization.deserializer(IonParticleData::new);

    private final List<ResourceLocation> overrideTextures;
    private final Integer overrideColor;
    private final Float overrideSize;
    private final Float startupProgress;
    private final Vec3 inheritedVelocity;
    private final float trailCoverage;

    public IonParticleData() {
        this(List.of(), null, null, null, Vec3.ZERO);
    }

    public IonParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor, Float overrideSize) {
        this(overrideTextures, overrideColor, overrideSize, null, Vec3.ZERO);
    }

    public IonParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor, Float overrideSize, Float startupProgress) {
        this(overrideTextures, overrideColor, overrideSize, startupProgress, Vec3.ZERO);
    }

    public IonParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor, Float overrideSize,
                           Float startupProgress, Vec3 inheritedVelocity) {
        this(overrideTextures, overrideColor, overrideSize, startupProgress, inheritedVelocity, 0.0f);
    }

    public IonParticleData(List<ResourceLocation> overrideTextures, Integer overrideColor, Float overrideSize,
                           Float startupProgress, Vec3 inheritedVelocity, float trailCoverage) {
        this.overrideTextures = overrideTextures == null ? List.of() : List.copyOf(overrideTextures);
        this.overrideColor = overrideColor;
        this.overrideSize = overrideSize;
        this.startupProgress = startupProgress;
        this.inheritedVelocity = inheritedVelocity == null ? Vec3.ZERO : inheritedVelocity;
        this.trailCoverage = Mth.clamp(trailCoverage, 0.0f, 1.0f);
    }

    public List<ResourceLocation> overrideTextures() {
        return overrideTextures;
    }

    public Integer overrideColor() {
        return overrideColor;
    }

    public Float overrideSize() {
        return overrideSize;
    }

    public Float startupProgress() { return startupProgress; }

    public Vec3 inheritedVelocity() { return inheritedVelocity; }
    public float trailCoverage() { return trailCoverage; }

    @Override
    public ParticleType<?> getType(){
        return ParticleTypes.getIonType();
    }

    public Codec<IonParticleData> getCodec(ParticleType<IonParticleData> type) {
        return CODEC;
    }

    @Override
    public ParticleOptions.Deserializer<IonParticleData> getDeserializer() {
        return DESERIALIZER;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        VariableParticleDataSerialization.write(buffer, overrideTextures, overrideColor, overrideSize,
                startupProgress, inheritedVelocity, trailCoverage);
    }

    @Override
    public String writeToString() {
        return VariableParticleDataSerialization.writeToString(ParticleTypes.ION.parameter(), overrideTextures,
                overrideColor, overrideSize, startupProgress, inheritedVelocity, trailCoverage);
    }

    @Override
    public SpriteParticleRegistration<IonParticleData> getMetaFactory() {
        return IonParticle.Factory::new;
    }
}
