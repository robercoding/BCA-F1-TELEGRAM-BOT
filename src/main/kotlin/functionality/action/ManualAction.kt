package functionality.action

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import repository.ActionRepository
import utils.BotOutcome

//TODO rename to RequestAction or UserAction? Like it's 1 time call and return
//TODO We should make builder in another class as writing it here will make the code more difficult to read as time passes by.
class ManualAction(actionRepository: ActionRepository) : Action(actionRepository) {

    suspend fun getCalendarRace(season: Int = 2021): BotOutcome {
        return withContext(Dispatchers.IO) {
            val calendar = actionRepository.getCalendarRace()
            val builder = StringBuilder("Here is the calendar of the season $season:\n")
            calendar.races.forEach {
                builder.append("\n${it.grandPrix.name}:\n")
                builder.append("${it.dateQualifying} - ${it.dateRace}\n")
                builder.append("More information about the race: /Race${it.id}\n")
            }
            return@withContext BotOutcome.SendMessage(builder.toString())
        }
    }

    suspend fun getRaceDetails(raceId: Long): BotOutcome {
        return withContext(Dispatchers.IO) {
            val race = actionRepository.getRaceDetails(raceId)
            val builder = StringBuilder()
            builder.append("${race.grandPrix.name}\n")
            builder.append("Circuit & Country: ${race.grandPrix.circuit.name}, ${race.grandPrix.circuit.country}\n")
            if (race.isSprintQualifying) {
                builder.append("Sprint Qualifying: ${race.dateSprintQuailifying}\n")
            }

            builder.append("Qualifying: ${race.dateQualifying}\n")
            builder.append("Race: ${race.dateRace}")

            return@withContext BotOutcome.SendPhotoByUrl(builder.toString(), race.grandPrix.circuit.layoutCircuitUrl)
        }
    }
}