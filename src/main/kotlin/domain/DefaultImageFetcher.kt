package domain

import repository.DownloadsRepository
import java.awt.image.BufferedImage
import java.net.URL

class DefaultImageFetcher(
    private val repository: DownloadsRepository
) : ImageFetcher {

    override suspend fun fetch(url: URL): BufferedImage {
        return repository.downloadImage(url)
    }

}