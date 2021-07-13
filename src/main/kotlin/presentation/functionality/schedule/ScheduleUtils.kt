package presentation.functionality.schedule

import common.utils.toDayOfWeek
import domain.model.NotifyRaceWeek
import domain.model.NotifyRaceWeekSettled
import domain.model.TimerTaskAndNotifyRaceWeek
import domain.model.dao.defaultTimeZone
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

object ScheduleUtils {

    private const val timerName = "RaceWeek"
    private const val weekInMilliseconds = 604800000L

    fun getTimerTask(
        notifyRaceWeek: NotifyRaceWeek,
        timeZone: String?,
        notifyRace: () -> Unit
    ): TimerTaskAndNotifyRaceWeek {
        val dayOfWeek = notifyRaceWeek.day.toDayOfWeek()
        val hour = notifyRaceWeek.hour
        val minute = notifyRaceWeek.minute
        val zoneId = timeZone?.let { ZoneId.of(it) } ?: let { ZoneId.of(defaultTimeZone) }
        println("zone id is ${zoneId.id}")

        val delay = getDelay(dayOfWeek, hour, minute, zoneId)
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
            timeZone
        )

        return TimerTaskAndNotifyRaceWeek(timerTask, alarmSettled)
    }

    private fun getDelay(dayOfWeek: DayOfWeek, hour: Int, minute: Int, zoneId: ZoneId): Long {
        val nextAlarmEpoch = getNextTimeAlarmEpoch(dayOfWeek, hour, minute, zoneId)
        val nowEpoch = getEpochNow(zoneId)
        return nextAlarmEpoch - nowEpoch
    }

    private fun getNextTimeAlarmEpoch(dayOfWeek: DayOfWeek, hour: Int, minute: Int, zoneId: ZoneId): Long {
        val now = LocalDateTime.now(zoneId)

        //If they're the same day and today's time is previous to alarm's time then. If not it will set for next week
        println("hour alarm $hour")
        println("hour now ${LocalDateTime.now(zoneId).hour}")
        val nextAlarmTime =
            if (dayOfWeek == now.dayOfWeek && ((hour > now.hour) || (hour == now.hour && minute >= now.minute))) {
                println("for today")
                now.withHour(hour).withMinute(minute).withSecond(0)
            } else {
                now.with(TemporalAdjusters.next(dayOfWeek)).withHour(hour).withMinute(minute).withSecond(0)
            }
        val zoneOffset = zoneId.rules.getOffset(Instant.now())
        return nextAlarmTime.toInstant(zoneOffset).toEpochMilli()
    }

    private fun getEpochNow(zoneId: ZoneId): Long {
        val zoneOffset = zoneId.rules.getOffset(Instant.now())
        return LocalDateTime.now(zoneId).toInstant(zoneOffset).toEpochMilli()
    }
}