package repository

import java.awt.image.BufferedImage
import java.io.File

interface FilesRepository {

    suspend fun saveImage(image: BufferedImage): File

    suspend fun deleteImage(file: File)

}