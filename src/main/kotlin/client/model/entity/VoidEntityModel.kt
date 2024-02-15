package dev.sterner.voidbound.client.model.entity

import dev.sterner.voidbound.Voidbound
import dev.sterner.voidbound.common.entity.HandEntity
import dev.sterner.voidbound.common.entity.VoidEntity
import mod.azure.azurelib.model.GeoModel
import net.minecraft.resources.ResourceLocation

class VoidEntityModel : GeoModel<VoidEntity>() {

    private val model = Voidbound.id("geo/entity/void.geo.json")
    private val texture = Voidbound.id("textures/entity/void.png")
    private val animation = Voidbound.id("animations/entity/void.animation.json")

    override fun getModelResource(p0: VoidEntity?): ResourceLocation {
       return model
    }

    override fun getTextureResource(p0: VoidEntity?): ResourceLocation {
        return texture
    }

    override fun getAnimationResource(p0: VoidEntity?): ResourceLocation {
        return animation
    }
}