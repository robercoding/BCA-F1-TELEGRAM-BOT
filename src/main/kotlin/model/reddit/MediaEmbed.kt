package model.reddit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaEmbed(
    @Json(name = "content")
    val content: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "scrolling")
    val scrolling: Boolean,
    @Json(name = "width")
    val width: Int
)