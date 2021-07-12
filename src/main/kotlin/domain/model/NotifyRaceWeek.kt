package domain.model

data class NotifyRaceWeek(
    var id: Long = -1,
    var isActivated: Boolean,
    var day: Int = 3, //Goes from 1-7 [MONDAY-SUNDAY]
    var hour: Int = 12,
    var minute: Int = 0
)
