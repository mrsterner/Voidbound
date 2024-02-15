package dev.sterner.voidbound.visual_effects.networked

import com.sammy.malum.visual_effects.networked.ParticleEffectType
import com.sammy.malum.visual_effects.networked.ParticleEffectType.ParticleEffectActor
import com.sammy.malum.visual_effects.networked.data.ColorEffectData
import com.sammy.malum.visual_effects.networked.data.NBTEffectData
import com.sammy.malum.visual_effects.networked.data.PositionEffectData
import net.minecraft.client.Minecraft
import net.minecraft.core.Vec3i
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import team.lodestar.lodestone.helpers.RandomHelper
import team.lodestar.lodestone.systems.particle.LodestoneWorldParticleActor
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData
import java.util.function.Supplier
import kotlin.math.cos
import kotlin.math.sin


class VoidParticleEffect(id: String) : ParticleEffectType(id) {


    @OnlyIn(Dist.CLIENT)
    override fun get(): Supplier<ParticleEffectActor> {
        return Supplier<ParticleEffectActor> {
            ParticleEffectActor { level: Level?, random: RandomSource, positionData: PositionEffectData, colorData: ColorEffectData, nbtData: NBTEffectData ->



            }
        }
    }


}