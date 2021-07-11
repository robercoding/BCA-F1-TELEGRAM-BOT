package domain.usecase

import common.utils.CalendarRaceYearNotFound
import domain.model.dao.CalendarRaceYearEntity
import domain.model.dao.toCalendarRaceYearDTO
import domain.model.dto.CalendarRaceYearDTO
import org.jetbrains.exposed.sql.transactions.transaction

//TODO ADD CalendarRaceYearRepository and add functions from ActioNRepository for calendarRaceYear (Maybe there's only 1 function that's already added here "getCalendarRace")
class CalendarRaceYearUseCase {
    fun getCalendarRace(season: Int = 2021): CalendarRaceYearDTO {
        return transaction {
            val calendarRaceYearEntity =
                CalendarRaceYearEntity.findById(season) ?: throw CalendarRaceYearNotFound(season)
            return@transaction calendarRaceYearEntity.toCalendarRaceYearDTO()
        }
    }
}