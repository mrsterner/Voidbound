package dev.sterner.voidbound.client.model.entity

import dev.sterner.voidbound.Voidbound
import dev.sterner.voidbound.common.entity.HandEntity
import mod.azure.azurelib.model.GeoModel
import net.minecraft.resources.ResourceLocation

class HandEntityModel : GeoModel<HandEntity>() {

    private val model = Voidbound.id("geo/entity/hand.geo.json")
    private val texture = Voidbound.id("textures/entity/hand.png")
    private val animation = Voidbound.id("animations/entity/hand.animation.json")

    override fun getModelResource(p0: HandEntity?): ResourceLocation {
       return model
    }

    override fun getTextureResource(p0: HandEntity?): ResourceLocation {
        return texture
    }

    override fun getAnimationResource(p0: HandEntity?): ResourceLocation {
        return animation
    }
}