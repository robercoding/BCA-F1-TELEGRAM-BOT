package data.repository.calendarraceyear

import domain.model.dao.CalendarRaceYearEntity
import domain.model.dao.toCalendarRaceYearDTO
import domain.model.dto.CalendarRaceYearDTO
import org.jetbrains.exposed.sql.transactions.transaction

class CalendarRaceYearRepositoryImpl : CalendarRaceYearRepository {
    override fun findById(id: Long): CalendarRaceYearDTO? =
        transaction { return@transaction CalendarRaceYearEntity.findById(id.toInt())?.toCalendarRaceYearDTO() }
}