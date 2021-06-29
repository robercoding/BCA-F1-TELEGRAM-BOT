package functionality.schedule

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

object ScheduleUtils {

    private const val timerName = "RaceWeek"
    private const val weekInMilliseconds = 604800000L
    private var zoneId = ZoneId.of("Europe/Madrid")

    fun getTimerTask(notifyRace : () -> Unit) : TimerTask {
        val delay = getDelay()
        val period = weekInMilliseconds

        return Timer(timerName, true).scheduleAtFixedRate(delay, period) {
            notifyRace()
        }
    }
    private fun getDelay() : Long{
        val nextThursday = getEpochNextThursday()
        val today = getEpochNow()
        return nextThursday - today
    }

    private fun getEpochNextThursday(): Long{
        val dayNow = LocalDateTime.now()
        val nextThursday = dayNow.with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).atZone(zoneId).withHour(8).withMinute(0).withSecond(0)
        return nextThursday.toInstant().toEpochMilli()
    }

    private fun getEpochNow() : Long {
        return LocalDateTime.now().atZone(zoneId).toInstant().toEpochMilli()
    }

    fun isTodayThursdayOrGreater() : Boolean{
        val day = getDayOfTheWeek()
        return day == DAYS.TUESDAY.name || day == DAYS.FRIDAY.name || day == DAYS.SATURDAY.name || day == DAYS.SUNDAY.name
    }

    private fun getDayOfTheWeek() : String{
        return LocalDate.now().dayOfWeek.name
    }
}