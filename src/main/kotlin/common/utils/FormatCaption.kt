package common.utils

import domain.model.NotifyRaceWeekSettled
import domain.model.dto.RaceDTO
import help.formatMonthDay
import help.formatMonthDayHourMinuteAndPreferredTimezone

object FormatCaption {

    fun formatCalendar(season: Int, races: List<RaceDTO>): String {
        val builder = StringBuilder("Here is the calendar of the season $season:\n")
        races.forEach {
            builder.append("\n*${it.grandPrix.name}:*\n")
            builder.append("\t\t\t\t*Qualifying:* ${it.dateQualifying.formatMonthDay()}\n")
            builder.append("\t\t\t\t*Race*: ${it.dateRace.formatMonthDay()}\n")
            builder.append("More information about the race:\n/Race${it.id}\n")
        }

        return builder.toString()
    }

    fun formatRaceDetails(race: RaceDTO, timeZone: String): String {
        val builder = StringBuilder()
        builder.append("*${race.grandPrix.name}*\n")
        builder.append("\t\t\t\t*City & Country:* ${race.grandPrix.circuit.city}, ${race.grandPrix.circuit.country}\n")
        builder.append("\t\t\t\t*Circuit:* ${race.grandPrix.circuit.name}, ${race.grandPrix.circuit.country}\n")
        if (race.isSprintQualifying) {
            builder.append(
                "\t\t\t\t*Sprint Qualifying:* ${
                    race.dateSprintQuailifying.formatMonthDayHourMinuteAndPreferredTimezone(
                        timeZone
                    )
                }\n"
            )
        }


        val dateQualifying = race.dateQualifying.formatMonthDayHourMinuteAndPreferredTimezone(timeZone = timeZone)
        val dateRace = race.dateRace.formatMonthDayHourMinuteAndPreferredTimezone(timeZone = timeZone)

        builder.append("\t\t\t\t*Qualifying:* $dateQualifying \n")
        builder.append("\t\t\t\t*Race:* $dateRace")

        return builder.toString()
    }

    fun formatSetAlarm(notifyRaceWeekSettled: NotifyRaceWeekSettled, timeZone: String): String {
        println("lets format set alarm")
        val builder = StringBuilder()
        val notifyInDays = notifyRaceWeekSettled.notifyInDays
        val notifyInHours = notifyRaceWeekSettled.notifyInHours
        val notifyInMinutes = notifyRaceWeekSettled.notifyInMinutes

        builder.append("We'll notify you about race week in:\n")
        if (notifyInDays > 0) {
            builder.append("*$notifyInDays ${if (notifyInDays > 1) "days" else "day"}")
            if (notifyInHours > 0 && notifyInMinutes > 0) {
                builder.append(",")
            } else if (notifyInHours > 0 || notifyInMinutes > 0) {
                builder.append(" and ")
            }
        }

        if (notifyInHours > 0) {
            builder.append("\t $notifyInHours ${if (notifyInHours > 1) "hours" else "hour"}")
            if (notifyInMinutes > 0) builder.append(" and ")
        }

        if (notifyInMinutes > 0) {
            builder.append("$notifyInMinutes ${if (notifyInMinutes > 1) "minutes" else "minute"}")
        }


        val dayOfWeek = notifyRaceWeekSettled.everyDayOfWeek
        val everyHour = notifyRaceWeekSettled.everyHour
        val everyMinute = notifyRaceWeekSettled.everyMinute

        builder.append(
            "*\n\nAfter that it will notify you every *$dayOfWeek at ${
                String.format(
                    "%02d",
                    everyHour
                )
            }:${String.format("%02d", everyMinute)}* in your actual *timezone $timeZone*"
        )
        return builder.toString()
    }
}