package util

/**
 * dramatically ignores alphas
 */
inline class ConvColor(private val intRepresentation: Int) {

    fun red() = intRepresentation.and(RED_MASK).shr(16)
    fun green() = intRepresentation.and(GREEN_MASK).shr(8)
    fun blue() = intRepresentation.and(BLUE_MASK)

    fun int() = intRepresentation

    companion object {

        const val RED_MASK = 0x00ff0000
        const val GREEN_MASK = 0x0000ff00
        const val BLUE_MASK = 0x000000ff

        fun fromRgb(r: Int, g: Int, b: Int): ConvColor {
            return ConvColor(r.shl(16) + g.shl(8) + b)
        }

        fun fromRgbTriple(triple: Triple<Int, Int, Int>): ConvColor {
            val (r, g, b) = triple
            return ConvColor(r.shl(16) + g.shl(8) + b)
        }

    }

}

fun interpolateMany(vararg colors: Int): ConvColor =
    colors.asSequence()
        .map { ConvColor(it) }
        .fold(Triple(0, 0, 0)) { (r, g, b), color ->
            Triple(
                r + color.red(),
                g + color.green(),
                b + color.red()
            )
        }.let { (tr, tg, tb) ->
            Triple(
                tr / colors.size,
                tg / colors.size,
                tb / colors.size
            )
        }
        .let { ConvColor.fromRgbTriple(it) }