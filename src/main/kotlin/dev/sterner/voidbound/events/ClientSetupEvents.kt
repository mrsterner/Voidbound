package dev.sterner.voidbound.events

import com.mojang.authlib.GameProfile
import dev.sterner.voidbound.client.renderer.blockentity.EffigyBlockEntityRenderer
import dev.sterner.voidbound.client.renderer.entity.HandEntityRenderer
import dev.sterner.voidbound.client.renderer.entity.VoidEntityRenderer
import dev.sterner.voidbound.registry.BlockEntityRegistry
import dev.sterner.voidbound.registry.EntityTypeRegistry
import dev.sterner.voidbound.registry.ParticleRegistry
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.client.event.RegisterTextureAtlasSpriteLoadersEvent
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(value = [Dist.CLIENT], bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientSetupEvents {

    @SubscribeEvent
    fun registerParticleFactory(event: RegisterParticleProvidersEvent) {
        ParticleRegistry.registerParticleFactory(event)
    }

    @SubscribeEvent
    fun registerRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerEntityRenderer(EntityTypeRegistry.HAND.get(), ::HandEntityRenderer)
        event.registerEntityRenderer(EntityTypeRegistry.VOID.get(), ::VoidEntityRenderer)
        event.registerBlockEntityRenderer(BlockEntityRegistry.EFFIGY.get(), ::EffigyBlockEntityRenderer)
    }

    @SubscribeEvent
    fun spriteReg(event: TextureStitchEvent){
        EffigyBlockEntityRenderer.TEXTURE_CACHE.forEach{entry: Map.Entry<GameProfile, EffigyBlockEntityRenderer.WoodTexture> ->  entry.value.needsUpdate = true}
    }
}