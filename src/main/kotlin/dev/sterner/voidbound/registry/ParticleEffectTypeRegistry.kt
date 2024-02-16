package dev.sterner.voidbound.registry

import dev.sterner.voidbound.visual_effects.networked.ParticleEffectType
import dev.sterner.voidbound.visual_effects.networked.VoidParticleEffect


object ParticleEffectTypeRegistry {

    @JvmStatic
    val EFFECT_TYPES: MutableMap<String, ParticleEffectType> = LinkedHashMap()

    val VOID: ParticleEffectType = VoidParticleEffect("void")

    fun init(){
    }
}