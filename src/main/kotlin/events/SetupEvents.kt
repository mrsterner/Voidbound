package dev.sterner.voidbound.events

import dev.sterner.voidbound.common.entity.HandEntity
import dev.sterner.voidbound.common.entity.VoidEntity
import dev.sterner.voidbound.registry.EntityTypeRegistry
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object SetupEvents {

    @SubscribeEvent
    fun entityAttributes(event: EntityAttributeCreationEvent) {
        event.put(EntityTypeRegistry.HAND.get(), HandEntity.createAttributes().build())
        event.put(EntityTypeRegistry.VOID.get(), VoidEntity.createAttributes().build())

    }
}