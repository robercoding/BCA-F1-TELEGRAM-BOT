package domain.model.reddit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PollData(
    @Json(name = "is_prediction")
    val isPrediction: Boolean,
    @Json(name = "options")
    val options: List<Option>,
    @Json(name = "resolved_option_id")
    val resolvedOptionId: Any?,
    @Json(name = "total_stake_amount")
    val totalStakeAmount: Any?,
    @Json(name = "total_vote_count")
    val totalVoteCount: Int,
    @Json(name = "tournament_id")
    val tournamentId: Any?,
    @Json(name = "user_selection")
    val userSelection: Any?,
    @Json(name = "user_won_amount")
    val userWonAmount: Any?,
    @Json(name = "voting_end_timestamp")
    val votingEndTimestamp: Long
)