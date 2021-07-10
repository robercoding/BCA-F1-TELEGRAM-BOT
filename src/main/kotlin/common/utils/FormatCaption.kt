package common.utils

import help.formatMonthDayHourMinuteAndPreferredTimezone
import model.dto.RaceDTO

object FormatRaceCaption {
    fun formatRaceDetails(race: RaceDTO, timeZone: String) : String {
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
}