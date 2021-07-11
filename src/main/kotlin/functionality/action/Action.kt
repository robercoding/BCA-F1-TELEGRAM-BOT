package functionality.action

import common.utils.Answer
import functionality.race.RaceHelper
import model.dto.RaceDTO
import repository.ActionRepository

abstract class Action(
    protected val actionRepository: ActionRepository
) {

    private val raceManager = RaceHelper()

    fun isRaceWeek(): Answer<RaceDTO?> {
        val raceCalendar = actionRepository.getCalendarRace()
        val races = raceCalendar.races

        if (raceManager.isThisWeekRaceWeek(races)) {
            val race = raceManager.getNextRaceWeek(races)
            return Answer.Yes(race)
        }

        return Answer.Not(null)
    }
}