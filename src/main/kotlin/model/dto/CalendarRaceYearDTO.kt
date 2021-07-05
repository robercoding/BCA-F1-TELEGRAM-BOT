package model.dto

data class CalendarRaceYearDTO(
    var yearId: Int = -1,
    val season: Int,
    val races: List<RaceDTO>,
    var numberRaces: Int = races.count()
)
