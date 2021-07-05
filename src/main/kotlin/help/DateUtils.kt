package help

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object DateUtils {
    private val calendar = Calendar.getInstance()

    fun formatToTimezoneGMT(date: Date): String {
        val dateFormat = SimpleDateFormat("dd-MM H:mm a")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")

        return dateFormat.format(date)
    }

    fun formatDayAndMonthToTimezoneGMT(date: Date): String {
        val dateFormat = SimpleDateFormat("dd-MM")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(date)
    }

    fun formatHourAndMinuteToTimezoneGMT(date: Date): String {
        val dateFormat = SimpleDateFormat("H:mm a")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(date)
    }

    fun getWeekDayOfYear(date: Date): Int {
        calendar.time = date
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }

    fun convertToDateFromLocalDateTime(localDateTime: LocalDateTime): Date {
        return Timestamp.valueOf(localDateTime)
    }

    fun getTimeFromLocalDateTime(localDateTime: LocalDateTime): Long {
        return Timestamp.valueOf(localDateTime).time
    }
}

fun Date.toLocalDateTime(): LocalDateTime {
//    return this.toInstant().atZone(ZoneId.of("Europe/Madrid")).toLocalDateTime()
    return this.toInstant().atZone(ZoneId.of(ZoneId.systemDefault().toString())).toLocalDateTime()
}

fun LocalDateTime.toDate(): Date {
    return Timestamp.valueOf(this)
}