package di

import org.koin.dsl.module.module
import repository.DownloadsRepository
import repository.JnioDownloadsRepository

val RepositoryModule = module {
    single { JnioDownloadsRepository() as DownloadsRepository }
}