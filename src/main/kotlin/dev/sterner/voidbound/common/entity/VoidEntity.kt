package dev.sterner.voidbound.common.entity

import mod.azure.azurelib.animatable.GeoEntity
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar
import mod.azure.azurelib.core.animation.AnimationController
import mod.azure.azurelib.core.animation.AnimationState
import mod.azure.azurelib.core.animation.RawAnimation
import mod.azure.azurelib.util.AzureLibUtil
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.FlyingMoveControl
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState


class VoidEntity(pEntityType: EntityType<out Mob>, level: Level) : Mob(pEntityType, level), GeoEntity {

    private val cache: AnimatableInstanceCache = AzureLibUtil.createInstanceCache(this)

    init {
        this.moveControl = FlyingMoveControl(this, 20, true)
    }

    override fun createNavigation(pLevel: Level): PathNavigation {
        val navigator = FlyingPathNavigation(this, pLevel)
        navigator.setCanOpenDoors(false)
        navigator.setCanFloat(true)
        navigator.setCanPassDoors(true)
        return navigator
    }

    companion object {

        fun createAttributes(): AttributeSupplier.Builder {
            return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.ATTACK_DAMAGE, 1.0)
        }
    }

    override fun registerControllers(controllers: ControllerRegistrar) {
        controllers.add(AnimationController<VoidEntity?>(this, "controllerName", 0
        ) { event: AnimationState<VoidEntity?> ->
            event.setAndContinue(
                if (event.isMoving) {
                    RawAnimation.begin().thenLoop("fly")
                } else {
                    RawAnimation.begin().thenLoop("idle")
                }
            )
        })
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

    override fun playStepSound(pPos: BlockPos, pState: BlockState) {
    }

    override fun checkFallDamage(pY: Double, pOnGround: Boolean, pState: BlockState, pPos: BlockPos) {
    }

    override fun getAmbientSound(): SoundEvent {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM
    }

    override fun getHurtSound(pDamageSource: DamageSource): SoundEvent {
        return SoundEvents.ALLAY_HURT
    }

    override fun getDeathSound(): SoundEvent {
        return SoundEvents.ALLAY_DEATH
    }

    override fun getSoundVolume(): Float {
        return 0.4f
    }
}