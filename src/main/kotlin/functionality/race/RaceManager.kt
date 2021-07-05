package functionality.race

import model.dto.RaceDTO
import java.util.*

class RaceManager {


    //TODO use DateUtils get weekOfyear
    fun isThisWeekRaceWeek(races: List<RaceDTO>): Boolean {
        val calendar = Calendar.getInstance()
        val todayWeekOfYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)

        races.forEach {
            calendar.time = it.dateRace
            val raceWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

            println("Week today = $todayWeekOfYear and raceweek = $raceWeekOfYear and date ${it.dateRace}")
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