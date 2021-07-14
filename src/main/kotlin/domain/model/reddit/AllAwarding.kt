package domain.model.reddit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllAwarding(
    @Json(name = "award_sub_type")
    val awardSubType: String,
    @Json(name = "award_type")
    val awardType: String,
    @Json(name = "awardings_required_to_grant_benefits")
    val awardingsRequiredToGrantBenefits: Any?,
    @Json(name = "coin_price")
    val coinPrice: Int,
    @Json(name = "coin_reward")
    val coinReward: Int,
    @Json(name = "count")
    val count: Int,
    @Json(name = "days_of_drip_extension")
    val daysOfDripExtension: Int,
    @Json(name = "days_of_premium")
    val daysOfPremium: Int,
    @Json(name = "description")
    val description: String,
    @Json(name = "end_date")
    val endDate: Any?,
    @Json(name = "giver_coin_reward")
    val giverCoinReward: Int,
    @Json(name = "icon_format")
    val iconFormat: String,
    @Json(name = "icon_height")
    val iconHeight: Int,
    @Json(name = "icon_url")
    val iconUrl: String,
    @Json(name = "icon_width")
    val iconWidth: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "is_enabled")
    val isEnabled: Boolean,
    @Json(name = "is_new")
    val isNew: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "penny_donate")
    val pennyDonate: Int,
    @Json(name = "penny_price")
    val pennyPrice: Int,
    @Json(name = "resized_icons")
    val resizedIcons: List<ResizedIcon>,
    @Json(name = "resized_static_icons")
    val resizedStaticIcons: List<ResizedStaticIcon>,
    @Json(name = "start_date")
    val startDate: Any?,
    @Json(name = "static_icon_height")
    val staticIconHeight: Int,
    @Json(name = "static_icon_url")
    val staticIconUrl: String,
    @Json(name = "static_icon_width")
    val staticIconWidth: Int,
    @Json(name = "subreddit_coin_reward")
    val subredditCoinReward: Int,
    @Json(name = "subreddit_id")
    val subredditId: Any?,
    @Json(name = "tiers_by_required_awardings")
    val tiersByRequiredAwardings: Any?
)