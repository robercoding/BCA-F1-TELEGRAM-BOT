package help

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatToTimezoneGMT(date: Date) : String{
        val dateFormat = SimpleDateFormat("dd-MM H:mm a")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")

        return dateFormat.format(date)
    }

    fun formatDayAndMonthToTimezoneGMT(date: Date) :String {
        val dateFormat = SimpleDateFormat("dd-MM")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(date)
    }

    fun formatHourAndMinuteToTimezoneGMT(date: Date) :String {
        val dateFormat = SimpleDateFormat("H:mm a")
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        return dateFormat.format(date)
    }
}