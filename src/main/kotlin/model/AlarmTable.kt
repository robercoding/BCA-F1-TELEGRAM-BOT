package model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement

object AlarmTable : LongIdTable("alarm", "alarm_id") {
    var isActivated = bool("isActivated")
    var day = integer("day")
    var hour = integer("hour")
    var minute = integer("minute")
}

class AlarmEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<AlarmEntity>(AlarmTable){
         fun insertDeactivatedAlarm() : AlarmEntity {
            return AlarmEntity.new {
                this.day = 0
                this.hour = 0
                this.minute = 0
                this.isActivated = false
            }
        }
    }

    var isActivated by AlarmTable.isActivated
    var day by AlarmTable.day
    var hour by AlarmTable.hour
    var minute by AlarmTable.minute
}