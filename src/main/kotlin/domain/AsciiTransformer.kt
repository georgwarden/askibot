package domain

import util.ConvColor
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

class AsciiTransformer : ImageTransformer {

    override fun transform(src: BufferedImage): BufferedImage {
        val ratio = with(src) { width.toFloat() / height.toFloat() }

        AbstractSides(ratio, STANDARD_SIZE).run {
            val dest = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)



            val horizontalFolding =
                (src.width.toFloat() /
                        (width.toFloat() / EMP_FONT_SIZE.toFloat())
                        ).roundToInt()
            val verticalFolding =
                (src.height.toFloat() /
                        (height.toFloat() / EMP_FONT_SIZE.toFloat())
                        ).roundToInt()

            var min = 0x00ffffff
            var max = 0x00000000
            for (i in 0 until src.width)
                for (j in 0 until src.height) {
                    src.getRGB(i, j).and(0x00ffffff).let {
                        if (it < min) min = it
                        if (it > max) max = it
                    }
                }
            val colorRanges = sequence {
                val step = (max - min) / symbols.size
                for (i in min..max step step)
                    yield(i..(i + step))
            }
            val map = Array(src.width / horizontalFolding) {
                CharArray(src.height / verticalFolding) { ' ' }
            }

            for (o1 in 0 until src.width step horizontalFolding)
                for (o2 in 0 until src.height step verticalFolding) {
                    var accumulatorR = 0
                    var accumulatorG = 0
                    var accumulatorB = 0
                    var count = 0
                    for (i1 in o1 until (o1 + horizontalFolding))
                        for (i2 in o2 until (o2 + verticalFolding)) {
                            with(src.getRGB(i1, i2)) {
                                accumulatorR += and(0x00ff0000).shr(16)
                                accumulatorG += and(0x0000ff00).shr(8)
                                accumulatorB += and(0x000000ff)
                            }
                            count++
                        }
                    val avgR = accumulatorR / count
                    val avgG = accumulatorG / count
                    val avgB = accumulatorB / count
                    val avgColor = ConvColor.fromRgb(avgR, avgG, avgB)
                    colorRanges.find { avgColor.int() in it }?.also {
                        val index = colorRanges.indexOf(it)
                        map[o1 / verticalFolding][o2 / horizontalFolding] = symbols[index]
                    }
                }

            dest.createGraphics().run {
                color = Color.BLACK
                drawRect(0, 0, width, height)
                fillRect(0, 0, width, height)

                color = Color.WHITE
                map.forEachIndexed { i, row ->
                    row.forEachIndexed { j, char ->
                        drawString(char.toString(), i * 12 + 6, j * 12 + 6)
                    }
                }
            }

            src.flush()
            dest.flush()

            return dest
        }

    }

    private class AbstractSides(val ratio: Float, val oneSide: Int) {

        val width: Int
            get() = if (ratio >= 1) oneSide else (oneSide * ratio).roundToInt()

        val height: Int
            get() = if (ratio >= 1) (oneSide / ratio).roundToInt() else oneSide

    }

    companion object {

        const val STANDARD_SIZE = 576
        const val EMP_FONT_SIZE = 12

        val symbols = listOf(
            ' ', '.', ':', ',',
            '-', ';', '"', '~',
            '_', '!', '*', '+',
            '0', '=', '$', '#',
            '%', '@', 'M'
        )

    }

}