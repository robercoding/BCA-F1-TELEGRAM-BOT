package domain.model

data class NotifyRaceWeek(
    var isActivated: Boolean = false,
    var day: Int? = null,
    var hour: Int? = null,
    var minute: Int? = null
)
