package functionality.action

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import repository.ActionRepository
import utils.Answer

//TODO rename to RequestAction or UserAction? Like it's 1 time call and return
class ManualAction(actionRepository: ActionRepository) : Action(actionRepository) {

    //TODO add column to race for GrandPrix but together -> BahrainGP or EmiliaRomagnaGP and this column will be for string commands
    suspend fun getCalendarRace(season: Int = 2021): Answer<String> {
        return withContext(Dispatchers.IO) {
            val calendar = actionRepository.getCalendarRace()
            val builder = StringBuilder("Here is the calendar of the season $season:\n\n")
            calendar.races.forEach {
                builder.append("/${it.grandPrix.name}\n")
            }
            return@withContext Answer.Yes(builder.toString())
        }
    }
}