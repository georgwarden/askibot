import bot.VkBot
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import data.VkAuthResponse
import data.VkPhoto
import di.*
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin

fun main() {

    val koin = startKoin(
        listOf(
            DomainModule,
            WebModule,
            SecretModule,
            CoroutineModule,
            PresentationModule
        )
    )

    val client = koin.koinContext.get<HttpClient>(Clients.Common)
    val gson = koin.koinContext.get<Gson>()
    val scope = koin.koinContext.get<CoroutineScope>(Scopes.Requests)
    val groupId = koin.koinContext.get<String>(Secrets.VkGroupId)
    val token = koin.koinContext.get<String>(Secrets.VkApiToken)
    scope.launch(Dispatchers.Default) {
        val response = client.get<HttpResponse> {
            url {
                protocol = URLProtocol.HTTPS
                url(
                    host = "api.vk.com",
                    scheme = "https",
                    path = "method/groups.getLongPollServer"
                )
                parameter("group_id", groupId)
                parameter("access_token", token)
                parameter("v", "5.58")
            }
        }.readText()
        val (key, server, ts) = gson.fromJson(response, VkAuthResponse::class.java).longPoll

        val vkBotModule = module {
            single {
                VkBot(
                    presenter = get(),
                    client = get(Clients.LongPoll),
                    commonClient = get(Clients.Common),
                    gson = get(),
                    longPollingScope = get(Scopes.LongPoll),
                    requestScope = get(Scopes.Requests),
                    serverUrl = server,
                    key = key,
                    initTs = ts
                )
            }
        }

        koin.loadModules(listOf(vkBotModule))
        koin.koinContext.get<VkBot>().start()
    }

    while (true) {
    }

}