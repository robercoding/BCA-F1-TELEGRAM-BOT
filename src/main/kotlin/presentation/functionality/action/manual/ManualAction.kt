package presentation.functionality.action.manual

import common.outcome.BotOutcome
import common.utils.FormatCaption
import domain.usecase.CalendarRaceYearUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import presentation.functionality.action.Action
import java.util.*

//TODO rename to RequestAction or UserAction? Like it's 1 time call and return
//TODO We should make builder in another class as writing it here will make the code more difficult to read as time passes by.
//TODO add RaceYearUseCase as a parameter
class ManualAction(
    private val calendarRaceYearUseCase: CalendarRaceYearUseCase
) : Action() {

    suspend fun getCalendarRace(season: Int = 2021): BotOutcome {
        return withContext(Dispatchers.IO) {
            val calendar = calendarRaceYearUseCase.getCalendarRace(season)

            val captionCalendar = FormatCaption.formatCalendar(season, calendar.races)
            return@withContext BotOutcome.SendMessage(captionCalendar)
        }
    }

    suspend fun getRaceDetails(raceId: Long): BotOutcome {
        return withContext(Dispatchers.IO) {
            val timeZone = "Europe/Madrid"
            val race = calendarRaceYearUseCase.getRaceDetails(raceId)

            val captionRaceDetails = FormatCaption.formatRaceDetails(race, timeZone)
            return@withContext BotOutcome.SendPhotoByUrl(captionRaceDetails, race.grandPrix.circuit.layoutCircuitUrl)
        }
    }
}
