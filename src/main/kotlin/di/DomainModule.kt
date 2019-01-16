package di

import domain.*
import org.koin.dsl.module.module

val DomainModule = module {
    single { AsciiTransformer() as ImageTransformer }
    single { DefaultImageFetcher(get()) as ImageFetcher }
    single { DefaultFilesInteractor(get()) as FilesInteractor }
}