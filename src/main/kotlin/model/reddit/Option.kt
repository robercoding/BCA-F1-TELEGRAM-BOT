package model.reddit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Option(
    @Json(name = "id")
    val id: String,
    @Json(name = "text")
    val text: String
)