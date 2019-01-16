package repository

import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

class JnioDownloadsRepository : DownloadsRepository {

    override suspend fun downloadImage(url: URL): BufferedImage {
        return ImageIO.read(url)
    }

}