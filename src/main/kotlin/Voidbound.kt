package dev.sterner.voidbound

import dev.sterner.voidbound.Voidbound.MODID
import dev.sterner.voidbound.common.creativetab.VoidboundCreativeModTab
import dev.sterner.voidbound.registry.*
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(MODID)
object Voidbound {

    const val MODID = "voidbound"

    val LOGGER: Logger = LogManager.getLogger(MODID)

    init {
        LOGGER.log(Level.INFO, "$MODID has started!")

        MOD_BUS.addListener(::onClientSetup)

        ItemRegistry.register(MOD_BUS)
        BlockRegistry.register(MOD_BUS)
        BlockEntityRegistry.register(MOD_BUS)
        EntityTypeRegistry.register(MOD_BUS)
        ParticleRegistry.register(MOD_BUS)
        VoidboundCreativeModTab.register(MOD_BUS)

        ParticleEffectTypeRegistry.init()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client... with voidbound!")

    }

    fun id(name: String): ResourceLocation {
        return ResourceLocation(MODID, name)
    }
}