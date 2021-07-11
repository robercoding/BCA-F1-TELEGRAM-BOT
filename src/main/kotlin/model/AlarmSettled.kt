package model

import java.time.DayOfWeek
import java.util.*

data class AlarmSettled(
    val notifyInDays: Int,
    val notifyInHours: Int,
    val notifyInMinutes: Int,
    val everyDayOfWeek: DayOfWeek,
    val everyHour: Int,
    val everyMinute: Int,
    val timeZone: TimeZone
)
