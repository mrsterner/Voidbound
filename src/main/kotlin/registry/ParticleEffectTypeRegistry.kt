package dev.sterner.voidbound.registry

import com.sammy.malum.visual_effects.networked.ParticleEffectType
import com.sammy.malum.visual_effects.networked.altar.SpiritAltarCraftParticleEffect
import dev.sterner.voidbound.visual_effects.networked.VoidParticleEffect


object ParticleEffectTypeRegistry {

    val EFFECT_TYPES: Map<String, ParticleEffectType> = LinkedHashMap()

    val VOID: ParticleEffectType = VoidParticleEffect("void")
}