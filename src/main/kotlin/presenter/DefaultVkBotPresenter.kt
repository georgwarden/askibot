package presenter

import com.google.gson.Gson
import data.Attachment
import data.VkPhoto
import data.VkUpdate
import domain.FilesInteractor
import domain.ImageFetcher
import domain.ImageTransformer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import view.BotView
import java.net.URL

class DefaultVkBotPresenter(
    private val bot: BotView,
    private val gson: Gson,
    private val transformer: ImageTransformer,
    private val presentationScope: CoroutineScope,
    private val imageFetcher: ImageFetcher,
    private val filesInteractor: FilesInteractor
) : VkBotPresenter {

    override fun processUpdates(updates: List<VkUpdate>) {
        presentationScope.launch {
            val cats = updates.groupBy { it.type }
            cats[VkUpdate.Type.NewMessage]?.forEach {
                val message = gson.fromJson(it.target, VkUpdate.Message::class.java)
                val attachmentCats = message.attachments.groupBy { a -> a.type }
                attachmentCats[Attachment.Type.Photo]
                    ?.asSequence()
                    ?.map { a -> gson.fromJson(a.content, VkPhoto::class.java) }
                    ?.forEach { photo ->
                        photo.sizes
                            .sortedBy { opt -> opt.type }
                            .last()
                            .runCatching {
                                URL(src)
                            }.getOrNull()
                            ?.let { url ->
                                withContext(Dispatchers.IO) {
                                    imageFetcher.fetch(url)
                                }
                            }
                            ?.let { src -> transformer.transform(src) }
                            ?.let { res -> filesInteractor.saveImage(res) }
                            ?.also { art ->
                                bot.respond(message.sender, art)
                                filesInteractor.deleteImage(art)
                            }
                    }
            }
        }
    }

}