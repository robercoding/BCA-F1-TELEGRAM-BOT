package presentation.functionality.race

import domain.model.dto.RaceDTO
import help.getWeekDayOfYear
import java.util.*

class RaceHelper {

    fun isThisWeekRaceWeek(races: List<RaceDTO>): Boolean {
        val todayWeekOfYear = Date().getWeekDayOfYear()

        races.forEach {
            val raceWeekOfYear = it.dateRace.getWeekDayOfYear()
            if (todayWeekOfYear == raceWeekOfYear) {
                return true
            }
        }
        return false
    }

    fun getNextRaceWeek(races: List<RaceDTO>): RaceDTO? {
        val timeNowMillis = Date().time
        for (race in races) {
            if (timeNowMillis < race.dateRace.time) {
//            if (timeNowMillis < race.dateRace.time) {
                return race
            }
        }
        return null
    }
}