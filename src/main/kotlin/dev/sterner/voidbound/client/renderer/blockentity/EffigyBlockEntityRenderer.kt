package dev.sterner.voidbound.client.renderer.blockentity

import com.mojang.authlib.GameProfile
import com.mojang.authlib.minecraft.MinecraftProfileTexture
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import dev.sterner.voidbound.Voidbound
import dev.sterner.voidbound.common.block.entity.EffigyBlockEntity
import dev.sterner.voidbound.util.ColorUtil
import it.unimi.dsi.fastutil.ints.Int2IntMap
import it.unimi.dsi.fastutil.ints.IntList
import net.minecraft.client.Minecraft
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.VillagerModel
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.client.renderer.texture.HttpTexture
import net.minecraft.client.resources.DefaultPlayerSkin
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.FastColor
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.function.Consumer
import kotlin.math.min


class EffigyBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<EffigyBlockEntity> {

    private var defaultTexture: WoodTexture = WoodTexture(DefaultPlayerSkin.getDefaultSkin(), false)
    private var steveModel: PlayerModel<Player> = PlayerModel(ctx.bakeLayer(ModelLayers.PLAYER), false)
    private var alexModel: PlayerModel<Player> = PlayerModel(ctx.bakeLayer(ModelLayers.PLAYER_SLIM), true)

    private val villagerModel = VillagerModel<Villager>(ctx.bakeLayer(ModelLayers.VILLAGER));
    private val villagerTexture: WoodTexture = WoodTexture(ResourceLocation("textures/entity/villager/villager.png"), false);

    private val poses: MutableList<Consumer<PlayerModel<Player>>> = mutableListOf()

    companion object {
        @JvmStatic
        val TEXTURE_CACHE: MutableMap<GameProfile, WoodTexture> = mutableMapOf()
    }

    init {
        steveModel.young = false
        alexModel.young = false
        this.villagerModel.young = false
        this.villagerTexture.updateTexture()
        poses.add { model ->
            setRotationAngle(model.head, -0.1047f, 0.0873f, 0.0f)
            model.hat.copyFrom(model.head)
            setRotationAngle(model.body, 0.0f, 0.0f, 0.0f)
            model.jacket.copyFrom(model.body)
            setRotationAngle(model.rightArm, -0.3927f, 0.0f, 0.0f)
            model.rightSleeve.copyFrom(model.rightArm)
            setRotationAngle(model.leftArm, 0.0349f, 0.0f, 0.0f)
            model.leftSleeve.copyFrom(model.leftArm)
            setRotationAngle(model.rightLeg, 0.192f, 0.0f, 0.0349f)
            model.rightPants.copyFrom(model.rightPants)
            setRotationAngle(model.leftLeg, -0.1745f, 0.0f, -0.0349f)
            model.leftLeg.copyFrom(model.leftPants)
        }
        poses.add { model ->
            setRotationAngle(model.head, -0.3665f, 0.3927f, 0.0f)
            model.hat.copyFrom(model.head)
            setRotationAngle(model.body, 0.0f, 0.0f, 0.0f)
            model.jacket.copyFrom(model.body)
            setRotationAngle(model.rightArm, -2.8798f, 0.0f, -0.3927f)
            model.rightSleeve.copyFrom(model.rightArm)
            setRotationAngle(model.leftArm, 0.4712f, 0.0f, -0.2182f)
            model.leftSleeve.copyFrom(model.leftArm)
            setRotationAngle(model.rightLeg, 0.192f, 0.0f, 0.0349f)
            model.rightPants.copyFrom(model.rightPants)
            setRotationAngle(model.leftLeg, -0.1745f, 0.0f, -0.0349f)
            model.leftLeg.copyFrom(model.leftPants)
        }
        poses.add { model ->
            setRotationAngle(model.head, -0.3665f, 0.0873f, 0.0f)
            model.hat.copyFrom(model.head)
            setRotationAngle(model.body, 0.0f, 0.0f, 0.0f)
            model.jacket.copyFrom(model.body)
            setRotationAngle(model.rightArm, -2.7489f, 0.0f, -0.1745f)
            model.rightSleeve.copyFrom(model.rightArm)
            setRotationAngle(model.leftArm, -2.714f, 0.0f, 0.1745f)
            model.leftSleeve.copyFrom(model.leftArm)
            setRotationAngle(model.rightLeg, -0.2443f, 0.0f, 0.0349f)
            model.rightPants.copyFrom(model.rightPants)
            setRotationAngle(model.leftLeg, 0.0436f, 0.0f, -0.0349f)
            model.leftLeg.copyFrom(model.leftPants)
        }
    }

