package domain.model

data class NotifyRaceWeek(
    var isActivated: Boolean = false,
    var day: Int = 3, //Goes from 1-7 [MONDAY-SUNDAY]
    var hour: Int = 12,
    var minute: Int = 0
)
