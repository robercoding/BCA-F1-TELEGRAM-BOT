package help

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object DateUtils {
    val calendar = Calendar.getInstance()

    fun getWeekDayOfYear(date: Date): Int {
        calendar.time = date
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }
}

fun Date.getWeekDayOfYear(): Int {
    val calendar = DateUtils.calendar
    calendar.time = this
    return calendar.get(Calendar.WEEK_OF_YEAR)
}

fun Date.formatMonthDay(locale: Locale = Locale.US): String {
    val dateMonthDayYear = SimpleDateFormat("MMMM dd, YYYY", locale)
    return "${dateMonthDayYear.format(this)}"
}

fun Date.formatMonthDayHourMinuteAndPreferredTimezone(timeZone: String = "GMT", locale: Locale = Locale.US): String {
    val dateMonthDayYear = SimpleDateFormat("MMMM dd, YYYY", locale)
    val dateHour = SimpleDateFormat("H:mm")
    dateHour.timeZone = TimeZone.getTimeZone(timeZone)
    return "${dateMonthDayYear.format(this)} at ${dateHour.format(this)}"
}

fun Date.toLocalDateTime(): LocalDateTime {
//    return this.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDateTime()
    return this.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().toString())).toLocalDateTime()
}

fun LocalDateTime.toDate(): Date {
    return Timestamp.valueOf(this)
}