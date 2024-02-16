package dev.sterner.voidbound.common.packet

import com.sammy.malum.visual_effects.networked.data.ColorEffectData
import com.sammy.malum.visual_effects.networked.data.NBTEffectData
import com.sammy.malum.visual_effects.networked.data.PositionEffectData
import dev.sterner.voidbound.registry.ParticleEffectTypeRegistry
import dev.sterner.voidbound.visual_effects.networked.ParticleEffectType
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.network.simple.SimpleChannel
import team.lodestar.lodestone.systems.network.LodestoneClientPacket
import java.util.function.Supplier

class ParticleEffectPacket(private val id: String,
                           private val positionData: PositionEffectData,
                           private val colorData: ColorEffectData?,
                           private val nbtData: NBTEffectData?) :
    LodestoneClientPacket() {

    override fun encode(buf: FriendlyByteBuf) {
        buf.writeUtf(this.id)
        positionData.encode(buf)
        val nonNullColorData = this.colorData != null
        buf.writeBoolean(nonNullColorData)
        if (nonNullColorData) {
            colorData!!.encode(buf)
        }

        val nonNullCompoundTag = this.nbtData != null
        buf.writeBoolean(nonNullCompoundTag)
        if (nonNullCompoundTag) {
            buf.writeNbt(nbtData!!.compoundTag)
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun execute(context: Supplier<NetworkEvent.Context>) {
        val instance = Minecraft.getInstance()
        val level = instance.level
        val particleEffectType = ParticleEffectTypeRegistry.EFFECT_TYPES[id]
        if (particleEffectType == null) {
            throw RuntimeException("This shouldn't be happening.")
        } else {
            val particleEffectActor = particleEffectType.get().get()
            particleEffectActor.act(level, level!!.random, this.positionData, this.colorData!!, this.nbtData)
        }
    }


    companion object {

        @JvmStatic
        fun decode(buf: FriendlyByteBuf): ParticleEffectPacket {
            return ParticleEffectPacket(buf.readUtf(),
                PositionEffectData(buf),
                if (buf.readBoolean()) ColorEffectData(buf) else null,
                if (buf.readBoolean()) NBTEffectData(buf.readNbt()) else null)
        }
    }
}