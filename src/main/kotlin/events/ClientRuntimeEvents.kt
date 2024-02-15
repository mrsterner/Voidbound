package dev.sterner.voidbound.events

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.FORGE)
object ClientRuntimeEvents {
}