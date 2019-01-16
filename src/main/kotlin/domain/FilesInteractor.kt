package domain

import java.awt.image.BufferedImage
import java.io.File

interface FilesInteractor {

    suspend fun saveImage(image: BufferedImage): File
    suspend fun deleteImage(file: File)

}