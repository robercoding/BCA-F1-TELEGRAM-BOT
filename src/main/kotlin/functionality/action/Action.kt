package functionality.action

import Answer
import functionality.race.RaceManager
import model.Race
import repository.ActionRepository

abstract class Action(
        private val actionRepository: ActionRepository
) {

    private val raceManager = RaceManager()

    fun isRaceWeek(): Answer<Race> {
        val raceCalendar = actionRepository.getRaceCalendar()
        val races = raceCalendar.races

        if (raceManager.isThisWeekRaceWeek(races)) {
            val race = raceManager.getNextRaceWeek(races)
            return Answer.Yes(race)
        }

        return Answer.Not(null)
    }
}