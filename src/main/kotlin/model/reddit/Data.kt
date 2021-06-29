package model.reddit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "after")
    val after: String,
    @Json(name = "before")
    val before: Any?,
    @Json(name = "children")
    val children: List<Children>,
    @Json(name = "dist")
    val dist: Int,
    @Json(name = "modhash")
    val modhash: String
)