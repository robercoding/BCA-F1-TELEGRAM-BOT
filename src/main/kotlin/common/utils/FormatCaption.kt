package common.utils

import domain.model.NotifyRaceWeekSettled
import domain.model.NotifyRaceWeekUnsettled
import domain.model.dao.defaultTimeZone
import domain.model.dto.RaceDTO

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

    //TODO Add timezone to this format alarm
    fun formatSetNotifyRaceWeek(notifyRaceWeekSettled: NotifyRaceWeekSettled): String {
        val builder = StringBuilder()
        val notifyInDays = notifyRaceWeekSettled.notifyInDays
        val notifyInHours = notifyRaceWeekSettled.notifyInHours
        val notifyInMinutes = notifyRaceWeekSettled.notifyInMinutes

        builder.append("We'll notify you about race week in:\n")
        if (notifyInDays > 0) {
            builder.append("*$notifyInDays ${if (notifyInDays > 1) "days" else "day"}*")
            if (notifyInHours > 0 && notifyInMinutes > 0) {
                builder.append("*,* ")
            } else if (notifyInHours > 0 || notifyInMinutes > 0) {
                builder.append(" *and* ")
            }
        }

        if (notifyInHours > 0) {
            builder.append("*$notifyInHours ${if (notifyInHours > 1) "hours" else "hour"}*")
            if (notifyInMinutes > 0) builder.append(" *and* ")
        }

        if (notifyInMinutes > 0) {
            builder.append("*$notifyInMinutes ${if (notifyInMinutes > 1) "minutes" else "minute"}*")
        } else if (notifyInDays == 0 && notifyInHours == 0 && notifyInMinutes == 0) {
            builder.append("*$notifyInMinutes minutes*")
        }

        val dayOfWeek = notifyRaceWeekSettled.everyDayOfWeek
        val everyHour = notifyRaceWeekSettled.everyHour
        val everyMinute = notifyRaceWeekSettled.everyMinute


        builder.append(
            "\n\nAfter that it will notify you *every $dayOfWeek at ${
                String.format(
                    "%02d",
                    everyHour
                )
            }*"
        )

        builder.append(
            "*:${
                String.format(
                    "%02d",
                    everyMinute
                )
            }*"
        )

        if (notifyRaceWeekSettled.timeZone != null) {
            builder.append(" *in your actual timezone ${notifyRaceWeekSettled.timeZone}*")
        } else {
            builder.append(" *in the default timezone $defaultTimeZone* ")
            builder.append("\n\nIf you want to change your timezone please send /setTzCountry CountryName")
            builder.append("\nExample: /setTzCountry Spain")
        }
        return builder.toString()
    }

    fun formatUnsetNotifyRaceWeek(notifyRaceWeekUnsettled: NotifyRaceWeekUnsettled): String {
        val builder = StringBuilder()
        builder.append("Notify when it's race week has been deactivated")
        val notifyRaceWeek = notifyRaceWeekUnsettled.notifyRaceWeek
        val dayOfWeek = notifyRaceWeek.day.toDayOfWeek()
        val everyHour = notifyRaceWeek.hour
        val everyMinute = notifyRaceWeek.minute

        builder.append("\n\nYour actual configuration is the next:")

        builder.append(
            "\n*Every $dayOfWeek at ${
                String.format(
                    "%02d",
                    everyHour
                )
            }*"
        )

        builder.append(
            "*:${
                String.format(
                    "%02d",
                    everyMinute
                )
            }* "
        )

        builder.append("in your actual *timezone ${notifyRaceWeekUnsettled.timeZone.id}*")
        return builder.toString()
    }
}