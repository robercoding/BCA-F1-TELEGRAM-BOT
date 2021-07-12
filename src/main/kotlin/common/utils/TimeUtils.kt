package common.utils

import java.time.DayOfWeek

fun Int.toDayOfWeek(): DayOfWeek {
    if (this < 1 || this > 7) throw DayOfWeekUnsupportedNumber(this)
    return DayOfWeek.values()[this - 1]
}