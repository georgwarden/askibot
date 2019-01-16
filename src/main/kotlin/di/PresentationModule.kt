package di

import org.koin.dsl.module.module
import presenter.VkBotPresenter
import presenter.DefaultVkBotPresenter

val PresentationModule = module {
    single { DefaultVkBotPresenter() as VkBotPresenter }
}