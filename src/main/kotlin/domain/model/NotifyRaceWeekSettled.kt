package domain.model

import java.time.DayOfWeek

data class NotifyRaceWeekSettled(
    val notifyInDays: Int,
    val notifyInHours: Int,
    val notifyInMinutes: Int,
    val everyDayOfWeek: DayOfWeek,
    val everyHour: Int,
    val everyMinute: Int,
    val timeZone: String?
)
