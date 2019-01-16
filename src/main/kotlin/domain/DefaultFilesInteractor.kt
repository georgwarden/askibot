package domain

import repository.FilesRepository
import java.awt.image.BufferedImage
import java.io.File

class DefaultFilesInteractor(
    private val repository: FilesRepository
) : FilesInteractor {

    override suspend fun saveImage(image: BufferedImage): File {
        return repository.saveImage(image)
    }

    override suspend fun deleteImage(file: File) {
        repository.deleteImage(file)
    }

}