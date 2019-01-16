package data

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class Attachment(
    @SerializedName("type")
    val type: Type,
    @SerializedName(
        "photo",
        alternate = [
            "audio",
            "video",
            "doc",
            "link",
            "market",
            "market_album",
            "wall",
            "wall_reply",
            "sticker",
            "gift"
        ]
    )
    val content: JsonElement
) {

    enum class Type {
        @SerializedName("photo")
        Photo,
        @SerializedName("video")
        Video,
        @SerializedName("audio")
        Audio,
        @SerializedName("doc")
        Document,
        @SerializedName("link")
        Link,
        @SerializedName("market")
        Goods,
        @SerializedName("market_album")
        Showcase,
        @SerializedName("wall")
        Wall,
        @SerializedName("wall_reply")
        WallReply,
        @SerializedName("sticker")
        Sticker,
        @SerializedName("gift")
        Gift
    }

}

data class VkPhoto(
    val id: Int,
    val sizes: List<Option>
) {

    data class Option(
        val type: Type,
        val src: String,
        val width: Int,
        val height: Int
    )

    enum class Type {
        @SerializedName("o")
        CroppedLeft,
        @SerializedName("p")
        CroppedSmall,
        @SerializedName("q")
        CroppedMedium,
        @SerializedName("r")
        CroppedLarge,
        @SerializedName("s")
        Small,
        @SerializedName("m")
        Medium,
        @SerializedName("x")
        Large,
        @SerializedName("y")
        XLarge,
        @SerializedName("z")
        XXLarge,
        @SerializedName("w")
        XXXLarge
    }

}