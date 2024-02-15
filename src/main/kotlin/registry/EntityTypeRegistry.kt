package dev.sterner.voidbound.registry

import com.sammy.malum.MalumMod
import com.sammy.malum.common.entity.bolt.HexBoltEntity
import dev.sterner.voidbound.Voidbound
import dev.sterner.voidbound.common.entity.HandEntity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.Level
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject


object EntityTypeRegistry {
    val ENTITY_TYPES: DeferredRegister<EntityType<*>> =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Voidbound.MODID)


    val HAND: RegistryObject<EntityType<HandEntity?>> = ENTITY_TYPES.register("hand"
    ) {
        EntityType.Builder.of({ e: EntityType<HandEntity>, w: Level ->
            HandEntity(e, w)
        }, MobCategory.CREATURE)
            .sized(0.75F, 4F)
            .clientTrackingRange(10)
            .build(Voidbound.id("hand").toString())
    }

    fun register(bus: IEventBus) = ENTITY_TYPES.register(bus)
}