package domain.usecase

import common.utils.CalendarRaceYearNotFound
import data.repository.calendarraceyear.CalendarRaceYearRepository
import domain.model.dto.CalendarRaceYearDTO

//TODO ADD CalendarRaceYearRepository and add functions from ActioNRepository for calendarRaceYear (Maybe there's only 1 function that's already added here "getCalendarRace")
class CalendarRaceYearUseCase(
    private val calendarRaceYearRepository: CalendarRaceYearRepository
) {
    fun getCalendarRace(season: Int = 2021): CalendarRaceYearDTO {
        return calendarRaceYearRepository.findById(season.toLong()) ?: throw CalendarRaceYearNotFound(season)
    }
}