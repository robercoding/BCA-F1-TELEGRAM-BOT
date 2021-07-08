package functionality.action

import common.utils.BotOutcome
import help.formatMonthDay
import help.formatMonthDayHourMinuteAndPreferredTimezone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import repository.ActionRepository
import java.util.*

//TODO rename to RequestAction or UserAction? Like it's 1 time call and return
//TODO We should make builder in another class as writing it here will make the code more difficult to read as time passes by.
class ManualAction(actionRepository: ActionRepository) : Action(actionRepository) {

    suspend fun getCalendarRace(season: Int = 2021): BotOutcome {
        return withContext(Dispatchers.IO) {
            val calendar = actionRepository.getCalendarRace(season)
            val builder = StringBuilder("Here is the calendar of the season $season:\n")
            calendar.races.forEach {
                builder.append("\n*${it.grandPrix.name}:*\n")
                builder.append("\t\t\t\t*Qualifying:* ${it.dateQualifying.formatMonthDay()}\n")
                builder.append("\t\t\t\t*Race*: ${it.dateRace.formatMonthDay()}\n")
                builder.append("More information about the race:\n/Race${it.id}\n")
            }
            return@withContext BotOutcome.SendMessage(builder.toString())
        }
    }

    suspend fun getRaceDetails(raceId: Long): BotOutcome {
        return withContext(Dispatchers.IO) {
            val timeZone = "Europe/Madrid"
            val race = actionRepository.getRaceDetails(raceId)
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
//            builder.append("yes")
            return@withContext BotOutcome.SendPhotoByUrl(builder.toString(), race.grandPrix.circuit.layoutCircuitUrl)
        }
    }
}
