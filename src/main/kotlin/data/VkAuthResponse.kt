package data

import com.google.gson.annotations.SerializedName

data class VkAuthResponse(
    @SerializedName("response")
    val longPoll: LongPollConfig
)

data class LongPollConfig(
    @SerializedName("key")
    val key: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("ts")
    val timestamp: String
)