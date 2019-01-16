package di

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.engine.config
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import org.koin.dsl.module.module

object Clients {
    const val LongPoll = "client long poll"
    const val Common = "client common"
}

val WebModule = module {
    single(name = Clients.Common) { HttpClient(Apache) }
    single(name = Clients.LongPoll) {
        HttpClient(Apache.config {
            connectionRequestTimeout = 30000
        }) {
            install(Logging) {
                this.level = LogLevel.ALL
                this.logger = Logger.DEFAULT
            }
        }
    }
    single { GsonBuilder().create() }
    single { JsonParser() }
}