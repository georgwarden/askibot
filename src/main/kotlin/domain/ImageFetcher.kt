package domain

import java.awt.image.BufferedImage
import java.net.URL

interface ImageFetcher {

    suspend fun fetch(url: URL): BufferedImage

}