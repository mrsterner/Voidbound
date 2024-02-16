package dev.sterner.voidbound.common.block

import dev.sterner.voidbound.common.block.entity.EffigyBlockEntity
import dev.sterner.voidbound.util.Constants
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.tags.FluidTags
import net.minecraft.util.Mth
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.BlockHitResult
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock


class EffigyBlock(properties: Properties) : LodestoneEntityBlock<EffigyBlockEntity>(properties) {

    init {
        registerDefaultState(super.defaultBlockState().setValue(BlockStateProperties.ROTATION_16, 0).setValue(BlockStateProperties.WATERLOGGED, false))
    }

    override fun use(state: BlockState,
                     level: Level,
                     pos: BlockPos,
                     player: Player,
                     hand: InteractionHand,
                     ray: BlockHitResult): InteractionResult {

        //setSculptureData(player.getItemInHand(hand), player, player, 1)
        val ent = level.getBlockEntity(pos)
        if (ent is EffigyBlockEntity) {

            ent.creator = player.uuid
            ent.creatorName = player.name.string
            ent.setStatueProfile(player.gameProfile)
        }

        return super.use(state, level, pos, player, hand, ray)
    }

    fun setSculptureData(stack: ItemStack, creator: Player?, target: Player?, pose: Int): ItemStack {
        if (!stack.hasTag()) {
            stack.tag = CompoundTag()
        }
        val blockEntityTag: CompoundTag = if (stack.tag!!.contains(Constants.NBT.BLOCK_ENTITY_TAG)) stack.tag!!
            .getCompound(Constants.NBT.BLOCK_ENTITY_TAG) else CompoundTag()

        if (creator != null) {
            blockEntityTag.putString(Constants.NBT.PLAYER_NAME, creator.name.string)
            blockEntityTag.putUUID(Constants.NBT.PLAYER_UUID, creator.uuid)
        }

        if (target != null) {
            val nbtCompound = CompoundTag()
            NbtUtils.writeGameProfile(nbtCompound, target.gameProfile)
            blockEntityTag.put(Constants.NBT.STATUE_OWNER, nbtCompound)
        }

        blockEntityTag.putInt(Constants.NBT.POSE, pose)
        stack.tag!!.put(Constants.NBT.BLOCK_ENTITY_TAG, blockEntityTag)
        return stack
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.ENTITYBLOCK_ANIMATED
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.ROTATION_16, BlockStateProperties.WATERLOGGED)
        super.createBlockStateDefinition(builder)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val state = this.defaultBlockState().setValue(BlockStateProperties.ROTATION_16, Mth.floor( (context.rotation * 16.0F / 360.0F) + 0.5) % 15)

        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            val fluidState = context.level.getFluidState(context.clickedPos);
            val source = fluidState.`is`(FluidTags.WATER) && fluidState.amount == 8;
            return state.setValue(BlockStateProperties.WATERLOGGED, source);
        }
        return state
     }
}