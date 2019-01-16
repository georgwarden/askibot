package repository

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class DefaultFilesRepository : FilesRepository {

    override suspend fun saveImage(image: BufferedImage): File {
        
    }

    override suspend fun deleteImage(file: File) {
    }
}