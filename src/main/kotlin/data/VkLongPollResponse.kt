package data

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class VkLongPollResponse(
    @SerializedName("ts")
    val ts: String,
    @SerializedName("updates")
    val updates: List<VkUpdate>,
    @SerializedName("group_id")
    val groupId: Int
)

data class VkUpdate(
    @SerializedName("type")
    val type: Type?,
    @SerializedName("object")
    val target: JsonElement,
    @SerializedName("group_id")
    val groupId: Int
) {

    enum class Type {
        @SerializedName("message_new") NewMessage
    }

    data class Message(
        @SerializedName("id")
        val id: Int,
        @SerializedName("text")
        val text: String,
        @SerializedName("from_id")
        val sender: Int,
        @SerializedName("attachments")
        val attachments: List<Attachment>
    )

}