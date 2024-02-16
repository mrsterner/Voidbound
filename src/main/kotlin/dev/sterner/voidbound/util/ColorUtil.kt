package dev.sterner.voidbound.util

import com.mojang.blaze3d.platform.NativeImage
import it.unimi.dsi.fastutil.ints.*
import net.minecraft.util.Mth
import java.util.stream.Collectors
import kotlin.math.floor


object ColorUtil {

    const val BLACK: Int = -0x1000000
    const val WHITE: Int = -0x1
    const val TEXT_COLOR: Int = -0x1f1f20
    const val UNEDITABLE_COLOR: Int = -0x8f8f90
    val GRAYSCALE_PALETTE: IntArrayList = IntArrayList
        .wrap(intArrayOf(-0x666667, -0x555556, -0x444445, -0x333334, -0x222223, -0x111112, -0x1))

    fun getColorsFromImage(image: NativeImage): IntSet {
        val colors = IntOpenHashSet()

        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                val color: Int = image.getPixelRGBA(x, y)

                if (argbUnpackAlpha(color) == 255) {
                    colors.add(color)
                }
            }
        }

        return colors
    }

    fun getPaletteFromImage(image: NativeImage): IntList {
        val colors = getColorsFromImage(image)

        // convert the IntStream into a generic stream using `boxed` to be able to supply a custom ordering
        return IntArrayList(colors.intStream().boxed().sorted { color0, color1 ->
            val lum0 = luminance(color0)
            val lum1 = luminance(color1)
            java.lang.Float.compare(lum0, lum1)
        }.collect(Collectors.toList()))
    }

    fun getPaletteFromImage(inputImage: NativeImage, expectColors: Int): IntList {
        val palette = getPaletteFromImage(inputImage)

        if (expectColors + 2 < palette.size) {
            val reducedPalette = IntArrayList()

            var lastLuminance = -1f

            for (i in palette.indices) {
                val color = palette.getInt(i)
                val luminance: Float = luminance(color)

                if (Mth.abs(luminance - lastLuminance) < 3.1f) {
                    continue
                }

                lastLuminance = luminance

                reducedPalette.add(color)
            }

            return reducedPalette
        }

        return palette
    }

    fun associateGrayscale(skinPalette: IntList): Int2IntMap {
        val map = Int2IntArrayMap()
        val sizeFactor: Float = GRAYSCALE_PALETTE.size / skinPalette.size.toFloat()
        for (i in 0 until skinPalette.size) {
            map.put(skinPalette.getInt(i), GRAYSCALE_PALETTE.getInt(floor((i * sizeFactor).toDouble()).toInt()))
        }
        return map
    }

    fun luminance(color: Int): Float {
        return luminance(argbUnpackRed(color), argbUnpackGreen(color), argbUnpackBlue(color))
    }

    fun luminance(red: Int, green: Int, blue: Int): Float {
        return (0.2126f * red + 0.7152f * green + 0.0722f * blue)
    }

    /**
     * Extracts and unpacks the red component of the ARGB color.
     *
     * @param color the ARGB color
     * @return the unpacked red component
     */
    fun argbUnpackRed(color: Int): Int {
        return (color shr 16) and 255
    }

    /**
     * Extracts and unpacks the green component of the ARGB color.
     *
     * @param color the ARGB color
     * @return the unpacked green component
     */
    fun argbUnpackGreen(color: Int): Int {
        return (color shr 8) and 255
    }

    /**
     * Extracts and unpacks the blue component of the ARGB color.
     *
     * @param color the ARGB color
     * @return the unpacked blue component
     */
    fun argbUnpackBlue(color: Int): Int {
        return color and 255
    }

    /**
     * Extracts and unpacks the alpha component of the ARGB color.
     *
     * @param color the ARGB color
     * @return the unpacked alpha component
     */
    fun argbUnpackAlpha(color: Int): Int {
        return (color shr 24) and 255
    }
}