package dev.sterner.voidbound.visual_effects.networked

import com.sammy.malum.visual_effects.SpiritLightSpecs
import com.sammy.malum.visual_effects.networked.data.ColorEffectData
import com.sammy.malum.visual_effects.networked.data.NBTEffectData
import com.sammy.malum.visual_effects.networked.data.PositionEffectData
import net.minecraft.client.Minecraft
import net.minecraft.core.Direction
import net.minecraft.core.Vec3i
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import team.lodestar.lodestone.helpers.RandomHelper
import team.lodestar.lodestone.systems.particle.LodestoneWorldParticleActor
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder
import team.lodestar.lodestone.systems.particle.data.GenericParticleData
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData
import java.util.function.Consumer
import java.util.function.Supplier
import kotlin.math.cos
import kotlin.math.sin


class VoidParticleEffect(id: String) : ParticleEffectType(id) {

    override fun get(): Supplier<ParticleEffectActor> {
        return Supplier<ParticleEffectActor> {
            ParticleEffectActor { level: Level?, random: RandomSource, positionData: PositionEffectData, colorData: ColorEffectData, nbtData: NBTEffectData? ->

                val colorRecord = colorData.defaultColorRecord
                val primaryColor = colorData.getPrimaryColor(colorRecord)
                val secondaryColor = colorData.getSecondaryColor(colorRecord)
                val blockPos = positionData.asBlockPos
                val pos = blockPos.center
                val playerPosition = Minecraft.getInstance().player!!.position()
                val normal: Vec3i = Direction.UP.normal
                val yRot = ((Mth.atan2(normal.x.toDouble(), normal.z.toDouble()) * (180f / Math.PI.toFloat()).toDouble()).toFloat())
                val yaw = Math.toRadians(yRot.toDouble()).toFloat()
                val left = Vec3(-cos(yaw.toDouble()), 0.0, sin(yaw.toDouble()))
                val up = left.cross(Vec3(normal.x.toDouble(), normal.y.toDouble(), normal.z.toDouble()))
                val acceleration =
                    Consumer { p: LodestoneWorldParticleActor ->
                        p.setParticleMotion(p.particleSpeed.scale(1.2))
                    }
                for (i in 0..11) {
                    val leftOffset = (random.nextFloat() - 0.5f) * 0.75f
                    val upOffset = (random.nextFloat() - 0.5f) * 0.75f
                    val particlePosition = pos.add(left.scale(leftOffset.toDouble())).add(up.scale(upOffset.toDouble()))
                    val particleMotion = playerPosition.subtract(particlePosition).normalize()
                    val targetPosition = pos.add(particleMotion.scale(0.75))
                    val actualMotion = targetPosition.subtract(particlePosition).normalize().scale(0.01)
                    val lightSpecs = SpiritLightSpecs.spiritLightSpecs(level,
                        particlePosition,
                        ColorParticleData.create(primaryColor, secondaryColor).build())
                    lightSpecs.builder.act { b: WorldParticleBuilder ->
                        b
                            .addTickActor(acceleration)
                            .setMotion(actualMotion)
                            .modifyData(Supplier { b.scaleData },
                                Consumer { d: GenericParticleData ->
                                    d.multiplyValue(RandomHelper.randomBetween(random,
                                        1f,
                                        2f))
                                })
                    }
                    lightSpecs.bloomBuilder.act { b: WorldParticleBuilder ->
                        b
                            .addTickActor(acceleration)
                            .setMotion(actualMotion)
                            .modifyData(Supplier { b.scaleData },
                                Consumer { d: GenericParticleData ->
                                    d.multiplyValue(RandomHelper.randomBetween(random,
                                        0.6f,
                                        1.5f))
                                })
                    }
                    lightSpecs.spawnParticles()
                }
            }
        }
    }
}