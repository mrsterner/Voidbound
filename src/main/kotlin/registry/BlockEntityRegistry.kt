package dev.sterner.voidbound.registry

import dev.sterner.voidbound.Voidbound.MODID
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object BlockEntityRegistry {

    private val BLOCK_ENTITY_TYPES: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID)

    fun register(bus: IEventBus) = BLOCK_ENTITY_TYPES.register(bus)

}