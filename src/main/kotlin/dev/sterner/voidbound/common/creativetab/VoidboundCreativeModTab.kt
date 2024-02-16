package dev.sterner.voidbound.common.creativetab

import dev.sterner.voidbound.Voidbound
import dev.sterner.voidbound.Voidbound.MODID
import dev.sterner.voidbound.registry.ItemRegistry
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.DeferredRegister


@Suppress("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object VoidboundCreativeModTab {

    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Voidbound.MODID)

    @SubscribeEvent
    fun buildContents(event: BuildCreativeModeTabContentsEvent) {
        if (event.tab == CREATIVE_MODE_TABS) {
            event.accept(ItemRegistry.ALL_BLACK)
        }
    }

    fun register(bus: IEventBus) = CREATIVE_MODE_TABS.register(bus)

}
