package functionality.race

import model.Race
import java.util.*

class RaceManager {

    private val raceCalendar = mutableListOf<Race>(
            Race(Date(1624114800000), "Gran Premio de Francia", "PAUL RICARD", "https://www.formula1.com/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/France_Circuit.png.transform/5col/image.png", "Francia", Date(1624114800000), Date(1624201200000)),
            Race(Date(1624719600000), "Gran Premio de Der Steiermark", "RED BULL RING", "https://www.formula1.com/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Styria_Circuit.png.transform/5col/image.png",  "Austria",Date(1624719600000), Date(1624806000000)),
            Race(Date(1625324400000), "Gran Premio de Von Österreich", "RED BULL RING", "https://www.formula1.com/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Styria_Circuit.png.transform/5col/image.png",  "Austria",Date(1625324400000), Date(1625410800000)),
            Race(Date(1625324400000), "Gran Premio de Bretaña", "SILVERSTONE", "https://www.formula1.com/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Styria_Circuit.png.transform/5col/image.png",  "Gran Bretaña",Date(1625324400000), Date(1625410800000)),
    )

    private var nextRace = Race()

    fun isThisWeekRaceWeek() : Boolean{
        val calendar = Calendar.getInstance()
        val todayWeekOfYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)

        raceCalendar.forEach {
            calendar.time = it.dateRace
            val raceWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

            println("Week today = $todayWeekOfYear and raceweek = $raceWeekOfYear and date ${it.dateRace}")
            if(todayWeekOfYear == raceWeekOfYear) {
                return true
            }
        }
        return false
    }

    fun getNextRaceWeek() : Race{
        val timeNowMillis = Date().time
        for(race in raceCalendar){
            if(timeNowMillis < race.dateRace.time) {
                return race
            }
        }
        return Race()
    }
}