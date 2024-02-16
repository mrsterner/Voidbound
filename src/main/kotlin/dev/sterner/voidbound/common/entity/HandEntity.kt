package dev.sterner.voidbound.common.entity

import com.sammy.malum.registry.common.SpiritTypeRegistry
import com.sammy.malum.visual_effects.networked.SapCollectionParticleEffect
import com.sammy.malum.visual_effects.networked.data.ColorEffectData
import com.sammy.malum.visual_effects.networked.data.PositionEffectData
import dev.sterner.voidbound.registry.ParticleEffectTypeRegistry
import mod.azure.azurelib.animatable.GeoEntity
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar
import mod.azure.azurelib.core.animation.Animation
import mod.azure.azurelib.core.animation.AnimationController
import mod.azure.azurelib.core.animation.AnimationState
import mod.azure.azurelib.core.animation.RawAnimation
import mod.azure.azurelib.core.`object`.PlayState
import mod.azure.azurelib.util.AzureLibUtil
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.level.Level


class HandEntity(pEntityType: EntityType<out Mob>, level: Level) : Mob(pEntityType, level), GeoEntity {


    private val cache: AnimatableInstanceCache = AzureLibUtil.createInstanceCache(this)

    companion object {
        private val SPAWN_TIMER: EntityDataAccessor<Int> = SynchedEntityData.defineId(HandEntity::class.java, EntityDataSerializers.INT)

        fun createAttributes(): AttributeSupplier.Builder {
            return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
        }
    }


    override fun addAdditionalSaveData(nbt: CompoundTag) {
        super.addAdditionalSaveData(nbt)
        nbt.putInt("SpawnTimer", getSpawnTimer())
    }

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        super.readAdditionalSaveData(nbt)
        setSpawnTimer(nbt.getInt("SpawnTimer"))
    }

    override fun defineSynchedData() {
        super.defineSynchedData()
        this.entityData.define(SPAWN_TIMER, 75)
    }

    override fun tick() {
        if (this.getSpawnTimer() > 0) {
            this.setSpawnTimer(getSpawnTimer() - 1)
        }

        super.tick()
        if (!level().isClientSide) {

            ParticleEffectTypeRegistry.VOID.createPositionedEffect(level(),
                PositionEffectData(position()),
                ColorEffectData(SpiritTypeRegistry.INFERNAL_SPIRIT.primaryColor))
            //ParticleEffectTypeRegistry.VOID.createPositionedEffect(level(), PositionEffectData(position()))
        }
    }

    override fun aiStep() {
    }

    override fun knockback(strength: Double, x: Double, z: Double) {
    }

    override fun registerControllers(controllers: ControllerRegistrar) {
        controllers.add(AnimationController(this, "idle", 20, this::idle).setAnimationSpeed(0.5))
        controllers.add(AnimationController(this, "emerge", 0, this::emerge).setAnimationSpeed(0.5))
    }

    private fun idle(state: AnimationState<HandEntity>): PlayState {
        if (getSpawnTimer() <= 0) {
            state.setAnimation(RawAnimation.begin().thenLoop("misc.idle"))
            return PlayState.CONTINUE
        }
        return PlayState.STOP
    }

    private fun emerge(state: AnimationState<HandEntity>): PlayState {
        if (getSpawnTimer() > 0) {
            state.setAnimation(RawAnimation.begin().then("misc.emerge", Animation.LoopType.HOLD_ON_LAST_FRAME))
            return PlayState.CONTINUE
        }
        return PlayState.STOP
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }

    fun getSpawnTimer(): Int {
        return this.entityData.get(SPAWN_TIMER)
    }

    fun setSpawnTimer(ticks: Int) {
        this.entityData.set(SPAWN_TIMER, ticks)
    }
}