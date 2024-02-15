package dev.sterner.voidbound.registry


import dev.sterner.voidbound.Voidbound
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.simple.SimpleChannel


@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Voidbound.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
object PacketRegistry {

    const val PROTOCOL_VERSION: String = "1"
    var VOIDBOUND_CHANNEL: SimpleChannel = NetworkRegistry.newSimpleChannel(Voidbound.id("main"),
        { PROTOCOL_VERSION }, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals)

    @SubscribeEvent
    fun registerNetworkStuff(event: FMLCommonSetupEvent?) {
        var index = 0

    }
}