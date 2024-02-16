package dev.sterner.voidbound.registry

import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlock
import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlockEntity
import com.sammy.malum.registry.common.block.MalumBlockProperties
import dev.sterner.voidbound.Voidbound.MODID
import dev.sterner.voidbound.common.block.EffigyBlock
import dev.sterner.voidbound.common.block.entity.EffigyBlockEntity
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject


object BlockRegistry {

    private val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)

    val EFFIGY: RegistryObject<Block> = BLOCKS.register<Block>("effigy"
    ) {
        EffigyBlock<EffigyBlockEntity>(
            MalumBlockProperties.SOULWOOD().setCutoutRenderType()
                .noOcclusion()).setBlockEntity(BlockEntityRegistry.EFFIGY)
    }

    fun register(bus: IEventBus) = BLOCKS.register(bus)

}