@file:Suppress("HasPlatformType", "unused")

package dev.sterner.voidbound.init

import dev.sterner.voidbound.MODID
import net.minecraft.world.item.Item
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ItemRegistry {

    // for register
    private val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    fun register(bus: IEventBus) = ITEMS.register(bus)

    // ==================== //
    //     Normal Items     //
    // ==================== //


}