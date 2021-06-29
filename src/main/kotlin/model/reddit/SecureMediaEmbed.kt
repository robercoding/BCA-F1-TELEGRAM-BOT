package model.reddit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SecureMediaEmbed(
    @Json(name = "content")
    val content: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "media_domain_url")
    val mediaDomainUrl: String,
    @Json(name = "scrolling")
    val scrolling: Boolean,
    @Json(name = "width")
    val width: Int
)