package domain

import java.awt.image.BufferedImage

interface ImageTransformer {

    fun transform(src: BufferedImage): BufferedImage

}