package dev.sterner.voidbound.common.block.entity

import com.mojang.authlib.GameProfile
import dev.sterner.voidbound.registry.BlockEntityRegistry
import dev.sterner.voidbound.util.Constants
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.StringUtil
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.SkullBlockEntity
import net.minecraft.world.level.block.state.BlockState
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity
import java.util.*


class EffigyBlockEntity(pos: BlockPos?, state: BlockState?) : LodestoneBlockEntity(BlockEntityRegistry.EFFIGY.get(), pos, state) {

    var creator: UUID? = null
    var creatorName: String? = null
    var pose: Int = 0
    private var statueOwner: GameProfile? = null

    fun selectRandomPose(random: Random): Int {
        return if (random.nextFloat() < 0.05f) {
            69
        } else {
            random.nextInt(3)
        }
    }

    fun setStatueProfile(profile: GameProfile?) {
        synchronized(this) {
            this.statueOwner = profile
        }

        this.loadOwnerProperties()

        if (level is ServerLevel) {
            sync(level, blockPos)
        }
    }

    private fun loadOwnerProperties() {
        SkullBlockEntity.updateGameprofile(this.statueOwner) { owner ->
            this.statueOwner = owner
            this.setChanged()
        }
    }

    fun sync(level: Level?, pos: BlockPos) {
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(pos, blockState, blockState, Block.UPDATE_CLIENTS)
        }
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)

        if (tag.contains(Constants.NBT.PLAYER_UUID)) {
            creator = tag.getUUID(Constants.NBT.PLAYER_UUID)
        }
        if (tag.contains(Constants.NBT.STATUE_OWNER, 10)) {
            this.setStatueProfile(NbtUtils.readGameProfile(tag.getCompound(Constants.NBT.STATUE_OWNER)))
        } else if (tag.contains("ExtraType", 8)) {
            val string: String = tag.getString("ExtraType")
            if (!StringUtil.isNullOrEmpty(string)) {
                this.setStatueProfile(GameProfile(null, string))
            }
        }
        if (tag.contains(Constants.NBT.PLAYER_NAME)) {
            creatorName = tag.getString(Constants.NBT.PLAYER_NAME)
        }
        pose = tag.getInt(Constants.NBT.POSE)
    }

    override fun saveAdditional(tag: CompoundTag) {
        if (creator != null) {
            tag.putUUID(Constants.NBT.PLAYER_UUID, creator!!)
        }
        if (statueOwner != null) {
            val nbtCompound = CompoundTag()
            NbtUtils.writeGameProfile(nbtCompound, statueOwner!!)
            tag.put(Constants.NBT.STATUE_OWNER, nbtCompound)
        }

        if (creatorName != null) {
            tag.putString(Constants.NBT.PLAYER_NAME, creatorName!!)
        }
        tag.putInt(Constants.NBT.POSE, pose)
    }

    fun getStatueProfile(): GameProfile? {
        return statueOwner
    }
}