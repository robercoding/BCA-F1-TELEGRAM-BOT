package model.reddit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditPost(
    @Json(name = "data")
    val `data`: Data,
    @Json(name = "kind")
    val kind: String
)