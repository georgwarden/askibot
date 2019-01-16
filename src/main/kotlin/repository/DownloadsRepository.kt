package repository

import java.awt.image.BufferedImage
import java.net.URL

interface DownloadsRepository {

    suspend fun downloadImage(url: URL): BufferedImage

}