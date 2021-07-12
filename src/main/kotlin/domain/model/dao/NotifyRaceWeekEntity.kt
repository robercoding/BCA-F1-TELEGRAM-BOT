package domain.model.dao

import domain.model.NotifyRaceWeek
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object NotifyRaceWeekTable : LongIdTable("alarm", "alarm_id") {
    var isActivated = bool("isActivated")
    var day = integer("day")
    var hour = integer("hour")
    var minute = integer("minute")
}

class NotifyRaceWeekEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<NotifyRaceWeekEntity>(NotifyRaceWeekTable) {
        fun genericDeactivatedAlarm(): NotifyRaceWeekEntity {
            return NotifyRaceWeekEntity.new {
                this.day = 0
                this.hour = 0
                this.minute = 0
                this.isActivated = false
            }
        }
    }

    var isActivated by NotifyRaceWeekTable.isActivated
    var day by NotifyRaceWeekTable.day
    var hour by NotifyRaceWeekTable.hour
    var minute by NotifyRaceWeekTable.minute
}

fun NotifyRaceWeekEntity.toNotifyRaceWeek() = NotifyRaceWeek(
    id = id.value,
    isActivated = this.isActivated,
    day = this.day,
    hour = this.hour,
    minute = this.minute
)