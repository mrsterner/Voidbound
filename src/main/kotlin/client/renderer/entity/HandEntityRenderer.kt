package dev.sterner.voidbound.client.renderer.entity

import dev.sterner.voidbound.client.model.entity.HandEntityModel
import dev.sterner.voidbound.common.entity.HandEntity
import mod.azure.azurelib.renderer.GeoEntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider

class HandEntityRenderer(renderManager: EntityRendererProvider.Context?) : GeoEntityRenderer<HandEntity>(renderManager, HandEntityModel()) {

}