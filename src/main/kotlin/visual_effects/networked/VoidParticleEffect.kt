package dev.sterner.voidbound.visual_effects.networked

import com.sammy.malum.visual_effects.SpiritLightSpecs
import com.sammy.malum.visual_effects.networked.ParticleEffectType
import com.sammy.malum.visual_effects.networked.ParticleEffectType.ParticleEffectActor
import com.sammy.malum.visual_effects.networked.data.ColorEffectData
import com.sammy.malum.visual_effects.networked.data.NBTEffectData
import com.sammy.malum.visual_effects.networked.data.PositionEffectData
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData
import java.awt.Color
import java.util.function.Supplier


class VoidParticleEffect(id: String) : ParticleEffectType(id) {

    @OnlyIn(Dist.CLIENT)
    override fun get(): Supplier<ParticleEffectActor> {
        return Supplier<ParticleEffectActor> {
            ParticleEffectActor { level: Level?, random: RandomSource, positionData: PositionEffectData, colorData: ColorEffectData, nbtData: NBTEffectData ->

                val compoundTag = nbtData.compoundTag.getCompound("TargetPosition")
                val vec3 = Vec3(compoundTag.getDouble("x"), compoundTag.getDouble("y"), compoundTag.getDouble("z"))

                coolLookingShinyThing(level, vec3, ColorParticleData.create(Color(0f,0f,0f), Color(0f,0f,0f)).build())
            }
        }
    }

    fun coolLookingShinyThing(level: Level?, pos: Vec3?, data: ColorParticleData) {
        val centralLightSpecs = SpiritLightSpecs.spiritLightSpecs(level, pos, data)

        val worldParticle = centralLightSpecs.builder as WorldParticleBuilder
        worldParticle.multiplyLifetime(0.6f)
            .modifyColorData { d: ColorParticleData -> d.multiplyCoefficient(0.5f) }
            .modifyData({ obj -> obj.scaleData }, { d -> d.multiplyValue(6.0f) })
            .modifyData({ obj -> obj.transparencyData }, { d -> d.multiplyValue(3.0f) })

        val bloomBuilder = centralLightSpecs.bloomBuilder as WorldParticleBuilder
        bloomBuilder.multiplyLifetime(0.6f)
            .modifyColorData { d -> d.multiplyCoefficient(0.5f)}
            .modifyData({ obj -> obj.scaleData }, { d -> d.multiplyValue(4.0f) })
            .modifyData({ obj -> obj.transparencyData }, { d -> d.multiplyValue(3.0f) })

        centralLightSpecs.spawnParticles()
    }
}