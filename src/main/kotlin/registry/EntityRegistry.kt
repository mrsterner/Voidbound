package dev.sterner.voidbound.registry

import com.sammy.malum.MalumMod
import dev.sterner.voidbound.Voidbound
import net.minecraft.world.entity.EntityType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries


object EntityRegistry {
    val ENTITY_TYPES: DeferredRegister<EntityType<*>> =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Voidbound.MODID)

    fun register(bus: IEventBus) = ENTITY_TYPES.register(bus)

    @Mod.EventBusSubscriber(modid = MalumMod.MALUM, value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
    object ClientOnly {
        @SubscribeEvent
        fun bindEntityRenderers(event: RegisterRenderers) {

        }
    }
}