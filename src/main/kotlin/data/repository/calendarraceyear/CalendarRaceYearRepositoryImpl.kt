package data.repository.calendarraceyear

import domain.interfaces.CalendarRaceYearRepository
import domain.model.dao.CalendarRaceYearEntity
import domain.model.dao.CalendarRaceYearTable
import domain.model.dao.toCalendarRaceYearDTO
import domain.model.dto.CalendarRaceYearDTO
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class CalendarRaceYearRepositoryImpl : CalendarRaceYearRepository {
    override fun findById(id: Long): CalendarRaceYearDTO? =
        transaction { return@transaction CalendarRaceYearEntity.findById(id.toInt())?.toCalendarRaceYearDTO() }

    override fun update(calendarRaceYearDTO: CalendarRaceYearDTO): CalendarRaceYearDTO? {
        return transaction {
            CalendarRaceYearTable.update({ CalendarRaceYearTable.id eq calendarRaceYearDTO.yearId }) {
                it[season] = calendarRaceYearDTO.season
            }
            return@transaction findById(calendarRaceYearDTO.yearId.toLong())
        }
    }
}