package model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime


object RaceTable : IntIdTable("Race", "race_id") {
    val calendarRaceId = reference("calendar_race_year_id", CalendarRaceYearTable, onDelete = ReferenceOption.CASCADE)
    val grandPrixId = reference("grand_prix_id", GrandPrixTable, onDelete = ReferenceOption.CASCADE)

    val weekRace = integer("week_race").default(-1)
    val dateQualifying = datetime("date_qualifying").default(LocalDateTime.of(1, 1, 1, 1, 1, 1))
    val dateRace = datetime("date_race").default(LocalDateTime.of(1, 1, 1, 1, 1, 1))
    val isSprintQualifying = bool("is_sprint_qualifying").default(false)
    val dateSprintQualifying = datetime("date_sprint_qualifying").default(LocalDateTime.of(1, 1, 1, 1, 1, 1))
}

class RaceEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RaceEntity>(RaceTable)

    var calendarRaceYear by CalendarRaceYearEntity referencedOn RaceTable.calendarRaceId
    var grandPrix by GrandPrixEntity referencedOn RaceTable.grandPrixId

    var weekRace by RaceTable.weekRace
    var dateQualifying by RaceTable.dateQualifying
    var dateRace by RaceTable.dateRace
    var isSprintQualifying by RaceTable.isSprintQualifying
    var dateSprintQualifying by RaceTable.dateSprintQualifying
}