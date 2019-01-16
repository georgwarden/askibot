package di

import org.koin.dsl.module.module

object Secrets {
    const val VkGroupId = "vk group id"
    const val VkApiToken = "vk api key"
}

val SecretModule = module {
    single(name = Secrets.VkGroupId) { "153243160" }
    single(name = Secrets.VkApiToken) { "c69456a1d990a6d896eb12e7ea7b8cc20da8ad4f7b8bd84a0360e049020676fad3c8f799ea64060eef38d" }
}