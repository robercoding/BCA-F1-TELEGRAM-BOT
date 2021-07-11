package functionality.action.manual

import common.utils.BotOutcome
import common.utils.FormatCaption
import functionality.action.Action
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

            val captionCalendar = FormatCaption.formatCalendar(season, calendar.races)
            return@withContext BotOutcome.SendMessage(captionCalendar)
        }
    }

    suspend fun getRaceDetails(raceId: Long): BotOutcome {
        return withContext(Dispatchers.IO) {
            val timeZone = "Europe/Madrid"
            val race = actionRepository.getRaceDetails(raceId)

            val captionRaceDetails = FormatCaption.formatRaceDetails(race, timeZone)
            return@withContext BotOutcome.SendPhotoByUrl(captionRaceDetails, race.grandPrix.circuit.layoutCircuitUrl)
        }
    }
}
