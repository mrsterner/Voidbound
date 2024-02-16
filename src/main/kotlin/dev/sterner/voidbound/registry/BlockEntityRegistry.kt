package dev.sterner.voidbound.registry

import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlockEntity
import dev.sterner.voidbound.Voidbound.MODID
import dev.sterner.voidbound.common.block.entity.EffigyBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier


object BlockEntityRegistry {

    private val BLOCK_ENTITY_TYPES: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID)

    val EFFIGY: RegistryObject<BlockEntityType<EffigyBlockEntity>> =
        BLOCK_ENTITY_TYPES.register("effigy"
        ) {
            BlockEntityType.Builder.of({ pos: BlockPos?, state: BlockState? ->
                EffigyBlockEntity(pos,
                    state)
            }, BlockRegistry.EFFIGY.get()).build(null)
        }


    fun register(bus: IEventBus) = BLOCK_ENTITY_TYPES.register(bus)

}