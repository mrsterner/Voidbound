package dev.sterner.voidbound.registry

import dev.sterner.voidbound.Voidbound
import net.minecraft.core.particles.ParticleType
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ParticleRegistry {

    private var PARTICLES: DeferredRegister<ParticleType<*>> = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Voidbound.MODID);
    fun register(bus: IEventBus) = PARTICLES.register(bus)

    fun registerParticleFactory(event: RegisterParticleProvidersEvent) {

    }
}