package dev.sterner.voidbound.events

import dev.sterner.voidbound.registry.ParticleRegistry
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientSetupEvents {

    @SubscribeEvent
    fun registerParticleFactory(event: RegisterParticleProvidersEvent) {
        ParticleRegistry.registerParticleFactory(event)
    }
}