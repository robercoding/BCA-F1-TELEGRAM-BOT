package domain.model.dao

import common.utils.toDate
import domain.model.dto.RaceDTO
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime


object RaceTable : LongIdTable("Race", "race_id") {
    val calendarRaceId = reference("calendar_race_year_id", CalendarRaceYearTable, onDelete = ReferenceOption.NO_ACTION)
    val grandPrixId = reference("grand_prix_id", GrandPrixTable, onDelete = ReferenceOption.NO_ACTION)

    val weekRace = integer("week_race").default(-1)
    val dateQualifying = datetime("date_qualifying").default(LocalDateTime.of(1, 1, 1, 1, 1, 1))
    val dateRace = datetime("date_race").default(LocalDateTime.of(1, 1, 1, 1, 1, 1))
    val isSprintQualifying = bool("is_sprint_qualifying").default(false)
    val dateSprintQualifying = datetime("date_sprint_qualifying").default(LocalDateTime.of(1, 1, 1, 1, 1, 1))
}

class RaceEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<RaceEntity>(RaceTable)

    var calendarRaceYear by CalendarRaceYearEntity referencedOn RaceTable.calendarRaceId
    var grandPrix by GrandPrixEntity referencedOn RaceTable.grandPrixId

    var weekRace by RaceTable.weekRace
    var dateQualifying by RaceTable.dateQualifying
    var dateRace by RaceTable.dateRace
    var isSprintQualifying by RaceTable.isSprintQualifying
    var dateSprintQualifying by RaceTable.dateSprintQualifying
}

fun RaceEntity.toRaceDTO(): RaceDTO = RaceDTO(
    id = this.id.value,
    grandPrix = this.grandPrix.toGrandPrix(),
    weekRace = this.weekRace,
    dateRace = this.dateRace.toDate(),
    dateQualifying = this.dateQualifying.toDate(),
    season = this.calendarRaceYear.season,
    isSprintQualifying = this.isSprintQualifying,
    dateSprintQuailifying = this.dateSprintQualifying.toDate()
)