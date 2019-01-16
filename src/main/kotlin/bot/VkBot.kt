package bot

import com.google.gson.Gson
import data.VkLongPollResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import presenter.VkBotPresenter
import util.Computed
import view.BotView
import java.io.File

class VkBot(
    private val presenter: VkBotPresenter,
    private val client: HttpClient,
    private val commonClient: HttpClient,
    private val gson: Gson,
    private val longPollingScope: CoroutineScope,
    private val requestScope: CoroutineScope,
    @Computed private val serverUrl: String,
    @Computed private val key: String,
    @Computed private val initTs: String
) : KoinComponent, BotView {

    fun start() {
        longPollingScope.launch {
            longPoll(initTs)
        }
    }

    private tailrec suspend fun longPoll(ts: String) {
        val response = client.get<HttpResponse> {
            url(Url(serverUrl))
            parameter("act", "a_check")
            parameter("key", key)
            parameter("ts", ts)
        }.readText()
        val summary = gson.fromJson(response, VkLongPollResponse::class.java)
        presenter.processUpdates(summary.updates)
        longPoll(summary.ts)
    }

    override fun respond(recipient: Int, file: File) {
        requestScope.launch {
            client.get<HttpResponse> {

            }
        }
    }

    companion object

}