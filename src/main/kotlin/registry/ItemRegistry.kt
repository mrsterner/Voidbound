package dev.sterner.voidbound.registry

import dev.sterner.voidbound.Voidbound.MODID
import net.minecraft.world.item.Item
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ItemRegistry {

    private val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    fun register(bus: IEventBus) = ITEMS.register(bus)

}