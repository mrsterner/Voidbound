package dev.sterner.voidbound.visual_effects.networked

import com.sammy.malum.visual_effects.networked.ParticleEffectType
import com.sammy.malum.visual_effects.networked.ParticleEffectType.ParticleEffectActor
import com.sammy.malum.visual_effects.networked.data.ColorEffectData
import com.sammy.malum.visual_effects.networked.data.NBTEffectData
import com.sammy.malum.visual_effects.networked.data.PositionEffectData
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.function.Supplier


class VoidParticleEffect(id: String) : ParticleEffectType(id) {


    @OnlyIn(Dist.CLIENT)
    override fun get(): Supplier<ParticleEffectActor> {
        return Supplier<ParticleEffectActor> {
            ParticleEffectActor { level: Level?, random: RandomSource, positionData: PositionEffectData, colorData: ColorEffectData, nbtData: NBTEffectData ->



            }
        }
    }


}