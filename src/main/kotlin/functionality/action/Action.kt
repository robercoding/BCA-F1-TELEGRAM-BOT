package functionality.action

import functionality.race.RaceManager
import model.dto.RaceDTO
import repository.ActionRepository
import utils.Answer

abstract class Action(
        private val actionRepository: ActionRepository
) {

    private val raceManager = RaceManager()

    fun isRaceWeek(): Answer<RaceDTO?> {
        val raceCalendar = actionRepository.getRaceCalendar()
        val races = raceCalendar.races

        if (raceManager.isThisWeekRaceWeek(races)) {
            val race = raceManager.getNextRaceWeek(races)
            return Answer.Yes(race)
        }

        return Answer.Not(null)
    }
}