    private fun setRotationAngle(part: ModelPart, pitch: Float, yaw: Float, roll: Float) {
        part.xRot = pitch
        part.yRot = yaw
        part.zRot = roll
    }

    override fun render(pBlockEntity: EffigyBlockEntity,
                        pPartialTick: Float,
                        pPoseStack: PoseStack,
                        pBuffer: MultiBufferSource,
                        pPackedLight: Int,
                        pPackedOverlay: Int) {

        pPoseStack.pushPose()
        val rotation = pBlockEntity.blockState.getValue(BlockStateProperties.ROTATION_16)
        pPoseStack.translate(0.5, 1.5, 0.5)
        pPoseStack.mulPose(Axis.YN.rotationDegrees((0.125f / 2f) * rotation * 360f))
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(180f))

        renderPlayer(pBlockEntity.getStatueProfile(), pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pBlockEntity.pose)

        pPoseStack.popPose()
    }

    private fun renderPlayer(statueProfile: GameProfile?, pPoseStack: PoseStack, pBuffer: MultiBufferSource, pPackedLight: Int, pPackedOverlay: Int, pose: Int) {

        if (statueProfile != null) {
            val layer: RenderType = getLayer(statueProfile)
            val model: PlayerModel<Player> = getModel(statueProfile)
            poseStatue(pose, model)
            model.renderToBuffer(pPoseStack, pBuffer.getBuffer(layer), pPackedLight, pPackedOverlay, 1f, 1f, 1f, 1f)
        } else {
            val layer: RenderType = villagerTexture.renderType
            villagerTexture.updateTexture()
            villagerModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(layer), pPackedLight, pPackedOverlay, 1f, 1f, 1f, 1f)
        }
    }

    private fun poseStatue(pose: Int, model: PlayerModel<Player>) {
        if (pose >= poses.size) {
            setRotationAngle(model.head, 0.0f, 0.0f, 0.0f)
            model.head.copyFrom(model.hat)
            setRotationAngle(model.body, 0.0f, 0.0f, 0.0f)
            model.body.copyFrom(model.jacket)
            setRotationAngle(model.rightArm, 0.0f, 0.0f, (Math.PI / 2f).toFloat())
            model.rightSleeve.copyFrom(model.rightArm)
            setRotationAngle(model.leftArm, 0.0f, 0.0f, -(Math.PI / 2f).toFloat())
            model.leftSleeve.copyFrom(model.leftArm)
            setRotationAngle(model.rightLeg, 0.0f, 0.0f, 0.0f)
            model.rightPants.copyFrom(model.rightPants)
            setRotationAngle(model.leftLeg, 0.0f, 0.0f, 0.0f)
            model.leftLeg.copyFrom(model.leftPants)
            return
        }
        poses[pose].accept(model)
    }

    private fun getModel(profile: GameProfile?): PlayerModel<Player> {
        return if (profile != null && profile.id != null && DefaultPlayerSkin.getSkinModelName(profile.id).equals("default")) steveModel else alexModel
    }

    private fun getLayer(profile: GameProfile): RenderType {
        return getStoneTexture(profile).renderType
    }

    private fun getStoneTexture(profile: GameProfile?): WoodTexture {
        return if (profile == null) defaultTexture else TEXTURE_CACHE.compute(profile
        ) { _: Any?, woodTexture: WoodTexture? ->
            if (woodTexture == null) {
                val minecraftClient: Minecraft = Minecraft.getInstance()
                val map = minecraftClient.skinManager.getInsecureSkinInformation(profile)
                if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                    try {
                        val skin = MinecraftProfileTexture.Type.SKIN
                        return@compute WoodTexture(minecraftClient.skinManager
                            .registerTexture(map[skin]!!, skin), true)
                    } catch (e: NullPointerException) {
                        return@compute defaultTexture
                    }
                } else {
                    return@compute WoodTexture(DefaultPlayerSkin.getDefaultSkin(profile.id), false)
                }
            } else {
                if (woodTexture.needsUpdate) {
                    woodTexture.updateTexture()
                }
                return@compute woodTexture
            }
        }!!
    }

    class WoodTexture(private var base: ResourceLocation, private var playerSkin: Boolean) : AutoCloseable {

        private val woodTexture: ResourceLocation = ResourceLocation("textures/block/stone.png")
        private var textureManager = Minecraft.getInstance().getTextureManager()
        private var resourceManager = Minecraft.getInstance().resourceManager
        private var texture: DynamicTexture = DynamicTexture(NativeImage(64, 64, true))
        private val id: ResourceLocation = textureManager.register("void_wood/" + base.path, texture)
        var renderType: RenderType = RenderType.entityCutout(id)

        var needsUpdate: Boolean = true

        override fun close() {
            this.texture.close()
        }

        fun updateTexture() {
            Minecraft.getInstance().execute {
                try {
                    val inputImage: NativeImage = if (!playerSkin) {
                        NativeImage.read(resourceManager.getResource(base).get().open())
                    } else {
                        getPlayerSkin(base)
                    }
                    val stoneImage: NativeImage = NativeImage.read(resourceManager.getResource(woodTexture).get().open())
                    val skinPalette: IntList = ColorUtil.getPaletteFromImage(inputImage)
                    val colorMap: Int2IntMap = ColorUtil.associateGrayscale(skinPalette)
                    val height = min(texture.pixels!!.height, inputImage.height)
                    val width = min(texture.pixels!!.width, inputImage.width)

                    for (y in 0 until height) {
                        for (x in 0 until width) {
                            var color = colorMap[inputImage.getPixelRGBA(x, y)]
                            color = FastColor.ARGB32.multiply(color, stoneImage.getPixelRGBA(x % stoneImage.width, y % stoneImage.height))
                            texture.pixels!!.setPixelRGBA(x, y, color)
                        }
                    }
                    needsUpdate = false
                    inputImage.close()
                    texture.upload()
                } catch (e: Exception) {
                    if (e is FileNotFoundException) {
                        Voidbound.LOGGER.info("Retrieving player texture file...")
                    } else {
                        Voidbound.LOGGER.error("Could not update stone texture.", e)
                    }
                }
            }
        }
        @Throws(IOException::class)
        private fun getPlayerSkin(base: ResourceLocation): NativeImage {
            val skinTexture: HttpTexture = textureManager.getTexture(base) as HttpTexture
            val file: File = skinTexture.file!!

            return skinTexture.processLegacySkin(NativeImage.read(FileInputStream(file)))!!
        }
    }

    class BuiltinItemStatueRenderer(dispatcher: BlockEntityRenderDispatcher, modelSet: EntityModelSet) : BlockEntityWithoutLevelRenderer(dispatcher, modelSet) {
        override fun renderByItem(pStack: ItemStack,
                                  pDisplayContext: ItemDisplayContext,
                                  pPoseStack: PoseStack,
                                  pBuffer: MultiBufferSource,
                                  pPackedLight: Int,
                                  pPackedOverlay: Int) {

        }
    }
}