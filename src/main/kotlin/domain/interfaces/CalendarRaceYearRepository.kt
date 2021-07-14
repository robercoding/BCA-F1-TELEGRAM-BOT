package domain.interfaces

import domain.model.dto.CalendarRaceYearDTO

interface CalendarRaceYearRepository : Repository<CalendarRaceYearDTO> {
    override fun update(calendarRaceYearDTO: CalendarRaceYearDTO): CalendarRaceYearDTO?
}