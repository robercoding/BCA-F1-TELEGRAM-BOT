package data.repository.calendarraceyear

import data.Repository
import domain.model.dto.CalendarRaceYearDTO

interface CalendarRaceYearRepository : Repository<CalendarRaceYearDTO> {
    override fun update(calendarRaceYearDTO: CalendarRaceYearDTO): CalendarRaceYearDTO?
}