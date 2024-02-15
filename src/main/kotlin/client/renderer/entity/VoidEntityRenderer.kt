package dev.sterner.voidbound.client.renderer.entity

import dev.sterner.voidbound.client.model.entity.VoidEntityModel
import dev.sterner.voidbound.common.entity.VoidEntity
import mod.azure.azurelib.renderer.GeoEntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider

class VoidEntityRenderer(renderManager: EntityRendererProvider.Context?) : GeoEntityRenderer<VoidEntity>(renderManager, VoidEntityModel())