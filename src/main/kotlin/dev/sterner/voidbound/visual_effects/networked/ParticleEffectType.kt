package dev.sterner.voidbound.visual_effects.networked

import com.sammy.malum.visual_effects.networked.data.ColorEffectData
import com.sammy.malum.visual_effects.networked.data.NBTEffectData
import com.sammy.malum.visual_effects.networked.data.PositionEffectData
import dev.sterner.voidbound.PacketRegistry
import dev.sterner.voidbound.common.packet.ParticleEffectPacket
import dev.sterner.voidbound.registry.ParticleEffectTypeRegistry
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.PacketDistributor.PacketTarget
import java.util.function.Supplier

abstract class ParticleEffectType(val id: String) {
    init {
        ParticleEffectTypeRegistry.EFFECT_TYPES[id] = this
    }

    @OnlyIn(Dist.CLIENT)
    abstract fun get(): Supplier<ParticleEffectActor>

    @JvmOverloads
    fun createEntityEffect(entity: Entity?, colorData: ColorEffectData? = null, nbtData: NBTEffectData? = null) {
        createEffect(PacketDistributor.TRACKING_ENTITY_AND_SELF.with { entity },
            PositionEffectData(entity),
            colorData,
            nbtData)
    }

    @JvmOverloads
    fun createPositionedEffect(level: Level,
                               positionData: PositionEffectData,
                               colorData: ColorEffectData? = null,
                               nbtData: NBTEffectData? = null) {

        createEffect(PacketDistributor.TRACKING_CHUNK.with {
            level.getChunkAt(positionData.asBlockPos)
        }, positionData, colorData, nbtData)
    }

    fun createEffect(target: PacketTarget?,
                     positionData: PositionEffectData,
                     colorData: ColorEffectData?,
                     nbtData: NBTEffectData?) {
        PacketRegistry.VOIDBOUND_CHANNEL.send(target, ParticleEffectPacket(
            id, positionData, colorData, nbtData))
    }

    fun interface ParticleEffectActor {
        fun act(level: Level?,
                random: RandomSource,
                positionData: PositionEffectData,
                colorData: ColorEffectData,
                nbtData: NBTEffectData?)
    }
}