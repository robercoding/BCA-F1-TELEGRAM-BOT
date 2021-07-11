package domain.model.dao

import domain.model.dto.CalendarRaceYearDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object CalendarRaceYearTable : IntIdTable("RaceCalendar", "year_id") {
    var season = integer("season")
//    var drivers  = reference driver etc.. //TODO create a driver table and an intermediate table Many-to-many
}

class CalendarRaceYearEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CalendarRaceYearEntity>(CalendarRaceYearTable)

    val races by RaceEntity referrersOn RaceTable.calendarRaceId //referrersOn must have val

    var season by CalendarRaceYearTable.season
}

fun CalendarRaceYearEntity.toCalendarRaceYearDTO(): CalendarRaceYearDTO {
    return CalendarRaceYearDTO(
        yearId = this.id.value,
        season = this.season,
        races = this.races.map { raceEntity -> raceEntity.toRaceDTO() }
    )
}