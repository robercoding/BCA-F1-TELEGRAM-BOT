package presentation.functionality.schedule

import domain.model.NotifyRaceWeek
import domain.model.NotifyRaceWeekSettled
import domain.model.TimerTaskAndNotifyRaceWeek
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

    fun getTimerTask(notifyRaceWeek: NotifyRaceWeek, notifyRace: () -> Unit): TimerTaskAndNotifyRaceWeek {
        val dayOfWeek = getDayOfWeek(notifyRaceWeek.day)
        val hour = notifyRaceWeek.hour
        val minute = notifyRaceWeek.minute

        val delay = getDelay(dayOfWeek, hour, minute)
        val period = weekInMilliseconds

        val timerTask = Timer(timerName, true).scheduleAtFixedRate(delay, period) {
            notifyRace()
        }

        val seconds: Long = delay / 1000
        val notifyInMinutes = seconds / 60
        val notifyInHours = (notifyInMinutes / 60)
        val notifyInDays = notifyInHours / 24

        val alarmSettled = NotifyRaceWeekSettled(
            notifyInDays = notifyInDays.toInt(),
            notifyInHours = notifyInHours.toInt() % 24,
            notifyInMinutes = notifyInMinutes.toInt() % 60,
            everyDayOfWeek = dayOfWeek,
            everyHour = hour,
            everyMinute = minute,
            TimeZone.getTimeZone(zoneId)
        )

        return TimerTaskAndNotifyRaceWeek(timerTask, alarmSettled)
    }

    private fun getDayOfWeek(day: Int): DayOfWeek = DayOfWeek.values()[day - 1]

    private fun getDelay(dayOfWeek: DayOfWeek, hour: Int, minute: Int): Long {
        val nextAlarmEpoch = getNextAlarmEpoch(dayOfWeek, hour, minute)
        val nowEpoch = getEpochNow()
        return nextAlarmEpoch - nowEpoch
    }

    private fun getNextAlarmEpoch(dayOfWeek: DayOfWeek, hour: Int, minute: Int): Long {
        val now = LocalDateTime.now().atZone(zoneId)

        //If they're the same day and today's time is previous to alarm's time then. If not it will set for next week
        println("hour alarm $hour")
        println("hour now ${LocalDateTime.now().atZone(zoneId).hour}")
        val nextAlarmTime =
            if (dayOfWeek == now.dayOfWeek && ((hour > now.hour) || (hour == now.hour && minute >= now.minute))) {
                println("for today")
                now.withHour(hour).withMinute(minute).withSecond(0)
            } else {
                now.with(TemporalAdjusters.next(dayOfWeek)).withHour(hour).withMinute(minute).withSecond(0)
            }
        return nextAlarmTime.toInstant().toEpochMilli()
    }

    private fun getEpochNow(): Long {
        return LocalDateTime.now().atZone(zoneId).toInstant().toEpochMilli()
    }

    fun isTodayThursdayOrGreater(): Boolean {
        val day = getDayOfTheWeek()
        return day == DAYS.TUESDAY.name || day == DAYS.FRIDAY.name || day == DAYS.SATURDAY.name || day == DAYS.SUNDAY.name
    }

    private fun getDayOfTheWeek(): String {
        return LocalDate.now().dayOfWeek.name
    }
}

enum class DAYS_OF_WEEK {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}